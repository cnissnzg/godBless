//
// Created by root on 2021/5/6.
//

#ifndef JUDGER_COMPILER_H
#define JUDGER_COMPILER_H
#include "properties.h"
#include "problem.h"
#include "logger.h"

#include "fileOperator.h"

#include <sys/resource.h>
#include <sys/wait.h>
#include <csignal>
#ifdef __i386
#define REG_SYSCALL orig_eax
#define REG_RET eax
#define REG_ARG0 ebx
#define REG_ARG1 ecx
#else
#define REG_SYSCALL orig_rax
#define REG_RET rax
#define REG_ARG0 rdi
#define REG_ARG1 rsi

#endif
class compiler {
    int lang;
    problem problemInfo;
    logger &logfile;
public:
    compiler(int lang, const problem &problemInfo, logger &logfile);
    int compile();
};


#endif //JUDGER_COMPILER_H
