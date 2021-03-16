# coding=utf-8

import cv2
import math
import numpy as np
import random
import math
import libjpeg
from numba import jit

@jit(nopython=True)
def gaussian_out(h,w,img):
    for j in range(0, h):
        n = random.uniform(-0.1, 0.1)
        for k in range(0, w):
            for i in range(0, 3):
                img[j, k, i] += n
    return img

@jit(nopython=True)
def cfa_out_I(h,w,new_img,image,jud):
    for i in range(1, h + 1, 2):
        for j in range(1, w + 1, 2):
            new_img[i, j, 0] = image[i - 1, j - 1, 0]
            new_img[i + 1, j + 1, 2] = image[i, j, 2]
            new_img[i + 1, j, 1] = image[i, j - 1, 1]
            new_img[i, j + 1, 1] = image[i - 1, j, 1]
            jud[i, j, 0] = 1
            jud[i + 1, j + 1, 2] = 1
            jud[i, j + 1, 1] = 1
            jud[i + 1, j, 1] = 1
    return new_img,jud

@jit(nopython=True)
def cfa_out_II(h,w,new_img,jud,cz_img,dy,dx):
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
    return cz_img

@jit(nopython=True)
def noise_out(image,h,w):
    def clip(v,l,r):
        if v < l:
            v=l
        elif v > r:
            v=r
        return v
    for i in range(0, h):
        for j in range(0, w):
            s = np.random.normal(0, 5, 3)
            image[i, j, 0] = clip(image[i, j, 0] + s[0], 0, 255)
            image[i, j, 1] = clip(image[i, j, 1] + s[1], 0, 255)
            image[i, j, 2] = clip(image[i, j, 2] + s[2], 0, 255)
    return image

class attack:

    def __init__(self, filename):
        self.compressRate = cv2.IMWRITE_JPEG_QUALITY
        if filename is None:
            return
        self.image = cv2.imread(filename)
        self.h = self.image.shape[0]
        self.w = self.image.shape[1]


    def blur(self,size,rate):
        kernel = np.ones((size, size), np.float32) * rate
        self.image = cv2.filter2D(self.image, -1, kernel)
        return self

    def sharpen(self,size):
        mat = np.ones((size,size),dtype=np.float32) * -1.
        mat[(size+1)/2][(size+1)/2] = size*size
        #mat = np.array([[-1, -1, -1], [-1, 9, -1], [-1, -1, -1]], dtype=np.float32)
        self.image = cv2.filter2D(self.image, cv2.CV_32F, mat)
        self.image = cv2.convertScaleAbs(self.image)
        return self

    def chop(self, rate,code):
        if code == 'vertical':
            self.image[:, self.w - int(self.w * rate):] = self.image[:, :int(self.w * rate)]
        else:
            self.image[self.h - int(self.h * rate):, :] = self.image[:int(self.h * rate), :]
        return self

    def cut(self,left,right,up,bottom):
        self.image = self.image[self.w*left:self.w*(1-right),self.h*up,:self.h*(1-bottom)]
        self.h = self.image.shape[0]
        self.w = self.image.shape[1]
        return self

    def cover(self,height,width):
        x = random.randint(0,self.w-width)
        y = random.randint(0,self.h-height)
        cv2.rectangle(self.image, (x, y), (x+width,y+height),
                      (0, 0, 0), -1)
        return self

    def rotate(self, angle):
        rangle = np.deg2rad(angle)
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

    def hue(self, rate):
        fImg = self.image.astype(np.float32)
        fImg = fImg / 255.0
        hls = cv2.cvtColor(fImg, cv2.COLOR_BGR2HLS)
        hls[:, :, 0] = rate * hls[:, :, 0]
        hls[:, :, 0][hls[:, :, 0] > 360] = 360
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


    def flip(self, code):
        if code == 'vertical':
            self.image = np.fliplr(self.image)
        else:
            self.image = np.flipud(self.image)
        return self

    def liner_blur(self):
        liner_blur_degree = 3
        tran = cv2.getRotationMatrix2D((liner_blur_degree / 2, liner_blur_degree / 2), random.uniform(0, 180.), 1)
        liner_blur_kernel = np.diag(np.ones(liner_blur_degree))
        liner_blur_kernel = cv2.warpAffine(liner_blur_kernel, tran, (liner_blur_degree, liner_blur_degree))
        liner_blur_kernel = liner_blur_kernel / liner_blur_degree
        self.image = cv2.filter2D(self.image, -1, liner_blur_kernel)
        cv2.normalize(self.image, self.image, 0, 255, cv2.NORM_MINMAX)
        self.image = np.array(self.image, dtype=np.uint8)
        return self

    def gaussian_blur(self):
        gaussian_blur_size = 3
        self.image = cv2.GaussianBlur(self.image, ksize=(gaussian_blur_size, gaussian_blur_size), sigmaX=0, sigmaY=0)
        self.image = gaussian_out(self.h,self.w,self.image)
        return self

    def gaussian_noise(self):
        self.h = self.image.shape[0]
        self.w = self.image.shape[1]
        self.image = noise_out(self.image,self.h,self.w)
        return self

    def translation(self,h,w):
        temp = np.zeros((int(self.h + h),int(self.w + w)),np.uint8)
        #print(self.image.shape,h,w)
        temp[int(max(0,h)):int(max(0,h))+int(self.image.shape[0]),int(max(0,w)):int(max(0,w))+int(self.image.shape[1])] = self.image

        self.image = temp
        self.h = self.image.shape[0]
        self.w = self.image.shape[1]
        #mat_trans = np.float32([[1, 0, h], [0, 1, w]])
        #self.image = cv2.warpAffine(self.image, mat_trans, (self.w, self.h))

        return self

    def pro_tran(self):
        w = self.w
        h = self.h
        p1 = [random.uniform(0, w * 0.05), random.uniform(0, h * 0.05)]
        p2 = [random.uniform(w * 0.95, w), random.uniform(0, h * 0.05)]
        p3 = [random.uniform(0, w * 0.05), random.uniform(h * 0.95, h)]
        p4 = [random.uniform(w * 0.95, w), random.uniform(h * 0.95, h)]
        print("pro_tran:",p1,p2,p3,p4)
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


    def cameraScreen(self):
        w = self.w - (self.w & 1)
        h = self.h - (self.h & 1)
        new_img = np.zeros([h + 3, w + 3, 3], dtype=np.uint8)
        jud = np.zeros([h + 3, w + 3, 3], dtype=np.uint8)
        new_img,jud = cfa_out_I(h,w,new_img,self.image,jud)
        dx = [-1, 0, 1, 1, 1, 0, -1, -1]
        dy = [-1, -1, -1, 0, 1, 1, 1, 0]

        cz_img = np.zeros([h + 3, w + 3, 3], dtype=np.uint8)
        cz_img = cfa_out_II(h,w,new_img,jud,cz_img,np.array(dy),np.array(dx))

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
        alpha,beta = random.uniform(0.8, 1.2),random.uniform(-3, 3)
        self.image = cv2.convertScaleAbs(self.image, alpha=alpha, beta=beta)
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
if __name__ == '__main__':
    new = attack('lena.jpg')
    new.cameraScreen()
    cv2.imwrite('dolena.jpg',new.image)
'''
print(divide_image(4,4))
cof = cv2.cvtColor(new.image, cv2.COLOR_RGB2GRAY)
cof = cv2.dct(cof.astype(np.float32))
m,n = cof.shape
print(m,n)
cof = zigZag(cof,m,n)[m*n//4:m*n//4*3]
print(len(cof),m*n)

    def cameraScreen(self):
        self.pro_tran()
        self.liner_blur()
        self.gaussian_blur()

        self.image = self.image / 255
        noise = np.random.normal(0, random.uniform(0, 0.1), self.image.shape)
        self.image = self.image + noise
        self.image = np.clip(self.image, 0, 1)
        self.image = np.uint8(self.image * 255)
        return self
'''