FROM centos:centos7
COPY trash/get-pip.py /tmp/
RUN yum -y update && yum -y install epel-release &&  yum -y install python36  && \
/usr/bin/python3.6 /tmp/get-pip.py -i http://pypi.douban.com/simple/ --trusted-host pypi.douban.com && yum -y install gcc gcc-c++ && yum -y install libtool make cmake unzip && \
/usr/sbin/useradd -m -u 9810 judger && mkdir /home/judger/etc && mkdir /home/judger/data && \
mkdir /home/judger/log && mkdir /home/judger/run && chown -R judger /home/judger && \
chgrp -R root /home/judger/etc /home/judger/run /home/judger/data && \
chmod 775 /home/judger /home/judger/data /home/judger/etc /home/judger/run && \
ln -s /usr/bin/g++ /usr/local/bin/g++ && ln -s /usr/bin/gcc /usr/local/bin/gcc

COPY judger /home/judger
COPY trash/ippicv_linux_20151201.tgz  trash/opencv-3.2.0.zip  trash/pkg-config-0.29.2.tar.gz /opencv/
RUN cd /opencv && ls && tar -xvf pkg-config-0.29.2.tar.gz && cd pkg-config-0.29.2 && \
./configure --prefix=/usr/local/pkg-config --with-internal-glib && make && make install && \
pkg-config --version && \
yum localinstall -y –nogpgcheck https://download1.rpmfusion.org/free/el/rpmfusion-free-release-7.noarch.rpm && \
yum localinstall -y –nogpgcheck https://download1.rpmfusion.org/nonfree/el/rpmfusion-nonfree-release-7.noarch.rpm && \
rpm --import http://li.nux.ro/download/nux/RPM-GPG-KEY-nux.ro && \
rpm -Uvh http://li.nux.ro/download/nux/dextop/el7/x86_64/nux-dextop-release-0-1.el7.nux.noarch.rpm && \
yum -y install ffmpeg ffmpeg-devel && ffmpeg -version && \
yum -y install python-devel numpy libdc1394-devel libv4l-devel gstreamer-plugins-base-devel && \
cd /opencv && unzip opencv-3.2.0.zip && mkdir -p opencv-3.2.0/3rdparty/ippicv/downloads/linux-808b791a6eac9ed78d32a7666804320e && \
cp /opencv/ippicv_linux_20151201.tgz opencv-3.2.0/3rdparty/ippicv/downloads/linux-808b791a6eac9ed78d32a7666804320e/ && cd opencv-3.2.0 && mkdir build && cd build && \
cmake -D WITH_TBB=ON -D WITH_EIGEN=ON .. BUILD_DOCS=ON -D BUILD_TESTS=OFF -D BUILD_PERF_TESTS=OFF -D BUILD_EXAMPLES=OFF .. WITH_OPENCL=OFF -D WITH_CUDA=OFF -D BUILD_opencv_gpu=OFF -D BUILD_opencv_gpuarithm=OFF -D BUILD_opencv_gpubgsegm=OFF -D BUILD_opencv_gpucodec=OFF -D BUILD_opencv_gpufeatures2d=OFF -D BUILD_opencv_gpufilters=OFF -D BUILD_opencv_gpuimgproc=OFF -D BUILD_opencv_gpulegacy=OFF -D BUILD_opencv_gpuoptflow=OFF -D BUILD_opencv_gpustereo=OFF -D BUILD_opencv_gpuwarping=OFF .. CMAKE_BUILD_TYPE=RELEASE -D CMAKE_INSTALL_PREFIX=/usr/local .. && \
make && make install && cd /etc/ld.so.conf.d && /bin/bash -c 'echo -e "/opt/opencv/lib64 \n /opt/opencv/lib" > /etc/ld.so.conf.d/opencv.conf' && \
ldconfig && mkdir -p /usr/lib/pkgconfig && cp /opencv/opencv-3.2.0/build/unix-install/opencv.pc /usr/lib/pkgconfig/ && \
cp /opencv/opencv-3.2.0/build/unix-install/opencv.pc /usr/lib64/pkgconfig/





