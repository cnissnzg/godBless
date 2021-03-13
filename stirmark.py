import imageProcess
import json


class setINI():

    def __init__(self, path):
        self.path = path
        self.INI = list()

    def clear(self):
        self.INI.clear()

    def blur(self, size,rate):
        self.INI.append({
            "name": "blur",
            "size":size,
            "rate": rate
        })


    def contrast(self, rate):
        self.INI.append({
            "name": "contrast",
            "rate": rate
        })

    def saturation(self, rate):
        self.INI.append({
            "name": "saturation",
            "rate": rate
        })


    def hue(self, rate):
        self.INI.append({
            "name": "hue",
            "rate": rate
        })

    def sharpen(self, size):
        self.INI.append({
            "name": "sharpen",
            "size": size
        })

    def rotate(self,angle):
        self.INI.append({
            "name": "rotate",
            "size": angle
        })

