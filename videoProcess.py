import cv2
import os
import numpy as np

fmt = ['mp4','mp5','avi','yuv','rm','rmvb']

class VideoAttack:
    def __init__(self,inp=None,outp=None,prefix=None):
        self.input_path = os.path.join('.','input')
        self.out_path = os.path.join('.', 'output')
        self.temp_path = os.path.join('.', 'temp')
        self.prefix = prefix
        if inp:
            self.input_path = os.path.join('.',inp)
        if outp:
            self.out_path = os.path.join('.',outp)


    def getFiles(self):
        self.file_list = list()
        for root, dirs, files in os.walk(self.input_path):
            print(root, dirs, files)
            for file in files:
                if file.split(".")[1] in fmt:
                    self.file_list.append((root,file))

    def _fullName_(self,n):
        return os.path.join(self.file_list[n][0],self.file_list[n][1])


    def _prefixName_(self,n,prefix=''):
        if self.prefix:
            return os.path.join(self.out_path, self.prefix + '_' + self.file_list[n][1])
        elif prefix:
            return os.path.join(self.out_path,prefix+'_'+self.file_list[n][1])
        else:
            return os.path.join(self.out_path, self.file_list[n][1])

    def _debug_(self):
        videoCapture = cv2.VideoCapture(self._fullName_(0))
        fps = videoCapture.get(cv2.CAP_PROP_FPS)
        size = (int(videoCapture.get(cv2.CAP_PROP_FRAME_WIDTH)),
                int(videoCapture.get(cv2.CAP_PROP_FRAME_HEIGHT)))
        print(fps, size)

        success, frame = videoCapture.read()
        print(frame)

    def blur(self,size,rate):
        prefix = 'blur'+ str(size)+'-'+str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i,prefix)))
            os.system('ffmpeg -i {0} -vf "gblur=sigma={1}:steps={2}" {3}'.format(self._fullName_(i),str(rate),str(size),self._prefixName_(i,prefix)))

    def sharpen(self,size,rate=3):
        prefix = 'sharpen' + str(size) + '-' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "unsharp=luma_msize_x={1}:luma_msize_y={1}:luma_amount={2}" {3}'.format(self._fullName_(i),size,rate,self._prefixName_(i,prefix)))



    def rotate(self, angle):
        rAngle = np.deg2rad(angle)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, 'rot' + str(angle))))
            os.system('ffmpeg -i {0} -vf "rotate={1}:ow=floor(rotw({1})/2)*2:oh=floor(roth({1})/2)*2" {2}'.format(
                self._fullName_(i), str(rAngle), self._prefixName_(i, 'rot' + str(angle))))


'''
while success:
    cv2.imshow('test',frame)
    cv2.waitKey(int(1000 /fps))
    success, frame = videoCapture.read()
'''
test = VideoAttack()
test.getFiles()
test.blur(1,0.6)
