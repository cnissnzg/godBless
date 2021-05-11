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

    string problemid;
    string sourceId;

    problem(int timeLimit, int memLimit, int type, const string &inputDir, const string &outputDir,
            const string &checkDir);

    problem(int _type,const string &_pid,const string &_sourceId);

};
#endif //JUDGER_PROBLEM_H
