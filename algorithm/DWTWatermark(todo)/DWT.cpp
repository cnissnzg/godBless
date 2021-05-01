#include "DWT.h"
#include <opencv2/imgproc.hpp>
#include <opencv2/highgui.hpp>
#include <vector>
#include "opencv2/imgproc/imgproc_c.h"
using namespace std;
DWT::DWT(void)
{
}


DWT::~DWT(void)
{
}

Mat DWT::enDWT(Mat srcImage) //ͼ�� DWT�任
{
	Mat dwtImage = srcImage.clone(); //����Դͼ�񸱱���DWT�����ڸ����Ͻ��У�����Դͼ��
	int nLayer = 1; //DWT�任����
	Size waveletSize;
	/* ��ʼ����DWTͼ���С*/
	waveletSize.height = dwtImage.size().height;
	waveletSize.width = dwtImage.size().width;
	if ((dwtImage.size().width >> nLayer) << nLayer != dwtImage.size().width)
		waveletSize.width = ((dwtImage.size().width >> nLayer) + 1) << nLayer;
	if((dwtImage.size().height >> nLayer) << nLayer != dwtImage.size().height)
		waveletSize.height = ((dwtImage.size().height >> nLayer) + 1) << nLayer;
	/* �������� */
	Mat waveletImg(waveletSize,CV_32FC3); //DWT���ͼ��
	if (waveletImg.data != NULL)
	{
		Mat waveletImgROI(waveletImg,Rect(0, 0, dwtImage.size().width, dwtImage.size().height));  //��Чͼ������
		dwtImage.convertTo(waveletImgROI, CV_32FC3, 1, -128); //������Чͼ������
		vector<Mat> channelImgs; //ͨ��������ͼ��
		split(waveletImg,channelImgs); //��ͨ������ͼ��
		for(int i = 0; i < waveletImg.channels(); i++)
			enDWTCore(channelImgs[i], nLayer); //�Ը�ͨ������DWT
		merge(channelImgs,waveletImg); //�ϲ�DWT��ĸ�ͨ��
		waveletImgROI.convertTo(dwtImage, CV_32FC3, 1, 128); //����DWT�����Чͼ������
	}
	return dwtImage;
}

Mat DWT::deDWT(Mat srcImage) //ͼ�� DWT��任
{
	Mat dwtImage = srcImage.clone(); //����Դͼ�񸱱���DWT��任�����ڸ����Ͻ��У�����Դͼ��
	int nLayer = 1; //DWT�任����
	Size waveletSize;
	/* ��ʼ����DWT��任ͼ���С*/
	waveletSize.height = dwtImage.size().height;
	waveletSize.width = dwtImage.size().width;
	if ((dwtImage.size().width >> nLayer) << nLayer != dwtImage.size().width)
		waveletSize.width = ((dwtImage.size().width >> nLayer) + 1) << nLayer;
	if((dwtImage.size().height >> nLayer) << nLayer != dwtImage.size().height)
		waveletSize.height = ((dwtImage.size().height >> nLayer) + 1) << nLayer;
	/* �������� */
	Mat waveletImg(waveletSize,CV_32FC3); //DWT��任���ͼ��
	if (waveletImg.data != NULL)
	{
		Mat waveletImgROI(waveletImg,Rect(0, 0, dwtImage.size().width, dwtImage.size().height));  //��Чͼ������
		dwtImage.convertTo(waveletImgROI, CV_32FC3, 1, -128); //������Чͼ������
		vector<Mat> channelImgs; //ͨ��������ͼ��
		split(waveletImg,channelImgs); //��ͨ������ͼ��
		for(int i = 0; i < waveletImg.channels(); i++)
			deDWTCore(channelImgs[i], nLayer); //�Ը�ͨ������DWT��任
		merge(channelImgs,waveletImg); //�ϲ�DWT��任��ĸ�ͨ��
		waveletImgROI.convertTo(dwtImage, CV_32FC3, 1, 128); //����DWT��任�����Чͼ������
	}
	return dwtImage;
}

void DWT::enDWTCore(Mat srcImage, int nLayer) //��ͨ������ DWT�任
{
	//IplImage* pImage = &srcImage.operator IplImage();
	Mat pImage = srcImage.clone();
	if (!pImage.empty())
	{
		if (pImage.channels() == 1 && pImage.depth() == IPL_DEPTH_32F && ((pImage.size().width >> nLayer) << nLayer) == pImage.size().width && ((pImage.size().height >> nLayer) << nLayer) == pImage.size().height)
		{
			int i, x, y, n;
			float fValue = 0;
			float fRadius = sqrt(2.0f);
			int nWidth = pImage.size().width;
			int nHeight = pImage.size().height;
			int nHalfW = nWidth / 2;
			int nHalfH = nHeight / 2;
			float **pData = new float*[pImage.size().height];
			float *pRow = new float[pImage.size().width];
			float *pColumn = new float[pImage.size().height];
			for (i = 0; i < pImage.size().height; i++)
				pData[i] = pImage.ptr<float>(i);
			for (n = 0; n < nLayer; n++, nWidth /= 2, nHeight /= 2, nHalfW /= 2, nHalfH /= 2) //���С���任
			{
				for (y = 0; y < nHeight; y++) //ˮƽ�任
				{
					memcpy(pRow, pData[y], sizeof(float) * nWidth); //��ż����
					for (i = 0; i < nHalfW; i++)
					{
						x = i * 2;
						pData[y][i] = pRow[x];
						pData[y][nHalfW + i] = pRow[x + 1];
					}
					for(i = 0; i < nHalfW - 1; i++)  //����С���任
					{
						fValue = (pData[y][i] + pData[y][i + 1]) / 2;
						pData[y][nHalfW + i] -= fValue;
					}
					fValue = (pData[y][nHalfW - 1] + pData[y][nHalfW - 2]) / 2;
					pData[y][nWidth - 1] -= fValue;
					fValue = (pData[y][nHalfW] + pData[y][nHalfW + 1]) / 4;
					pData[y][0] += fValue;
					for(i = 1; i < nHalfW; i++)
					{
						fValue = (pData[y][nHalfW + i] + pData[y][nHalfW + i - 1]) / 4;
						pData[y][i] += fValue;
					}
					for(i = 0; i < nHalfW; i++) //Ƶ��ϵ��
					{
						pData[y][i] *= fRadius;
						pData[y][nHalfW + i] /= fRadius;
					}
				}
				for (x = 0; x < nWidth; x++) //��ֱ�任
				{
					for(i = 0; i < nHalfH; i++)  //��ż����
					{
						y = i * 2;
						pColumn[i] = pData[y][x];
						pColumn[nHalfH + i] = pData[y + 1][x];
					}
					for(i = 0; i < nHeight; i++)
					{
						pData[i][x] = pColumn[i];
					}
					for(i = 0; i < nHalfH - 1; i++) // ����С���任
					{
						fValue = (pData[i][x] + pData[i + 1][x]) / 2;
						pData[nHalfH + i][x] -= fValue;
					}
					fValue = (pData[nHalfH - 1][x] + pData[nHalfH - 2][x]) / 2;
					pData[nHeight - 1][x] -= fValue;
					fValue = (pData[nHalfH][x] + pData[nHalfH + 1][x]) / 4;
					pData[0][x] += fValue;
					for(i = 1; i < nHalfH; i++)
					{
						fValue = (pData[nHalfH + i][x] + pData[nHalfH + i - 1][x]) / 4;
						pData[i][x] += fValue;
					}
					for(i = 0; i < nHalfH; i++)  //Ƶ��ϵ��
					{
						pData[i][x] *= fRadius;
						pData[nHalfH + i][x] /= fRadius;
					}
				}
			}
			delete[] pData;
			delete[] pRow;
			delete[] pColumn;
		}
	}
	pImage.copyTo(srcImage);
	//Mat dwtImg(pImage);
	///dwtImg.copyTo(srcImage);
}

void DWT::deDWTCore(Mat srcImage, int nLayer) //��ͨ������ DWT��任
{
	Mat pImage = srcImage.clone();
	//IplImage *pImage = &cvIplImage(srcImage);
	if (!pImage.empty())
	{
		if (pImage.channels() == 1 && pImage.depth() == IPL_DEPTH_32F && ((pImage.size().width >> nLayer) << nLayer) == pImage.size().width && ((pImage.size().height >> nLayer) << nLayer) == pImage.size().height)
		{
			int i, x, y, n;
			float fValue = 0;
			float fRadius = sqrt(2.0f);
			int nWidth = pImage.size().width >> (nLayer - 1);
			int nHeight = pImage.size().height >> (nLayer - 1);
			int nHalfW = nWidth / 2;
			int nHalfH = nHeight / 2;
			float **pData = new float*[pImage.size().height];
			float *pRow = new float[pImage.size().width];
			float *pColumn = new float[pImage.size().height];
			for(i = 0; i < pImage.size().height; i++)
			{
				pData[i] = pImage.ptr<float>(i);
			}
			for(n = 0; n < nLayer; n++, nWidth *= 2, nHeight *= 2, nHalfW *= 2, nHalfH *= 2) //���С���ָ�
			{
				for(x = 0; x < nWidth; x++) //��ֱ�ָ�
				{
					for(i = 0; i < nHalfH; i++) //Ƶ��ϵ��
					{
						pData[i][x] /= fRadius;
						pData[nHalfH + i][x] *= fRadius;
					}
					fValue = (pData[nHalfH][x] + pData[nHalfH + 1][x]) / 4; //����С���ָ�
					pData[0][x] -= fValue;
					for(i = 1; i < nHalfH; i++)
					{
						fValue = (pData[nHalfH + i][x] + pData[nHalfH + i - 1][x]) / 4;
						pData[i][x] -= fValue;
					}
					for(i = 0; i < nHalfH - 1; i++)
					{
						fValue = (pData[i][x] + pData[i + 1][x]) / 2;
						pData[nHalfH + i][x] += fValue;
					}
					fValue = (pData[nHalfH - 1][x] + pData[nHalfH - 2][x]) / 2;
					pData[nHeight - 1][x] += fValue;
					for(i = 0; i < nHalfH; i++) //��ż�ϲ�
					{
						y = i * 2;
						pColumn[y] = pData[i][x];
						pColumn[y + 1] = pData[nHalfH + i][x];
					}
					for(i = 0; i < nHeight; i++)
						pData[i][x] = pColumn[i];
				}
				for (y = 0; y < nHeight; y++) //ˮƽ�ָ�
				{
					for (i = 0; i < nHalfW; i++) //Ƶ��ϵ��
					{
						pData[y][i] /= fRadius;
						pData[y][nHalfW + i] *= fRadius;
					}
					fValue = (pData[y][nHalfW] + pData[y][nHalfW + 1]) / 4; //����С���ָ�
					pData[y][0] -= fValue;
					for (i = 1; i < nHalfW; i++)
					{
						fValue = (pData[y][nHalfW + i] + pData[y][nHalfW + i - 1]) / 4;
						pData[y][i] -= fValue;
					}
					for (i = 0; i < nHalfW - 1; i++)
					{
						fValue = (pData[y][i] + pData[y][i + 1]) / 2;
						pData[y][nHalfW + i] += fValue;
					}
					fValue = (pData[y][nHalfW - 1] + pData[y][nHalfW - 2]) / 2;
					pData[y][nWidth - 1] += fValue;
					for (i = 0; i < nHalfW; i++) //��ż�ϲ�
					{
						x = i * 2;
						pRow[x] = pData[y][i];
						pRow[x + 1] = pData[y][nHalfW + i];
					}
					memcpy(pData[y], pRow, sizeof(float) * nWidth);
				}
			}
			delete[] pData;
			delete[] pRow;
			delete[] pColumn;
		}
	}
	pImage.copyTo(srcImage);
	//Mat dwtImg(pImage,true);
	//dwtImg.copyTo(srcImage);
}