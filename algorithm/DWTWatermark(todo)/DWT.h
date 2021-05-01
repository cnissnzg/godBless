/* ��ɢС���任 �� */
#pragma once
#include <opencv2/core.hpp>
using namespace cv;
class DWT
{
public:
	DWT(void);
	~DWT(void);
	Mat enDWT(Mat srcImage); //ͼ�� DWT�任
	Mat deDWT(Mat srcImage); //ͼ�� DWT��任
	void enDWTCore(Mat srcImage, int nLayer);
	void deDWTCore(Mat srcImage, int nLayer);
};

