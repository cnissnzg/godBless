#include "code/properties.h"
#include "code/problem.h"
#include "code/logger.h"
#include "code/compiler.h"
#include "code/fileOperator.h"
/*
 * judge (language)
 */
int main(int argc,char* argv[]) {
    if(argc < 3){
        cout<<"not enough args!";
        return 2;
    }
    int lang = getLanguage(string(argv[1]));
    problem problemInfo(lang,string(argv[1]),string(argv[2]));
    logger logfile(argc,argv);
    compiler tmp(problemInfo,logfile);
    int compileRet = tmp.compile();
    if (compileRet != 0) {
        execute_cmd("/bin/cp %s/ce.txt %sce.info", WORK_DIR.c_str(), JUDGER_HOME.c_str());
        clean_workdir(WORK_DIR,logfile);
        logfile.writeLog("compile error");
        return 1;
    }else{
        cout<<"end"<<endl;
        umount(WORK_DIR.c_str());
    }
    return 0;
}
