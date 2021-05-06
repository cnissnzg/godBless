#include "properties.h"
#include "problem.h"
#include "logger.h"
#include "compiler.h"
#include "fileOperator.h"
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
    int compileRet = tmp.compile();
    if (compileRet != 0) {
        execute_cmd("/bin/cp '%s'/ce.txt %s/ce.info", WORK_DIR.c_str(), JUDGER_HOME.c_str());
        clean_workdir(WORK_DIR,logfile);
        logfile.writeLog("compile error");
        return -1;
    }else{
        umount(WORK_DIR.c_str());
    }
    return 0;
}
