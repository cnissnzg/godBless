FROM centos:centos7

RUN yum -y update && yum -y install epel-release &&  yum -y install python36 && curl -O https://bootstrap.pypa.io/get-pip.py && \
/usr/bin/python3.6 get-pip.py && yum -y install gcc gcc-c++ && yum -y install libtool cmake && \
/usr/sbin/useradd -m -u 9810 judger && mkdir /home/judger/etc && mkdir /home/judger/data && \
mkdir /home/judger/log && mkdir /home/judger/run && chown -R judger /home/judger && \
chgrp -R root /home/judger/etc /home/judger/run /home/judger/data && \
chmod 775 /home/judger /home/judger/data /home/judger/etc /home/judger/run && \
ln -s /usr/bin/g++ /usr/local/bin/g++ && ln -s /usr/bin/gcc /usr/local/bin/gcc



