//
// Created by root on 2021/5/5.
//

#ifndef JUDGER_PROPERTIES_H
#define JUDGER_PROPERTIES_H
#include<string>
#include<iostream>
#include<cstring>
#include<vector>
using namespace std;
int getLang(const string &s){
    if(s=="C") return 1;
    if(s=="CPP") return 2;
    if(s=="JAVA") return 3;
    if(s=="PY2") return 4;
    return -1;
}


#endif //JUDGER_PROPERTIES_H
