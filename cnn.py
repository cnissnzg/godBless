import torch
import torch.nn as nn
import torchvision
from torch.autograd import Variable
import matplotlib.pyplot as plt
import torch.nn.functional as F
import torch.utils.data as Data
import cv2
import random
import numpy as np
from data import BATCH_SIZE,trainData
import watermark
import imageProcess

EPOCH = 10
LR = 0.001
DOWNLOAD_MINIST = False
lamd = 0.5
DEBUG = False

#hi
class WatermarkGeneration(nn.Module):
    def __init__(self, key):
        super(WatermarkGeneration, self).__init__()
        self.key = key
        self.layer1 = nn.Sequential(
            nn.ConvTranspose2d(
                in_channels=1,
                out_channels=12,
                kernel_size=3,
                stride=1,
                padding=1
            ),
            nn.BatchNorm2d(12),
            nn.ReLU()
        )
        self.layer2 = nn.Sequential(
            nn.ConvTranspose2d(
                in_channels=12,
                out_channels=48,
                kernel_size=3,
                stride=1,
                padding=1
            ),
            nn.BatchNorm2d(48),
            nn.ReLU()
        )
        self.layer3 = nn.Sequential(
            nn.ConvTranspose2d(
                in_channels=48,
                out_channels=96,
                kernel_size=3,
                stride=1,
                padding=1
            ),
            nn.BatchNorm2d(96),
            nn.ReLU()
        )
        self.layer4 = nn.Sequential(
            nn.ConvTranspose2d(
                in_channels=96,
                out_channels=48,
                kernel_size=3,
                stride=1,
                padding=1
            ),
            nn.BatchNorm2d(48),
            nn.ReLU()
        )
        self.layer5 = nn.Sequential(
            nn.ConvTranspose2d(
                in_channels=48,
                out_channels=24,
                kernel_size=3,
                stride=1,
                padding=1
            ),
            nn.BatchNorm2d(24),
            nn.ReLU()
        )
        self.layer6 = nn.Sequential(
            nn.ConvTranspose2d(
                in_channels=24,
                out_channels=12,
                kernel_size=3,
                stride=2
            ),
            nn.BatchNorm2d(12),
            nn.ReLU()
        )
        self.layer7 = nn.Sequential(
            nn.ConvTranspose2d(
                in_channels=12,
                out_channels=6,
                kernel_size=3,
                stride=2,
                padding=1
            ),
            nn.BatchNorm2d(6),
            nn.ReLU()
        )
        self.layer8 = nn.ConvTranspose2d(
            in_channels=6,
            out_channels=1,
            kernel_size=3,
            stride=2,
            padding=1
        )

    def masking(self, x, key, m=8, n=8):
        a, b, c, d, e = [], [], [], [], []
        for i in range(x.shape[2]):
            for j in range(x.shape[3]):
                if key[i // m][j // n] == 1:
                    a.append(0)
                    b.append(0)
                    c.append(i)
                    d.append(j)
                    e.append(0)

        x.index_put_((torch.LongTensor(a), torch.LongTensor(b), torch.LongTensor(c), torch.LongTensor(d)),
                     torch.Tensor(e))

        return x

    def forward(self, x):
        x = self.layer1(x)
        x = self.layer2(x)
        x = self.layer3(x)
        x = self.layer4(x)
        x = self.layer5(x)
        x = self.layer6(x)
        x = self.layer7(x)
        x = self.layer8(x)
        x = self.masking(x[:, :, :512, :512], key=self.key)
        return x


class templateExtNet(nn.Module):
    def __init__(self):
        super(templateExtNet, self).__init__()
        self.layer1 = nn.Sequential(
            nn.Conv2d(
                in_channels=1,
                out_channels=6,
                kernel_size=3,
                stride=2
            ),
            nn.BatchNorm2d(6),
            nn.ReLU()
        )
        self.layer2 = nn.Sequential(
            nn.Conv2d(
                in_channels=6,
                out_channels=12,
                kernel_size=3,
                stride=2
            ),
            nn.BatchNorm2d(12),
            nn.ReLU()
        )
        self.layer3 = nn.Sequential(
            nn.Conv2d(
                in_channels=12,
                out_channels=24,
                kernel_size=3,
                stride=2
            ),
            nn.BatchNorm2d(24),
            nn.ReLU()
        )
        self.layer4 = nn.Sequential(
            nn.Conv2d(
                in_channels=24,
                out_channels=48,
                kernel_size=3,
                stride=1
            ),
            nn.BatchNorm2d(48),
            nn.ReLU()
        )
        self.layer5 = nn.Sequential(
            nn.Conv2d(
                in_channels=48,
                out_channels=96,
                kernel_size=3,
                stride=1
            ),
            nn.BatchNorm2d(96),
            nn.ReLU()
        )
        self.layer6 = nn.Sequential(
            nn.Conv2d(
                in_channels=96,
                out_channels=48,
                kernel_size=3,
                stride=1
            ),
            nn.BatchNorm2d(48),
            nn.ReLU()
        )
        self.layer7 = nn.Sequential(
            nn.Conv2d(
                in_channels=48,
                out_channels=12,
                kernel_size=3,
                stride=1
            ),
            nn.BatchNorm2d(12),
            nn.ReLU()
        )
        self.layer8 = nn.Sequential(
            nn.Conv2d(
                in_channels=12,
                out_channels=1,
                kernel_size=3,
                stride=1
            ),
            nn.BatchNorm2d(1),
            nn.ReLU()
        )
        self.FC = nn.Linear(53 * 53, 64 * 64)
        nn.init.xavier_uniform_(next(self.layer1.children()).weight)
        nn.init.xavier_uniform_(next(self.layer2.children()).weight)
        nn.init.xavier_uniform_(next(self.layer3.children()).weight)
        nn.init.xavier_uniform_(next(self.layer4.children()).weight)
        nn.init.xavier_uniform_(next(self.layer5.children()).weight)
        nn.init.xavier_uniform_(next(self.layer6.children()).weight)
        nn.init.xavier_uniform_(next(self.layer7.children()).weight)
        nn.init.xavier_uniform_(next(self.layer8.children()).weight)
        nn.init.xavier_uniform_(self.FC.weight)

    def forward(self, x):
        x = self.layer1(x)
        x = self.layer2(x)
        x = self.layer3(x)
        x = self.layer4(x)
        x = self.layer5(x)
        x = self.layer6(x)
        x = self.layer7(x)
        x = self.layer8(x)
        x = x.view(x.size(0),1, -1)

        x = self.FC(x)
        x = x.view(x.size(0),64, 64)
        return x


class featureExtNet(nn.Module):
    def __init__(self):
        super(featureExtNet, self).__init__()
        self.layer1 = nn.Sequential(
            nn.Conv2d(
                in_channels=1,
                out_channels=256,
                kernel_size=64,
                stride=1
            ),
            nn.BatchNorm2d(256),
            nn.ReLU()
        )
        self.layer2 = nn.Sequential(
            nn.Conv2d(
                in_channels=1,
                out_channels=256,
                kernel_size=16,
                stride=1
            ),
            nn.BatchNorm2d(256),
            nn.ReLU()
        )
        self.layer3 = nn.Sequential(
            nn.Conv2d(
                in_channels=1,
                out_channels=256,
                kernel_size=16,
                stride=1
            ),
            nn.BatchNorm2d(256),
            nn.ReLU()
        )
        nn.init.xavier_uniform_(next(self.layer1.children()).weight)
        nn.init.xavier_uniform_(next(self.layer2.children()).weight)
        nn.init.xavier_uniform_(next(self.layer3.children()).weight)

    def forward(self, x):
        x = self.layer1(x)
        x = x.view(-1, 1, 16, 16)
        x = self.layer2(x)
        x = x.view(-1, 1, 16, 16)
        x = self.layer3(x)
        x = x.view(-1, 1, 16, 16)
        return x


class templateMatchNet(nn.Module):
    def __init__(self):
        super(templateMatchNet, self).__init__()
        self.layer1 = nn.Sequential(
            nn.Conv2d(
                in_channels=2,
                out_channels=8,
                kernel_size=5,
                stride=1
            ),
            nn.BatchNorm2d(8),
            nn.ReLU()
        )
        self.layer2 = nn.Sequential(
            nn.Conv2d(
                in_channels=8,
                out_channels=4,
                kernel_size=3,
                stride=1
            ),
            nn.BatchNorm2d(4),
            nn.ReLU()
        )
        self.layer3 = nn.Sequential(
            nn.Conv2d(
                in_channels=4,
                out_channels=2,
                kernel_size=1,
                stride=1
            ),
            nn.BatchNorm2d(2),
            nn.ReLU()
        )
        self.layer4 = nn.Sequential(
            nn.Conv2d(
                in_channels=2,
                out_channels=1,
                kernel_size=1,
                stride=1
            ),
            nn.BatchNorm2d(1),
            nn.ReLU()
        )
        self.FC = nn.Linear(10 * 10, 5)
        nn.init.xavier_uniform_(next(self.layer1.children()).weight)
        nn.init.xavier_uniform_(next(self.layer2.children()).weight)
        nn.init.xavier_uniform_(next(self.layer3.children()).weight)
        nn.init.xavier_uniform_(next(self.layer4.children()).weight)
        nn.init.xavier_uniform_(self.FC.weight)

    def forward(self, x):
        x = self.layer1(x)
        x = self.layer2(x)
        x = self.layer3(x)
        x = self.layer4(x)
        x = x.view(x.size(0), 1, -1)
        x = self.FC(x)
        return x


class L2Loss(nn.Module):
    def __init__(self, U=512, delta=None):
        super(L2Loss, self).__init__()
        self.delta = delta
        self.U = U

    def forward(self, x):
        if self.delta is not None:
            x = torch.sub(x, self.delta)
        x = torch.pow(x, 2)
        x = torch.sum(x)
        x = torch.div(x, self.U * self.U)
        return x


class EularLoss(nn.Module):
    def __init__(self, S=10, M=512, N=512):
        super(EularLoss, self).__init__()
        self.S = S
        self.Y = [M * i / S for i in range(S)]
        self.X = [N * i / S for i in range(S)]

    def doRST(self, RST, y, x, M=512, N=512):
        rad = RST[0].mul(np.pi/180.)
        ny = torch.cos(rad).mul(y - M / 2).sub(torch.sin(rad).mul(x - N / 2)).add(M / 2)
        nx = torch.cos(rad).mul(x - N / 2).add(torch.sin(rad).mul(y - M / 2)).add(N / 2)
        ny = ny.mul(abs(RST[1])).add(RST[3].mul(M))
        nx = nx.mul(abs(RST[2])).add(RST[4].mul(N))
        return (ny,nx)

    def forward(self, Ge, Gt):
        Ge = torch.split(Ge, 1,dim=2)
        Gt = torch.tensor(Gt).split(1)
        #print(Ge)
        #print(Gt)
        res = None
        for y in self.Y:
            for x in self.X:
                Pe = self.doRST(Ge,y,x)
                Pt = self.doRST(Gt,y,x)
                Pe = Pe[0].sub(Pt[0]).pow(2).add(Pe[1].sub(Pt[1]).pow(2)).sqrt()
                if res is None:
                    res = Pe
                else:
                    res = torch.add(res,Pe)
        res = res.div(self.S*self.S)
        return res

def templateGen(M, N):
    random.seed(100)
    key = [0] * (M * N // 2) + [1] * (M * N // 2)
    random.shuffle(key)
    key = np.array(key).reshape((M, N))
    return key


def zigZag(M, m, n):
    x, y, k = 0, 0, 0
    res = [M[0][0]]
    while True:
        if y == 0 and (x + m * 2 + n * 2) % 2 == 0:
            x += 1
        elif y == m - 1 and (x + m * 2 + n * 2) % 2 == m % 2:
            x += 1
        elif (x + y) % 2 == 1:
            x -= 1
            y += 1
        else:
            x += 1
            y -= 1
        if y < m and x < n and y >= 0 and x >= 0:
            res.append(M[y][x])
        if y == m - 1 and x > n - 1:
            break
    return res


def noise_fill(M, N, seq, key, m=8, n=8):
    res = np.ndarray((M, N), dtype=np.float32)
    k = 0
    for i in range(M):
        for j in range(N):
            if key[i // m][j // n] == 0:
                res[i][j] = seq[k]
                k += 1
            else:
                res[i][j] = 0
    return res


def pre_mat(key, M=512, N=512, m=8, n=8):
    cof = cv2.cvtColor(cv2.imread('lena.jpg'), cv2.COLOR_RGB2GRAY)
    cof = cv2.dct(cof.astype(np.float32))
    cof = zigZag(cof, M, N)
    cof = cof[M * N // 4:M * N // 4 * 3]
    std, mean = np.std(cof), np.mean(cof)
    cof = [(i - mean) / std for i in cof]
    res = noise_fill(M, N, cof, key, m, n)
    return Variable(torch.tensor(np.array([[res]], dtype=np.float32)))


def preTrain(key):
    img = pre_mat(key)
    cnn = WatermarkGeneration(key)
    optimizer = torch.optim.Adam(cnn.parameters(), lr=1e-3)
    loss_func = L2Loss(delta=img)
    temp = Variable(torch.tensor(np.array([[key]], dtype=np.float32)))
    for epoch in range(3):
        out = cnn(temp)
        loss = loss_func(out)
        print(loss)
        optimizer.zero_grad()
        loss.backward()
        optimizer.step()


def process(img, key, temp, RST, M=512, N=512, m=8, n=8):
    img = img.detach().numpy()
    for t in range(len(img)):
        for i in range(m):
            for j in range(n):
                if key[i][j] == 1:
                    # img[i*8:i*8+8,j*8:j*8+8] = watermark.watermark_embedding(img[i*8:i*8+8,j*8:j*8+8])
                    continue
                else:
                    for ii in range(m):
                        for jj in range(n):
                            img[t][0][i * 8 + ii, j * 8 + jj] = np.clip(
                                img[t][0][i * 8 + ii, j * 8 + jj] + temp[t][0][i * 8 + ii, j * 8 + jj], 0, 255)
        img[t][0] = imageProcess.doRST(img[t][0],RST, M, N)
    return img

def debug(t,text=''):
    if not DEBUG:
        return
    print(text,t,t.size())

img = cv2.cvtColor(cv2.imread('lena.jpg'), cv2.COLOR_RGB2GRAY)
key = templateGen(64, 64)
preTrain(key)
multiKey = [[key] for i in range(BATCH_SIZE)]
origin = torch.tensor(multiKey, dtype=torch.float32)
debug(origin)

watermarkGen = WatermarkGeneration(key)
tExtN = templateExtNet()
fExtN = featureExtNet()
tMatN = templateMatchNet()
optimizerLg = torch.optim.Adam(watermarkGen.parameters(), lr=1e-3)
optimizerLe = torch.optim.Adam(tExtN.parameters(), lr=1e-3)
optimizerLd = torch.optim.Adam(fExtN.parameters(), lr=1e-3)
optimizerL = torch.optim.Adam(tMatN.parameters(), lr=1e-3)

for epoch in range(EPOCH):
    for step, (data, y) in enumerate(trainData):
        print('epoch',epoch,', step',step,':')
        RST = [random.randint(0,90), random.uniform(0.7,1.5), random.uniform(0.7,1.5), random.uniform(0,0.3),random.uniform(0,0.3)]
        temp = origin
        if(data.size(0) < BATCH_SIZE):
            break
        tempNoise = watermarkGen(Variable(temp))
        loss_func = L2Loss()
        Lg = loss_func(tempNoise)
        optimizerLg.zero_grad()
        debug(tempNoise)

        imgProcessed = process(data, key, tempNoise.detach().numpy(), RST)
        extTemp = tExtN(Variable(torch.tensor(imgProcessed,dtype=torch.float32)))
        debug(extTemp)
        GTemplate = imageProcess.doRST(key, RST, 64, 64)
        if DEBUG:
            print(GTemplate)
        loss_func = L2Loss(64, torch.tensor(GTemplate))
        Le = loss_func(extTemp)
        Lt = torch.add(torch.mul(lamd, Lg), torch.mul(1 - lamd, Le)).mean()
        optimizerLe.zero_grad()

        extTemp = extTemp.view(-1, 1, 64, 64)
        #extTemp = torch.cat((extTemp, extTemp), 0)#TODO delete
        #temp = torch.cat((temp, temp), 0)#TODO delete
        featureExtTemp = fExtN(extTemp)
        temp = fExtN(temp)
        featureExtTemp = torch.cat((featureExtTemp, temp), 1)
        optimizerLd.zero_grad()
        debug(featureExtTemp)

        out = tMatN(featureExtTemp)
        debug(out)
        loss_func = EularLoss()
        Ld = loss_func(out,RST)
        L = torch.add(torch.mul(lamd,Lt),torch.mul(1-lamd,Ld)).mean()
        print('loss:',L.item())
        optimizerL.zero_grad()
        L.backward()

        optimizerL.step()
        optimizerLd.step()
        optimizerLe.step()
        optimizerLg.step()

torch.save(watermarkGen,'watermarkGen.pkl')
torch.save(tExtN,'tExtN.pkl')
torch.save(fExtN,'fExtN.pkl')
torch.save(tMatN,'tMatN.pkl')