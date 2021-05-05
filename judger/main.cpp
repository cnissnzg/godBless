#include "properties.h"
#include "problem.h"

/*
 * judge (language)
 */
int main(int argc,char* argv[]) {
    int lang = getLang(string(argv[1]));
    problem problemInfo(atoi(argv[2]), atoi(argv[3]), atoi(argv[4]),string(argv[5]),string(argv[6]),string(argv[7]));

    return 0;
}
