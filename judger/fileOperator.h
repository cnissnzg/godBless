//
// Created by root on 2021/5/7.
//

#ifndef JUDGER_FILEOPERATOR_H
#define JUDGER_FILEOPERATOR_H
#include "logger.h"
void mount();
void clean_workdir(const string &work_dir,logger &logfile);
void setId();
#endif //JUDGER_FILEOPERATOR_H
