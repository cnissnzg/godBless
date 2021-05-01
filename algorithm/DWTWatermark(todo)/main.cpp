#include <opencv2/core.hpp>
#include <opencv2/highgui.hpp>
#include "HiddenWatermark.h"
#include <iostream>
using namespace cv;
int main()
{
	HiddenWatermark hiddenWatermark;
	int i = hiddenWatermark.addWatermark("lena.jpg","DCT_ts.jpg","out.jpg"); //����ˮӡ
	int j = hiddenWatermark.extWatermark("out.jpg","lena.jpg","watermark.jpg"); //��ȡˮӡ
	cout<<i<<" "<<j<<endl;
	return 0;
}
