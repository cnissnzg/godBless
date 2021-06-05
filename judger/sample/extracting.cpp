#include <iostream>
#include <opencv2/opencv.hpp>

using namespace std;
using namespace cv;

Mat steganograph(Mat aFrontImage, Mat aHiddenImage)
{
	CV_Assert(aFrontImage.type() == aHiddenImage.type() && aFrontImage.size() == aHiddenImage.size());

	if (aFrontImage.size() != aHiddenImage.size())
	{
		copyMakeBorder(aHiddenImage, aHiddenImage, 0, aFrontImage.rows - aHiddenImage.rows, 0, aFrontImage.cols - aHiddenImage.cols, BORDER_CONSTANT, Scalar::all(0));
	}

	Mat aStegedImage(aFrontImage.rows, aFrontImage.cols, aFrontImage.type());
	Mat tFront_image, tHidden_image;
	if (aFrontImage.channels() == 3)
	{
		Mat front_mask(aFrontImage.rows, aFrontImage.cols, aFrontImage.type(), Scalar(0xF0, 0xF0, 0xF0));
		Mat hidden_mask(aHiddenImage.rows, aHiddenImage.cols, aHiddenImage.type(), Scalar(0xF0, 0xF0, 0xF0));

		bitwise_and(aFrontImage, front_mask, tFront_image);
		bitwise_and(aHiddenImage, hidden_mask, tHidden_image);

		for (int j = 0; j < aHiddenImage.rows; j++)
			for (int i = 0; i < aHiddenImage.cols; i++)
			{
				tHidden_image.at<Vec3b>(j, i)[0] = tHidden_image.at<Vec3b>(j, i)[0] >> 4;
				tHidden_image.at<Vec3b>(j, i)[1] = tHidden_image.at<Vec3b>(j, i)[1] >> 4;
				tHidden_image.at<Vec3b>(j, i)[2] = tHidden_image.at<Vec3b>(j, i)[2] >> 4;
			}
	}
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

	bitwise_or(tFront_image, tHidden_image, aStegedImage);
	return aStegedImage;
}

Mat deSteganograph(Mat &aStegedImage)
{
	Mat aFrontImage(aStegedImage.rows, aStegedImage.cols, aStegedImage.type());
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

int main(int argc,char* argv[])
{
	
	Mat img1 = imread(argv[1]);

	if (img1.empty() || !img1.data)
	{
		cerr << "Problem loading first image!" << endl;
		return -1;
	}

	Mat test1 = deSteganograph(img1);
	imwrite(argv[2], test1);
	return 0;
}