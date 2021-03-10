# encoding=utf-8
import torch
import torchvision
import torchvision.transforms as transforms
BATCH_SIZE = 32


def loadTraindata():
    path = "/home/hit/train"
    trainSet = torchvision.datasets.ImageFolder(path, transform=transforms.Compose(
        [transforms.Grayscale(), transforms.ToTensor()]))
    trainLoader = torch.utils.data.DataLoader(trainSet, batch_size=32, shuffle=True, num_workers=0)
    return trainLoader


trainData = loadTraindata()
torch.zeros(1).cuda()
'''
for step, (data, y) in enumerate(trainData):
    print(step, data.size())
    break
'''