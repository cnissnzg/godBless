#include "HiddenWatermark.h"
#include "DWT.h"

HiddenWatermark::HiddenWatermark(void)
{
}


HiddenWatermark::~HiddenWatermark(void)
{
}

int HiddenWatermark::addWatermark(const string srcImgFilename,const string watermarkImgFilename,const string saveFilename) //����ˮӡ��ͼƬ
{
	Mat srcImg = imread(srcImgFilename,CV_32FC3);
	if(srcImg.data == NULL) //��ȡͼƬ�ļ�ʧ��
		return 1;
	Mat watermarkImg = imread(watermarkImgFilename);
	if(watermarkImg.data == NULL) //��ȡˮӡͼƬ�ļ�ʧ��
		return 2;
	Mat resultImg = addWatermarkCore(srcImg,watermarkImg);
	if(resultImg.data == NULL) //����ˮӡʧ��
		return 3;
	vector<int> saveParams;
	saveParams.push_back(IMWRITE_JPEG_QUALITY); //����JPEGѹ������
	saveParams.push_back(100); //����Ϊ100
	if(!imwrite(saveFilename,resultImg,saveParams)) //����ʧ��
		return 4;
	return 0;
}

int HiddenWatermark::extWatermark(const string srcImgFilename,const string originaImgFilename,const string saveFilename) //��ͼƬ����ȡˮӡ
{
	Mat srcImg = imread(srcImgFilename,CV_32FC3);
	if(srcImg.data == NULL) //��ȡͼƬ�ļ�ʧ��
		return 1;
	Mat originaImg = imread(originaImgFilename);
	if(originaImg.data == NULL) //��ȡԭʼͼƬ�ļ�ʧ��
		return 2;
	Mat watermarkImg = extWatermarkCore(srcImg,originaImg); //��ȡˮӡ
	if(watermarkImg.data == NULL) //��ȡˮӡʧ��
		return 3;
	vector<int> saveParams;
	saveParams.push_back(IMWRITE_JPEG_QUALITY); //����JPEGѹ������
	saveParams.push_back(100); //����Ϊ100
	if(!imwrite(saveFilename,watermarkImg,saveParams)) //����ʧ��
		return 4;
	return 0;
}

Mat HiddenWatermark::addWatermarkCore(Mat srcImage,Mat watermarkImage) //����ˮӡ��ͼƬ����ʵ��
{
	DWT dwt;
	return dwt.enDWT(srcImage);
}

Mat HiddenWatermark::extWatermarkCore(Mat srcImage,Mat originalImage) //��ͼƬ����ȡˮӡ����ʵ��
{
	DWT dwt;
	return dwt.deDWT(srcImage);
}