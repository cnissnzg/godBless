#include <iostream>

int main(int argc,char* argv[]) {
    std::cout << "Now insert watermark"<<argv[3]<<"from "<<argv[1]<<" into " <<argv[2]<< std::endl;
    char cmd[100];
    sprintf(cmd,"cp -f %s %s",argv[1],argv[2]);
    system(cmd);
    return 0;
}
