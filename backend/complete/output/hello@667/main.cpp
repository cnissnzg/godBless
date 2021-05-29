#include <iostream>
#include <fstream>
#include <vector>
#include <sys/io.h>
#include <opencv2/opencv.hpp>
#include "header/Watermark_Text.h"

using namespace std;
using namespace cv;

int main()
{
	WaterMarkText wmt;
	Mat img = imread("../image/wallhaven-137628.jpg");
	Mat imgFly = wmt.getWatermarkColorImage(img);
	imwrite("../image/imgOriFly.jpg", imgFly * 255);
	Mat result = wmt.addTextWatermarkColorImage(img, "CS SWJTU");

	Mat resultsave = result * 255;
	imwrite("../image/resultsave-1.jpg", resultsave);
	Mat imgWatermark1 = imread("../image/resultsave-1.jpg");
	Mat imgFly1 = wmt.getWatermarkColorImage(imgWatermark1);

	imwrite("../image/testImgFly1.jpg", imgFly1 * 255);
	imwrite("../image/resultsave-2.bmp", resultsave);
	Mat imgWatermark2 = imread("../image/resultsave-2.bmp");
	Mat imgFly2 = wmt.getWatermarkColorImage(imgWatermark2);

	imwrite("../image/testImgFly2.bmp", imgFly2 * 255);


	return 0;
}