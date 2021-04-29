import imageProcess
import json
import cv2

class imgINI():

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

    def execute(self,atk):
        for funcName in self.INI:
            func = getattr(atk,funcName['name'])
            funcName.pop('name')
            atk = func(**funcName)

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


test = imgINI('./settings/setting.ini')
test.cameraScreen()
#test.blur(10,0.5)
test.resize(10,10)
test.rotate(45)

test.save()
'''
test.clear()
test.load()
atk = imageProcess.attack('lena.jpg')
test.execute(atk)
cv2.imwrite('dolena.jpg',atk.image)
'''