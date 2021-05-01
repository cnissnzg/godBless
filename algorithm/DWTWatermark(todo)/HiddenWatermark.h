#pragma once
#include <opencv2/core.hpp>
#include <opencv2/highgui.hpp>
using namespace cv;
using namespace std;
class HiddenWatermark
{
public:
	HiddenWatermark(void);
	~HiddenWatermark(void);
	int addWatermark(const string srcImgFilename,const string watermarkImgFilename,const string saveFilename); //����ˮӡ��ͼƬ
	int extWatermark(const string srcImgFilename,const string originaImgFilename,const string saveFilename); //��ͼƬ����ȡˮӡ
private:
	Mat addWatermarkCore(Mat srcImage,Mat watermarkImage); //����ˮӡ��ͼƬ����ʵ��
	Mat extWatermarkCore(Mat srcImage,Mat originalImage); //��ͼƬ����ȡˮӡ����ʵ��
};

