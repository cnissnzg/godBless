#!/usr/bin/python3

import sys
import os

path = '.'

video_path = path + '/input/video/' + sys.argv[1]
if os.path.exists(video_path+'/gif'):
    os.system('rm -r '+video_path+'/gif')
os.mkdir(video_path+'/gif')
for file in os.listdir(video_path):
    if file == 'gif':
        continue
    prefix = file.split('.')[0]
    if os.system('ffmpeg -i '+video_path+'/'+file+' -ss 0 -t 3 '+video_path+'/gif/'+prefix+'.gif') != 0:
        exit(1)
