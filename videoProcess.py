import cv2
import os


fmt = ['mp4','mp5','avi','yuv','rm','rmvb']

class VideoAttack:
    def __init__(self,p=None):
        self.path = os.path.join('.','input')
        if p:
            self.path = os.path.join(self.path,p)

    def getFiles(self):
        self.file_list = list()
        for root, dirs, files in os.walk(self.path):
            print(root, dirs, files)
            for file in files:
                if file.split(".")[1] in fmt:
                    self.file_list.append(os.path.join(root, file))

    def _debug_(self):
        videoCapture = cv2.VideoCapture(self.file_list[0])
        fps = videoCapture.get(cv2.CAP_PROP_FPS)
        size = (int(videoCapture.get(cv2.CAP_PROP_FRAME_WIDTH)),
                int(videoCapture.get(cv2.CAP_PROP_FRAME_HEIGHT)))
        print(fps, size)

        success, frame = videoCapture.read()
        print(frame)

    def rotate(self,angle):
        rangle = np.deg2rad(angle)

'''
while success:
    cv2.imshow('test',frame)
    cv2.waitKey(int(1000 /fps))
    success, frame = videoCapture.read()
'''

