//
// Created by root on 2021/5/7.
//
#include "properties.h"
int execute_cmd(const char * fmt, ...) {
    char cmd[BUFFER_SIZE];

    int ret = 0;
    va_list ap;

    va_start(ap, fmt);
    vsprintf(cmd, fmt, ap);
    ret = system(cmd);
    va_end(ap);
    return ret;
}
int getLanguage(const string &s){
if(s=="C") return 1;
if(s=="CPP") return 2;
if(s=="JAVA") return 3;
if(s=="PY2") return 4;
return -1;
}
