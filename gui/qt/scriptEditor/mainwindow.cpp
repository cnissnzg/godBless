#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    QString filename;
    filename=QFileDialog::getOpenFileName(this,tr("Choose Material"),"",tr("Images (*.png *.bmp *.jpg *.tif *.GIF )"));
    if(filename.isEmpty())
       {
            return;
       }
       else
    {
       QImage* img=new QImage;

          if(! ( img->load(filename) ) ) //加载图像
           {
               QMessageBox::information(this,
                                        tr("打开图像失败"),
                                        tr("打开图像失败!"));
               delete img;
               return;
           }
       }
     ui->setupUi(this);
}

MainWindow::~MainWindow()
{
    delete ui;
}
