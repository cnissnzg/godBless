#include "properties.h"
#include "problem.h"
#include "logger.h"
#include "compiler.h"
/*
 * judge (language)
 */
int main(int argc,char* argv[]) {
    if(argc < 8){
        cout<<"not enough args!";
        return 0;
    }
    int lang = getLanguage(string(argv[1]));
    problem problemInfo(atoi(argv[2]), atoi(argv[3]), atoi(argv[4]),string(argv[5]),string(argv[6]),string(argv[7]));
    logger logfile(argc,argv);
    compiler tmp(lang,problemInfo,logfile);
    tmp.compile();
    return 0;
}
