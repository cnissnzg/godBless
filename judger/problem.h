#ifndef JUDGER_PROBLEM_H
#define JUDGER_PROBLEM_H
#include "properties.h"

struct problem{
    int timeLimit;
    int memLimit;
    int type;
    string inputDir;
    string outputDir;
    string checkDir;

    problem(int timeLimit, int memLimit, int type, const string &inputDir, const string &outputDir,
            const string &checkDir);
};
#endif //JUDGER_PROBLEM_H
