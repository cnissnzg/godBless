#include "properties.h"
#include "problem.h"
#include "logger.h"
#include "compiler.h"
#include "fileOperator.h"
/*
 * judge (language)
 */
int main(int argc,char* argv[]) {
    if(argc < 3){
        cout<<"not enough args!";
        return -2;
    }
    int lang = getLanguage(string(argv[1]));
    problem problemInfo(lang,string(argv[1]),string(argv[2]));
    logger logfile(argc,argv);
    compiler tmp(problemInfo,logfile);
    int compileRet = tmp.compile();
    if (compileRet != 0) {
        execute_cmd("/bin/cp %s/ce.txt %s/ce.info", WORK_DIR.c_str(), JUDGER_HOME.c_str());
        clean_workdir(WORK_DIR,logfile);
        logfile.writeLog("compile error");
        return -1;
    }else{
        umount(WORK_DIR.c_str());
    }
    return 0;
}
