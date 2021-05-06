//
// Created by root on 2021/5/5.
//


#include "problem.h"

problem::problem(int timeLimit, int memLimit, int type, const string &inputDir, const string &outputDir,
                 const string &checkDir) : timeLimit(timeLimit), memLimit(memLimit), type(type), inputDir(inputDir),
                                           outputDir(outputDir), checkDir(checkDir) {}

