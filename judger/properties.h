//
// Created by root on 2021/5/5.
//

#ifndef JUDGER_PROPERTIES_H
#define JUDGER_PROPERTIES_H

#include<string>
#include<iostream>
#include<cstring>
#include<vector>
#include<fstream>
#include<cstdarg>
#include "unistd.h"

using namespace std;
const string JUDGER_HOME = "/home/judger/";
const string LOG_DIR = JUDGER_HOME + "log/";
const string WORK_DIR = JUDGER_HOME + "run";

#define BUFFER_SIZE 5120
#define C_TIME_LIM 60
#define C_FILE_SIZE 30
#define STD_MB 1048576
#define C_MEM_LIM 512
#define ID_USER 9810
int execute_cmd(const char * fmt, ...);
int getLanguage(const string &s);

#endif //JUDGER_PROPERTIES_H
