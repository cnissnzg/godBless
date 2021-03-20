import cv2
import os
import numpy as np
import random
import imageProcess

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
            os.system('ffmpeg -i {0} -vf "unsharp=luma_msize_x={1}:luma_msize_y={1}:luma_amount={2}" {3}'.format(self._fullName_(i),str(size),str(rate),self._prefixName_(i,prefix)))

    def chop(self, rate,code):
        prefix = 'chop_' + code + '-' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            if code == 'vertical':
                os.system('ffmpeg -i {0} -filter_complex "[0]crop=iw*{1}:ih:0:0[tmp1];[0]crop=iw-iw*{1}:ih:iw*{1}:0[tmp2];[tmp2][tmp1]hstack" {2}'.format(self._fullName_(i),str(rate),self._prefixName_(i,prefix)))
            else:
                os.system(
                    'ffmpeg -i {0} -filter_complex "[0]crop=iw:ih*{1}:0:0[tmp1];[0]crop=iw:ih-ih*{1}:0:ih*{1}[tmp2];[tmp2][tmp1]vstack" {2}'.format(
                        self._fullName_(i), str(rate), self._prefixName_(i, prefix)))
        return self

    def cut(self,left,right,up,bottom):
        prefix = 'cur_' + str(left) + '-' + str(right) + '-' + str(up) + '-' + str(bottom)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "crop=iw*{1}:ih*{2}:{3}:{4}" {5}'.format(self._fullName_(i),str(left),str(up),str(1-right-left),str(1-up-bottom),self._prefixName_(i,prefix)))
        return self

    def cover(self,height,width):
        prefix = 'cover_' + str(height) + '-' + str(width)
        for i in range(len(self.file_list)):
            videoCapture = cv2.VideoCapture(self._fullName_(i))
            x,y = int(videoCapture.get(cv2.CAP_PROP_FRAME_WIDTH))-width,int(videoCapture.get(cv2.CAP_PROP_FRAME_HEIGHT))-height
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "drawbox=x={1}:y={2}:w={3}:h={4}:c=black:t=fill" {5}'.format(self._fullName_(i),str(x),str(y),str(width),str(height),self._prefixName_(i,prefix)))
        return self

    def brightness(self, rate):
        prefix = 'bri_' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system("ffmpeg -i {0} -vf \"format=pix_fmts=rgb24,geq=r='clip(r(X,Y)+{1},0,255)':g='clip(g(X,Y)+{1},0,255)':b='clip(b(X,Y)+{1},0,255)',format=pix_fmts=yuv420p\" {2}".format(
                self._fullName_(i), str(rate), self._prefixName_(i,prefix)))

    def contrast(self, rate):
        prefix = 'con_' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system("ffmpeg -i {0} -vf \"format=pix_fmts=rgb24,geq=r='clip(r(X,Y)*{1},0,255)':g='clip(g(X,Y)*{1},0,255)':b='clip(b(X,Y)*{1},0,255)',format=pix_fmts=yuv420p\" {2}".format(
                self._fullName_(i), str(rate), self._prefixName_(i,prefix)))

    def saturation(self, rate):
        prefix = 'sat_' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "eq=saturation={1}" {2}'.format(
                self._fullName_(i), str(rate), self._prefixName_(i,prefix)))

    def rotate(self, angle):
        rAngle = np.deg2rad(angle)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, 'rot' + str(angle))))
            os.system('ffmpeg -i {0} -vf "rotate={1}:ow=floor(rotw({1})/2)*2:oh=floor(roth({1})/2)*2" {2}'.format(
                self._fullName_(i), str(rAngle), self._prefixName_(i, 'rot' + str(angle))))

    def hue(self, rate):
        prefix = 'hue_' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "hue=h={1}" {2}'.format(
                self._fullName_(i), str(rate), self._prefixName_(i,prefix)))
        return self

    def resize(self, ratew, rateh):
        prefix = 'resize_' + str(ratew)+'-'+str(rateh)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "scale=w={1}*iw:h={2}*ih" {3}'.format(
                self._fullName_(i), str(ratew),str(rateh), self._prefixName_(i,prefix)))
        return self

    def noise(self, rate):
        #TODO
        rate = int(np.clip(rate,0,100))
        prefix = 'hue_' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "noise=alls={1}:allf=a" {2}'.format(
                self._fullName_(i), str(rate), self._prefixName_(i,prefix)))
        return self

    def flip(self, code):
        prefix = 'flip_' + code
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            if code == 'vertical':
                os.system('ffmpeg -i {0} -vf "vflip" {1}'.format(self._fullName_(i), self._prefixName_(i,prefix)))
            else:
                os.system('ffmpeg -i {0} -vf "hflip" {1}'.format(self._fullName_(i), self._prefixName_(i, prefix)))
        return self

    def pro_tran(self,rate=0.05):
        p1 = [random.uniform(0,rate), random.uniform(0,rate)]
        p2 = [random.uniform((1.-rate), 1.), random.uniform(0, rate)]
        p3 = [random.uniform(0, rate), random.uniform((1.-rate), 1.)]
        p4 = [random.uniform((1.-rate), 1.), random.uniform((1.-rate), 1.)]
        rate = int(np.clip(rate,0,100))
        prefix = 'pers_' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "perspective=x0={1}*W:y0={2}*H:x1={3}*W:y1={4}*H:x2={5}*W:y2={6}*H:x3={7}*W:y3={8}*H:sense=1:interpolation=cubic" {9}'.format(
                self._fullName_(i), str(p1[0]),str(p1[1]), str(p2[0]),str(p2[1]), str(p3[0]),str(p3[1]), str(p4[0]),str(p4[1]), self._prefixName_(i,prefix)))

    def liner_blur(self,rate=1.5):
        prefix = 'dblur_' + str(rate)
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "dblur=radius={1}" {2}'.format(
                self._fullName_(i), str(rate), self._prefixName_(i,prefix)))
        return self

    def translation(self, h, w):
        prefix = 'move_' + str(w)+'-'+str(h)
        if w < 0:
            x = 0
        else:
            x = w
        if h < 0:
            y = 0
        else:
            y = h
        for i in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(i, prefix)))
            os.system('ffmpeg -i {0} -vf "pad=iw+{1}:ih+{2}:{3}:{4}" {5}'.format(
                self._fullName_(i), str(w),str(h),str(x),str(y), self._prefixName_(i,prefix)))
        return self

    def cameraScreen(self):
        prefix = 'cfa_'
        for vn in range(len(self.file_list)):
            os.system('rm {}'.format(self._prefixName_(vn, prefix)))
            videoCapture = cv2.VideoCapture(self._fullName_(vn))
            fourcc = cv2.VideoWriter_fourcc(*'mp4v')
            fps = videoCapture.get(cv2.CAP_PROP_FPS)
            print(fps)
            size = (int(videoCapture.get(cv2.CAP_PROP_FRAME_WIDTH)),
                    int(videoCapture.get(cv2.CAP_PROP_FRAME_HEIGHT)))
            writer = cv2.VideoWriter('.temp.mp4'.format(str(vn)), fourcc, fps, size, True)
            success, frame = videoCapture.read()
            cnt = 0
            out = imageProcess.attack(None,1)
            out.video=1
            while success:
                cnt += 1
                print("frame {}:".format(str(cnt)))
                out.image = frame
                out.w,out.h = size[0],size[1]
                writer.write(out.cameraScreen().image)
                success, frame = videoCapture.read()

            writer.release()
            os.system('rm {}'.format(self._prefixName_(vn, prefix)))
            os.system('ffmpeg -i {0}  -vf  hqdn3d  {1}'.format(
                '.temp.mp4', self._prefixName_(vn,prefix)))
            os.system('rm {}'.format('.temp.mp4'))




'''
videoCapture = cv2.VideoCapture('./input/video_debug/66.mp4')
success, frame = videoCapture.read()
while success:
    print(frame)
    cv2.imshow('test',frame)
    success, frame = videoCapture.read()
'''
test = VideoAttack()
test.getFiles()
test.cameraScreen()
