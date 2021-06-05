//
// Created by root on 2021/5/6.
//

#include "compiler.h"

void handler_24( int signo)
{
    alarm(0);
}

compiler::compiler(const problem &problemInfo, logger &logfile) : problemInfo(problemInfo),
                                                                                  logfile(logfile) {}

int compiler::compile()  {
    chdir(WORK_DIR.c_str());
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

        setId();
        switch (problemInfo.type) {
            case 1:
                cout<<execute_cmd("cmake . >%s 2&>1 && make >%s 2&>1","cmake.log","make.log");
                break;
            case 2:
                execl("python", "main.py",(char *) nullptr);
            default:
                logfile.writeLog("not support");
        }
        exit(0);
    }else{
        int status = 0;
        waitpid(pid, &status, 0);
        execute_cmd("/bin/umount bin usr lib lib64 etc/alternatives proc dev 2>/dev/null");
        execute_cmd("/bin/umount %s/* 2>/dev/null",WORK_DIR.c_str());
        cout<<"fa"<<endl;
        return status;
    }
}
