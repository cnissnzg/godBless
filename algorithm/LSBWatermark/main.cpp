#include <iostream>
#include <opencv2/opencv.hpp>

using namespace std;
using namespace cv;

/*
���ܣ��ڿ���Ӧ���ټ�ʽ����
������aFrontImage:ԭʼ��Ƭ
������aHiddenImage:��Ҫ���ص�ˮӡ��Ƭ
*/
Mat steganograph(Mat aFrontImage, Mat aHiddenImage)
{
	// �������ͼ��������Ƿ�һ��
	CV_Assert(aFrontImage.type() == aHiddenImage.type() && aFrontImage.size() == aHiddenImage.size());

	// �����Ƭ�Ĵ�С��һ��
	if (aFrontImage.size() != aHiddenImage.size())
	{
		// �ѻҶ�ͼ��������Ͻǣ����ұߺ��±���չͼ����չ�������Ϊ0��
		copyMakeBorder(aHiddenImage, aHiddenImage, 0, aFrontImage.rows - aHiddenImage.rows, 0, aFrontImage.cols - aHiddenImage.cols, BORDER_CONSTANT, Scalar::all(0));
	}

	// �洢���ͼ��
	Mat aStegedImage(aFrontImage.rows, aFrontImage.cols, aFrontImage.type());
	// ��ʱ����
	Mat tFront_image, tHidden_image;

	// ����Ƿ�Ҫ������ɫͼ��
	if (aFrontImage.channels() == 3)
	{
		// ��ģ��������ڶ�����ֵ0xF0��11110000
		Mat front_mask(aFrontImage.rows, aFrontImage.cols, aFrontImage.type(), Scalar(0xF0, 0xF0, 0xF0));
		Mat hidden_mask(aHiddenImage.rows, aHiddenImage.cols, aHiddenImage.type(), Scalar(0xF0, 0xF0, 0xF0));

		// ���а�λ��������洢����ʱͼ������
		bitwise_and(aFrontImage, front_mask, tFront_image);
		bitwise_and(aHiddenImage, hidden_mask, tHidden_image);

		for (int j = 0; j < aHiddenImage.rows; j++)
			for (int i = 0; i < aHiddenImage.cols; i++)
			{
				// ��������4λ��ǰ��λ�������
				tHidden_image.at<Vec3b>(j, i)[0] = tHidden_image.at<Vec3b>(j, i)[0] >> 4;
				tHidden_image.at<Vec3b>(j, i)[1] = tHidden_image.at<Vec3b>(j, i)[1] >> 4;
				tHidden_image.at<Vec3b>(j, i)[2] = tHidden_image.at<Vec3b>(j, i)[2] >> 4;
			}
	}
	// ��������Ҷ�ͼ��
	else if (aFrontImage.channels() == 1)
	{
		Mat front_mask(aFrontImage.rows, aFrontImage.cols, aFrontImage.type(), Scalar(0xF0));
		Mat hidden_mask(aHiddenImage.rows, aHiddenImage.cols, aHiddenImage.type(), Scalar(0xF0));

		bitwise_and(aFrontImage, front_mask, tFront_image);
		bitwise_and(aHiddenImage, hidden_mask, tHidden_image);

		for (int j = 0; j < aHiddenImage.rows; j++)
			for (int i = 0; i < aHiddenImage.cols; i++)
			{
				tHidden_image.at<uchar>(j, i) = tHidden_image.at<uchar>(j, i) >> 4;
			}
	}

	// ִ�а�λ�����������洢�� aStegedImage
	bitwise_or(tFront_image, tHidden_image, aStegedImage);
	return aStegedImage;
}

// ��ȡˮӡͼ��
Mat deSteganograph(Mat &aStegedImage)
{
	// �洢ԭʼͼ��
	Mat aFrontImage(aStegedImage.rows, aStegedImage.cols, aStegedImage.type());
	// �洢ˮӡͼ��
	Mat aHiddenImage(aStegedImage.rows, aStegedImage.cols, aStegedImage.type());

	if (aFrontImage.channels() == 3)
	{
		Mat front_mask(aStegedImage.rows, aStegedImage.cols, aStegedImage.type(), Scalar(0xF0, 0xF0, 0xF0));
		Mat hidden_mask(aStegedImage.rows, aStegedImage.cols, aStegedImage.type(), Scalar(0x0F, 0x0F, 0x0F));

		bitwise_and(aStegedImage, front_mask, aFrontImage);
		bitwise_and(aStegedImage, hidden_mask, aHiddenImage);

		for (int j = 0; j < aHiddenImage.rows; j++)
			for (int i = 0; i < aHiddenImage.cols; i++)
			{
				aHiddenImage.at<Vec3b>(j, i)[0] = aHiddenImage.at<Vec3b>(j, i)[0] << 4;
				aHiddenImage.at<Vec3b>(j, i)[1] = aHiddenImage.at<Vec3b>(j, i)[1] << 4;
				aHiddenImage.at<Vec3b>(j, i)[2] = aHiddenImage.at<Vec3b>(j, i)[2] << 4;
			}
	}
	else if (aFrontImage.channels() == 1)
	{
		Mat front_mask(aStegedImage.rows, aStegedImage.cols, aStegedImage.type(), Scalar(0xF0));
		Mat hidden_mask(aStegedImage.rows, aStegedImage.cols, aStegedImage.type(), Scalar(0x0F));

		bitwise_and(aStegedImage, front_mask, aFrontImage);
		bitwise_and(aStegedImage, hidden_mask, aHiddenImage);

		for (int j = 0; j < aHiddenImage.rows; j++)
			for (int i = 0; i < aHiddenImage.cols; i++)
			{
				aHiddenImage.at<uchar>(j, i) = aHiddenImage.at<uchar>(j, i) << 4;
			}
	}

	return aHiddenImage;
}

int main()
{
	Mat img1 = imread("../image/lena.png");
	Mat img2 = imread("../image/baboon.png");


	if (img1.empty() || !img1.data)
	{
		cerr << "Problem loading first image!" << endl;
		return -1;
	}
	if (img2.empty() || !img2.data)
	{
		cerr << "Problem loading second image!" << endl;
		return -1;
	}

	Mat result = steganograph(img1, img2);

	// ʵ��һ
	imwrite("../image/result1.bmp", result);
	Mat img3 = imread("../image/result1.bmp");
	Mat test1 = deSteganograph(img3);
	imwrite("../image/test1.bmp", test1);

	// ʵ���
	imwrite("../image/result2.jpg", result);
	Mat img4 = imread("../image/result2.jpg");
	Mat test2 = deSteganograph(img4);
	imwrite("../image/test2.jpg", test2);

	return 0;
}