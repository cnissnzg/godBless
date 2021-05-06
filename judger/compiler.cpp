//
// Created by root on 2021/5/6.
//

#include "compiler.h"

void handler_24( int signo)
{
    alarm(0);
}


compiler::compiler(int lang, const problem &problemInfo, logger &logfile) : lang(lang), problemInfo(problemInfo),
                                                                                  logfile(logfile) {



}

void compiler::compile()  {
    clean_workdir(WORK_DIR,logfile);
    chroot(WORK_DIR.c_str());

    int pid = fork();
    if(pid < 0){
        logfile.writeLog("Error fork");
    }else if(pid == 0){
        signal(24,handler_24);
        struct rlimit compileLimit{};
        compileLimit.rlim_cur = C_TIME_LIM;
        compileLimit.rlim_max = C_TIME_LIM;
        setrlimit(RLIMIT_CPU,&compileLimit);
        compileLimit.rlim_max = C_FILE_SIZE * STD_MB;
        compileLimit.rlim_cur = C_FILE_SIZE * STD_MB;
        setrlimit(RLIMIT_FSIZE, &compileLimit);
        compileLimit.rlim_max = C_MEM_LIM * STD_MB;
        compileLimit.rlim_cur = C_MEM_LIM * STD_MB;
        setrlimit(RLIMIT_AS,&compileLimit);

        mount();

        freopen("ce.txt", "w", stderr);
        char *noArgv[] = {(char *) nullptr};
        switch (lang) {
            case 1:
                execv("cmake && make",noArgv);
                break;
            case 2:
                execl("python", "main.py",(char *) nullptr);
            default:
                logfile.writeLog("not support");
        }
        exit(0);
    }
}
