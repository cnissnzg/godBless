//
// Created by root on 2021/5/7.
//
#include "fileOperator.h"
void umount(const char * work_dir){
    execute_cmd("/bin/umount %s/proc 2>/dev/null", work_dir);
    execute_cmd("/bin/umount %s/dev 2>/dev/null", work_dir);
    execute_cmd("/bin/umount %s/lib 2>/dev/null", work_dir);
    execute_cmd("/bin/umount %s/lib64 2>/dev/null", work_dir);
    execute_cmd("/bin/umount %s/etc/alternatives 2>/dev/null", work_dir);
    execute_cmd("/bin/umount %s/usr 2>/dev/null", work_dir);
    execute_cmd("/bin/umount %s/bin 2>/dev/null", work_dir);
    execute_cmd("/bin/umount %s/proc 2>/dev/null", work_dir);
    execute_cmd("/bin/umount bin usr lib lib64 etc/alternatives proc dev 2>/dev/null");
    execute_cmd("/bin/umount %s/* 2>/dev/null",work_dir);
}
void clean_workdir(const string &work_dir,logger &logfile) {
    umount(work_dir.c_str());
    logfile.writeLog(work_dir+"\n");
    execute_cmd("/bin/rm -fr %s/*", work_dir.c_str());
}
void mount(){
    execute_cmd("mkdir -p bin usr lib lib64 etc/alternatives proc tmp dev");
    execute_cmd("chown judge *");
    execute_cmd("mount -o bind /bin bin");
    execute_cmd("mount -o bind /usr usr");
    execute_cmd("mount -o bind /lib lib");
#ifndef __i386
    execute_cmd("mount -o bind /lib64 lib64");
#endif
    execute_cmd("mount -o bind /etc/alternatives etc/alternatives");
    execute_cmd("mount -o bind /proc proc");
    execute_cmd("mount -o bind /dev dev");
}
void setId(){
    while(setgid(ID_USER)!=0) sleep(1);
    while(setuid(ID_USER)!=0) sleep(1);
    while(setresuid(ID_USER, ID_USER, ID_USER)!=0) sleep(1);
}
