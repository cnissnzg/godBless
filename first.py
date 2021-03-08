#coding=utf-8

import copy
import cv2
import time
import math
import glob
import numpy as np
import script

def attack(fname, type):
    img = cv2.imread(fname)

    if type == "ori":
        return img


    if type == "gray":
        return cv2.imread(fname, cv2.IMREAD_GRAYSCALE)

    if type == "redgray":
        return img[:, :, 0]

    if type == "saltnoise":
        for k in range(1000):
            i = int(np.random.random() * img.shape[1])
            j = int(np.random.random() * img.shape[0])
            if img.ndim == 2:
                img[j, i] = 255
            elif img.ndim == 3:
                img[j, i, 0] = 255
                img[j, i, 1] = 255
                img[j, i, 2] = 255
        return img

    if type == "randline":
        cv2.rectangle(img, (384, 0), (510, 128), (0, 255, 0), 3)
        cv2.rectangle(img, (0, 0), (300, 128), (255, 0, 0), 3)
        cv2.line(img, (0, 0), (511, 511), (255, 0, 0), 5)
        cv2.line(img, (0, 511), (511, 0), (255, 0, 255), 5)

        return img

    if type == "cover":
        cv2.circle(img, (256, 256), 63, (0, 0, 255), -1)
        font = cv2.FONT_HERSHEY_SIMPLEX
        cv2.putText(img, 'Just DO it ', (10, 500), font, 4, (255, 255, 0), 2)
        return img

attack_list ={}
attack_list['ori']          = '原图'
#attack_list['blur']         = '模糊'
attack_list['rotate180']    ='旋转180度'
attack_list['rotate90']     = '旋转90度'
attack_list['chop5']        = '剪切掉5%'
attack_list['chop10']       = '剪切掉10%'
attack_list['chop30']       = '剪切掉30%'
attack_list['saltnoise']    ='椒盐噪声'
attack_list['vwm']          = '增加明水印'
attack_list['randline']     = '随机画线'
attack_list['cover']        = '随机遮挡'
attack_list['brighter10']   = '亮度提高10%'
attack_list['darker10']     = '亮度降低10%'


def psnr(im1, im2):
    if im1.shape != im2.shape or len(im2.shape) < 2:
        return 0

    di = im2.shape[0] * im2.shape[1]
    if len(im2.shape) == 3:
        di = im2.shape[0] * im2.shape[1] * im2.shape[2]

    diff = np.abs(im1 - im2)
    rmse = np.sum(diff * diff) / di
    print(rmse)
    psnr = 20 * np.log10(255 / rmse)
    return psnr

def rotate_about_center(src, angle, scale=1.):
    w = src.shape[1]
    h = src.shape[0]
    rangle = np.deg2rad(angle)  # angle in radians
    nw = (abs(np.sin(rangle)*h) + abs(np.cos(rangle)*w))*scale
    nh = (abs(np.cos(rangle)*h) + abs(np.sin(rangle)*w))*scale
    rot_mat = cv2.getRotationMatrix2D((nw*0.5, nh*0.5), angle, scale)
    rot_move = np.dot(rot_mat, np.array([(nw-w)*0.5, (nh-h)*0.5,0]))
    rot_mat[0,2] += rot_move[0]
    rot_mat[1,2] += rot_move[1]
    return cv2.warpAffine(src, rot_mat, (int(math.ceil(nw)), int(math.ceil(nh))), flags=cv2.INTER_LANCZOS4)


def test_blindwm(alg, imgname, wmname, times=1):
    handle = script.dctwm

    if alg == 'DCT':
        handle = script.dctwm
    if alg == 'DWT':
        handle = script.dwtwm

    print('\n##############测试' + alg + '盲提取算法，以及鲁棒性')

    btime = time.time()
    for i in range(times):
        img = cv2.imread('./data/' + imgname)
        wm = cv2.imread('./data/' + wmname, cv2.IMREAD_GRAYSCALE)
        wmd = handle.embed(img, wm)
        outname = './output/' + alg + '_' + imgname

    cv2.imwrite(outname, wmd)
    print('嵌入完成，文件保存在 :{},平均耗时 ：{} 毫秒 ,psnr : {}'.format(outname, int((time.time() - btime) * 1000 / times),
                                                         psnr(img, wmd)))

    for k, v in attack_list.items():
        wmd = attack(outname, k)
        cv2.imwrite('./output/attack/' + k + '_' + imgname, wmd)
        btime = time.time()
        wm = cv2.imread('./data/' + wmname, cv2.IMREAD_GRAYSCALE)
        sim = handle.extract(wmd, wm)
        print('{:10} : 提取水印 {}，提取信息相似度是：{} ,耗时：{} 毫秒.'.format(v, '成功' if sim > 0.7 else '失败', sim,
                                                              int((time.time() - btime) * 1000)))

out = attack('lena.jpg','darker10')
cv2.imwrite('out.jpg',out)