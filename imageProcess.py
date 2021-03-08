# coding=utf-8

import cv2
import math
import numpy as np
import random
import math
import libjpeg

class attack:

    def __init__(self, filename):
        self.compressRate = cv2.IMWRITE_JPEG_QUALITY
        if filename is None:
            return
        self.image = cv2.imread(filename)
        self.h = self.image.shape[0]
        self.w = self.image.shape[1]


    def blur(self):
        kernel = np.ones((5, 5), np.float32) / 25
        self.image = cv2.filter2D(self.image, -1, kernel)
        return self

    def sharpen(self):
        mat = np.array([[-1, -1, -1], [-1, 9, -1], [-1, -1, -1]], dtype=np.float32)
        self.image = cv2.filter2D(self.image, cv2.CV_32F, mat)
        self.image = cv2.convertScaleAbs(self.image)
        return self

    def chop(self, rate):
        self.image[self.w - int(self.w * rate):, :] = self.image[:int(self.w * rate), :]
        return self

    def rotate(self, angle):
        rangle = np.deg2rad(angle)  # angle in radians
        nw = abs(np.sin(rangle) * self.h) + abs(np.cos(rangle) * self.w)
        nh = abs(np.cos(rangle) * self.h) + abs(np.sin(rangle) * self.w)
        rot_mat = cv2.getRotationMatrix2D((nw * 0.5, nh * 0.5), angle, 1.)
        rot_move = np.dot(rot_mat, np.array([(nw - self.w) * 0.5, (nh - self.h) * 0.5, 0]))
        rot_mat[0, 2] += rot_move[0]
        rot_mat[1, 2] += rot_move[1]
        self.image = self.image.astype(np.uint8)
        self.image = cv2.warpAffine(self.image, rot_mat, (int(math.ceil(nw)), int(math.ceil(nh))),
                                    flags=cv2.INTER_LANCZOS4)

        self.w = nw
        self.h = nh
        return self

    def brightness(self, rate):
        self.image = cv2.convertScaleAbs(self.image, beta=rate)
        return self

    def contrast(self, rate):
        self.image = cv2.convertScaleAbs(self.image, alpha=rate)
        return self

    def saturation(self, rate):
        fImg = self.image.astype(np.float32)
        fImg = fImg / 255.0
        hls = cv2.cvtColor(fImg, cv2.COLOR_BGR2HLS)
        hls[:, :, 2] = rate * hls[:, :, 2]
        hls[:, :, 2][hls[:, :, 2] > 1] = 1
        fImg = cv2.cvtColor(hls, cv2.COLOR_HLS2BGR)
        self.image = fImg * 255.0
        return self

    def resize(self, ratew, rateh):
        self.image = cv2.resize(self.image, (int(self.h * ratew), int(self.w * rateh)))
        self.w *= ratew
        self.h *= rateh
        return self

    def jpegRate(self, rate):
        self.compressRate = rate
        return self

    def saltNoise(self, num):
        for k in range(num):
            i = int(np.random.random() * self.h)
            j = int(np.random.random() * self.w)
            if self.image.ndim == 2:
                self.image[j, i] = 255
            elif self.image.ndim == 3:
                self.image[j, i, 0] = 255
                self.image[j, i, 1] = 255
                self.image[j, i, 2] = 255
        return self

    def cover(self):
        cv2.rectangle(self.image, (int(self.w / 4), int(self.h / 4)), (int(self.w / 2), int(self.h / 2)),
                      (255, 255, 255), -1)
        return self

    def flip(self, code):
        if code == 1:
            self.image = np.fliplr(self.image)
        else:
            self.image = np.flipud(self.image)
        return self

    def liner_blur(self):
        liner_blur_degree = int(random.uniform(3., 8.))
        tran = cv2.getRotationMatrix2D((liner_blur_degree / 2, liner_blur_degree / 2), random.uniform(0, 180.), 1)
        liner_blur_kernel = np.diag(np.ones(liner_blur_degree))
        liner_blur_kernel = cv2.warpAffine(liner_blur_kernel, tran, (liner_blur_degree, liner_blur_degree))
        liner_blur_kernel = liner_blur_kernel / liner_blur_degree
        self.image = cv2.filter2D(self.image, -1, liner_blur_kernel)
        cv2.normalize(self.image, self.image, 0, 255, cv2.NORM_MINMAX)
        self.image = np.array(self.image, dtype=np.uint8)
        return self

    def gaussian_blur(self):
        gaussian_blur_size = random.choice([1, 3])
        self.image = cv2.GaussianBlur(self.image, ksize=(gaussian_blur_size, gaussian_blur_size), sigmaX=0, sigmaY=0)
        for i in range(0, 3):
            n = random.uniform(-0.1, 0.1)
            for j in range(0, self.h):
                for k in range(0, self.w):
                    self.image[j, k, i] += n
        return self

    def gaussian_noise(self):
        self.h = self.image.shape[0]
        self.w = self.image.shape[1]

        for i in range(0, self.h):
            for j in range(0, self.w):
                s = np.random.normal(0, 5, 3)
                self.image[i, j, 0] = np.clip(self.image[i, j, 0] + s[0], 0, 255)
                self.image[i, j, 1] = np.clip(self.image[i, j, 1] + s[1], 0, 255)
                self.image[i, j, 2] = np.clip(self.image[i, j, 2] + s[2], 0, 255)
        return self

    def translation(self,h,w):
        mat_trans = np.float32([[1, 0, h], [0, 1, w]])
        self.image = cv2.warpAffine(self.image, mat_trans, (int(self.w + w), int(self.h + h)))
        self.w += w
        self.h += h
        return self

    def pro_tran(self):
        w = self.w
        h = self.h
        p1 = [random.uniform(0, w * 0.05), random.uniform(0, h * 0.05)]
        p2 = [random.uniform(w * 0.95, w), random.uniform(0, h * 0.05)]
        p3 = [random.uniform(0, w * 0.05), random.uniform(h * 0.95, h)]
        p4 = [random.uniform(w * 0.95, w), random.uniform(h * 0.95, h)]
        l = math.ceil(min(p1[0], p3[0]))
        r = math.floor(max(p2[0], p4[0]))
        u = math.ceil(min(p1[1], p2[1]))
        b = math.floor(max(p3[1], p4[1]))
        tran = cv2.getPerspectiveTransform(np.array([[0, 0], [w, 0], [0, h], [w, h]], dtype='float32'),
                                           np.array([p1, p2, p3, p4], dtype='float32'))
        self.image = cv2.warpPerspective(self.image, tran, (w, h))
        self.image = self.image[u:b, l:r]
        self.w = int(r - l)
        self.h = int(b - u)

        return self

    def shepin(self):
        self.pro_tran()
        self.liner_blur()
        self.gaussian_blur()

        self.image = cv2.convertScaleAbs(self.image, alpha=random.uniform(0.5, 1.5), beta=random.uniform(-0.3, 0.3))

        self.image = self.image / 255
        noise = np.random.normal(0, random.uniform(0, 0.1), self.image.shape)
        self.image = self.image + noise
        self.image = np.clip(self.image, 0, 1)
        self.image = np.uint8(self.image * 255)
        return self

    def cfa(self):
        w = self.w - (self.w & 1)
        h = self.h - (self.h & 1)
        new_img = np.zeros([h + 3, w + 3, 3], dtype=np.uint8)
        jud = np.zeros([h + 3, w + 3, 3], dtype=np.uint8)
        for i in range(1, h + 1, 2):
            for j in range(1, w + 1, 2):
                new_img[i, j, 0] = self.image[i - 1, j - 1, 0]
                new_img[i + 1, j + 1, 2] = self.image[i, j, 2]
                new_img[i + 1, j, 1] = self.image[i, j - 1, 1]
                new_img[i, j + 1, 1] = self.image[i - 1, j, 1]
                jud[i, j, 0] = 1
                jud[i + 1, j + 1, 2] = 1
                jud[i, j + 1, 1] = 1
                jud[i + 1, j, 1] = 1
        dx = [-1, 0, 1, 1, 1, 0, -1, -1]
        dy = [-1, -1, -1, 0, 1, 1, 1, 0]

        cz_img = np.zeros([h + 3, w + 3, 3], dtype=np.uint8)
        for i in range(1, h + 1):
            for j in range(1, w + 1):
                for k in range(0, 3):
                    if jud[i, j, k] == 1:
                        cz_img[i, j, k] = new_img[i, j, k]
                        continue
                    fz, fm = 0, 0
                    for dxy in range(0, 8):
                        fz += new_img[i + dx[dxy], j + dy[dxy], k]
                        fm += jud[i + dx[dxy], j + dy[dxy], k]
                    cz_img[i, j, k] = np.uint8(fz / fm)
                    # print(i,j,k,fz,fm)

        self.image = cz_img[1:h + 1, 1:w + 1]
        self.h = h
        self.w = w
        return self

    def morie(self):
        new_img = np.zeros([self.h + 2, self.w + 2, 3], dtype=np.uint8)
        for j in range(0, self.h, 3):
            for k in range(0, self.w, 3):
                for i in range(0, 3):
                    new_img[j, k + i, i] = self.image[j, k, i]
                    new_img[j + 1, k + i, i] = self.image[j, k, i]
                    new_img[j + 2, k + i, i] = self.image[j, k, i]
        self.image = new_img[0:self.h, 0:self.w, :]
        self.pro_tran()
        self.liner_blur()
        self.gaussian_blur()
        self.cfa()
        self.gaussian_noise()
        self.image = cv2.fastNlMeansDenoisingColored(self.image)
        return self

def doRST(img,param,M=512,N=512):
    new = attack(None)
    new.image = img
    new.h,new.w = M,N
    new = new.rotate(param[0])
    new = new.resize(abs(param[2]),abs(param[1]))
    new = new.translation(M*param[3],N*param[4])

    return cv2.resize(new.image,(M,N))

new = attack('lena.jpg')
'''
# new.morie()
# cv2.imwrite('rotateTest.jpg', new.image)
img = new.image
t = cv2.cvtColor(new.image, cv2.COLOR_RGB2YUV)
print(t)
x = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)
x = x / 2.5
print(x)
print(x.shape)
'''


def watermark_embedding(x, q=3., b=1,s=2):
    C_m = x.copy()
    L = len(x[s]) // 2
    for l in range(L):
        C = x[s][l]
        C_op = x[s][l+L]
        m, n = C.shape[0], C.shape[1]
        A_sl = 0.
        for C_i in C:
            for C_ij in C_i:
                A_sl += abs(C_ij)
        A_sl /= m * n

        if round(A_sl / q) % 2 == b:
            Q_A = A_sl / abs(A_sl) * (round(abs(A_sl) / q) * q)
        else:
            if math.fmod(abs(A_sl) / q, 1.) <= 0.5:
                Q_A = A_sl / abs(A_sl) * (round(abs(A_sl) / q + 0.5) * q)
            else:
                Q_A = A_sl / abs(A_sl) * (round(abs(A_sl) / q - 0.5) * q)

        for i in range(m):
            for j in range(n):
                C_m[s][l][i][j] = C[i][j] * Q_A / A_sl
                C_m[s][l+L][i][j] = C_op[i][j] * Q_A / A_sl
                #x[s][l][i][j] = x[s][l][i][j] * (4.)

    return C_m

def watermark_decoding(C_m,q=3,s=2):
    b = list()
    L = len(C_m[s]) // 2
    for l in range(L):
        C = C_m[s][l]
        m, n = C.shape[0], C.shape[1]
        A_sl = 0.
        for C_i in C:
            for C_ij in C_i:
                A_sl += abs(C_ij)
        A_sl /= m * n
        b.append(round(A_sl/q) % 2)
    return b

def divide_image(M,N):
    key = [0]*(M*N//2) + [1]*(M*N//2)
    random.shuffle(key)
    key = np.array(key).reshape((M,N))
    return key

def zigZag(M,m,n):
    x,y,k = 0,0,0
    res = [M[0][0]]
    while True:
        if y == 0 and (x+m*2+n*2) % 2 == 0:
            x += 1
        elif y == m-1 and (x+m*2+n*2) % 2 == m%2:
            x += 1
        elif (x+y) % 2 == 1:
            x -= 1
            y += 1
        else:
            x += 1
            y -= 1
        if y < m and x < n and y >= 0 and x >= 0:
            res.append(M[y][x])
        if y == m-1 and x > n-1:
            break
    return res

def noise_fill(M,N,seq,key,m=8,n=8):
    res = np.ndarray((M,N),dtype=np.float32)
    print(key)
    k = 0
    for i in range(M):
        for j in range(N):
            if key[i//m][j//n] == 0:
                print(i,j,k)
                res[i][j] = seq[k]
                k+=1
            else:
                res[i][j] = 0
    return res

if __name__ == '__main_':
    key = divide_image(4,4)
    cof = cv2.cvtColor(new.image, cv2.COLOR_RGB2GRAY)
    cof = cv2.dct(cof.astype(np.float32))[:16,:16]
    m,n = 16,16
    cof = zigZag(cof,m,n)[m*n//4:m*n//4*3]
    res = noise_fill(m,n,cof,key,4,4)
    print(res)
'''
print(divide_image(4,4))
cof = cv2.cvtColor(new.image, cv2.COLOR_RGB2GRAY)
cof = cv2.dct(cof.astype(np.float32))
m,n = cof.shape
print(m,n)
cof = zigZag(cof,m,n)[m*n//4:m*n//4*3]
print(len(cof),m*n)
'''