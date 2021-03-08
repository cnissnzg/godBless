import cv2
#import pyct
#import numpy as np
#import curvelops as cl
import math

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
'''
img = cv2.imread('lena.jpg')
img_yuv = cv2.cvtColor(img,cv2.COLOR_RGB2YUV)
x = cv2.cvtColor(img,cv2.COLOR_RGB2GRAY)[:8,:8]
#y = pyct.fdct2(x.shape,5,8,True,vec=False)
#z = y.fwd(x)
#t = y.inv(z)
print(x)
print('------\n')
print(z)
for t in z:
    print('	',len(t))
    for t1 in t:
        print('		',len(t1),len(t1[0]))

z = watermark_embedding(z)
print(z)
t = y.inv(z)
cv2.imwrite('out.jpg',t)
print(len(watermark_decoding(z)))
'''


