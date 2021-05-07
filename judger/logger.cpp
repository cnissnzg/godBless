//
// Created by root on 2021/5/6.
//

#include "logger.h"
logger::logger() {
    filename = LOG_DIR + "/log.txt";
    logStream.open(filename, ios::out | ios::app );

}

logger::~logger() {
    if(logStream.is_open())logStream.close();
}

logger::logger(int argc,char* argv[]):logger(){
    for(int i = 1;i < argc;i++){
        logStream<<argv[i]<<" ";
        logStream<<endl;
    }
}

void logger::writeLog(const string &s) {
    logStream<<s<<endl;
}

