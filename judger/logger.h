//
// Created by root on 2021/5/6.
//

#ifndef JUDGER_LOGGER_H
#define JUDGER_LOGGER_H

#include "properties.h"
struct logger {
    logger();

    string filename;
    fstream logStream;
    logger(int argc,char* argv[]);

    void writeLog(const string &s);

    virtual ~logger();
};


#endif //JUDGER_LOGGER_H
