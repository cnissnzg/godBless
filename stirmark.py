import imageProcess
import json
import cv2
import os

INI_PATH = './settings/setting.ini'
INPUT_PATH = './input/'
OUTPUT_PATH = './output/'

class commonINI():

    def __init__(self, path):
        self.path = path
        self.INI = list()

    def clear(self):
        self.INI.clear()

    def save(self):
        with open(self.path,'w') as f:
            f.write(json.dumps(self.INI))

    def load(self):
        with open(self.path, 'r') as f:
            self.INI = json.load(f)
        print(self.INI)

    def execute(self,filename):
        f = filename.split('.')
        f[1] = '.'+f[1]
        i = 0
        for funcName in self.INI:
            atk = imageProcess.attack(INPUT_PATH+f[0]+f[1])
            func = getattr(atk,funcName['cid'])
            funcName.pop('cid')
            atk = func(**funcName)
            dirs = OUTPUT_PATH+f[0]
            if not os.path.exists(dirs):
                os.makedirs(dirs)
            cv2.imwrite(dirs+'/'+f[0]+str(i)+f[1],atk.image)
            i+=1

    def blur(self, size,rate):
        self.INI.append({
            "cid": "blur",
            "size":size,
            "rate": rate
        })


    def contrast(self, rate):
        self.INI.append({
            "cid": "contrast",
            "rate": rate
        })

    def saturation(self, rate):
        self.INI.append({
            "cid": "saturation",
            "rate": rate
        })


    def hue(self, rate):
        self.INI.append({
            "cid": "hue",
            "rate": rate
        })

    def sharpen(self, size):
        self.INI.append({
            "cid": "sharpen",
            "size": size
        })

    def rotate(self,angle):
        self.INI.append({
            "cid": "rotate",
            "angle": angle
        })

    def flip(self,code):
        self.INI.append({
            "cid": "flip",
            "code": code
        })

    def cut(self,left,right,up,bottom):
        self.INI.append({
            "cid": "cut",
            "left": left,
            "right":right,
            "up":up,
            "bottom":bottom
        })

    def cover(self,height,width):
        self.INI.append({
            "cid": "cover",
            "height": height,
            "width":width
        })

    def chop(self,rate,code):
        self.INI.append({
            "cid": "chop",
            "rate": rate,
            "code":code
        })

    def resize(self,ratew,rateh):
        self.INI.append({
            "cid": "resize",
            "ratew": ratew,
            "rateh": rateh
        })

    def translation(self,h,w):
        self.INI.append({
            "cid": "translation",
            "height": h,
            "width": w
        })

    def cameraScreen(self):
        self.INI.append({
            "cid": "cameraScreen"
        })

    def pro_tran(self):
        self.INI.append({
            "cid": "pro_tran"
        })

    def liner_blur(self):
        self.INI.append({
            "cid": "liner_blur"
        })

class imgINI(commonINI):

    def jpeg(self,rate):
        self.INI.append({
            "cid": "jpeg",
            "rate": rate,
        })

    def saltNoise(self,num):
        self.INI.append({
            "cid": "saltNoise",
            "rate": num,
        })

class videoINI(commonINI):

    def debug(self):
        print("debug")


test = imgINI(INI_PATH)
test.cameraScreen()
#test.blur(10,0.5)
test.resize(0.8,0.9)
test.rotate(45)

test.save()

test.clear()
test.load()

test.execute('lena.jpg')
