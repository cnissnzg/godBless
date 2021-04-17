import React from 'react';
import ReactDOM from 'react-dom';
import Base from './component/superbase'
import { Row, Col, Card, Typography } from 'antd';
import { Link } from 'react-router-dom';
import { Api, Url } from './common/common';
import { PlaySquareTwoTone, CodeTwoTone, CloudTwoTone, DashboardTwoTone, ExperimentTwoTone } from '@ant-design/icons';
import './home.css'
const { Title } = Typography;
class Home extends React.Component {
    render() {
        return (
            <Base chosen='1'>
                <div className="content">
                    <Row type='flex' justify='center'>
                        <Col>
                            <Title level={1} style={{ color: '#1A5CC8' }}>Welcome to Watermark Testing Platform!</Title>
                        </Col>
                    </Row>
                    <div className="cardWrapper">
                        <Row gutter={[24, 24]} style={{ minHeight: '100%', display: 'flex' }} justify='center'>
                            <Col span={8}>
                                <Card className="card" title={<div className='cardTitle' id='t1'><CodeTwoTone twoToneColor="#52c41a" />代码在线编译</div>} bordered={true} hoverable={true}>
                                    <p className="description">如果您选择开源您的水印算法，请提交您的算法源代码，选择对应的编译环境和包依赖，平台将在后台实时编译生成可执行文件。
                                    </p>
                                    <p className="description">
                                        您也可以选择提供闭源算法代码，提交算法程序二进制文件或者网络接口。
                                    </p>
                                </Card>
                            </Col>
                            <Col span={8}>
                                <Card className="card" title={<div className='cardTitle' id='t2'><CloudTwoTone twoToneColor="#eb2f96" />水印测试素材库</div>} bordered={true} hoverable={true}>
                                    <p className="description">
                                        平台负责提供并维护丰富的测试用素材资源，包括视频、音频、图片。包含各种格式、尺寸、参数的文件和各种内容的素材文件资源，可以全方位覆盖测试场景。
                                        </p>
                                    <p className="description">
                                        测试水印时，可以自选对应的素材集合，也可以提交和修改素材集合库的内容，可以向平台上传用于测试的素材资源。
                                    </p>
                                </Card>
                            </Col>
                            <Col span={8}>
                                <Link to={Url.problem.list}>
                                    <Card className="card" title={<div className='cardTitle' id='t3'><DashboardTwoTone />水印测试项目</div>} bordered={true} hoverable={true}>
                                        <p className="description">
                                            提供丰富的测试项目，测试水印算法的鲁棒性，执行效率，内存消耗，容量和嵌入后的视频图片质量等指标。
                                            </p>
                                    <p className="description">
                                            鲁棒性测试包括常见的剪切、缩放、变形等几何攻击，模糊、锐化、对比度调整等信号处理攻击，压缩、转码、分辨率转换等编码攻击以及盗录、截屏、拍屏等的模拟。
                                    </p>
                                    </Card>
                                </Link>
                            </Col>
                            <Col span={8}>
                                <Card className="card" title={<div className='cardTitle' id='t4'><ExperimentTwoTone twoToneColor="orangered" />音视频攻击宏生成</div>} bordered={true} hoverable={true}>
                                    <p className="description">水印测试以测试集的方式进行，平台提供图片、视频和音频的处理编辑器。用户可以将基本攻击方式进行组合，生成新的攻击方式。
                                    </p>
                                    <p className="description">
                                        上传并保存攻击宏文件后，在测试时将运用于对应的测试素材文件，指定相应测试的分数标准，从而得到想要的水印评价综合指标。
                                    </p>
                                </Card>
                            </Col>
                            <Col span={8}>
                                <Card className="card" title={<div className='cardTitle' id='t5'><PlaySquareTwoTone twoToneColor="palevioletred" />主观质量评价</div>} bordered={true} hoverable={true}>
                                    <p className="description">
                                        平台提供音视频的在线播放和互动评价平台，水印提供方可以选择将嵌入水印后的视频提交到观赏平台，用户看到多个版本的视频后，可以打分、推荐，从而对水印主观质量进行评价
                                        </p>
                                    <p className="description">
                                        同时，该观赏平台还提供互动功能，数据表现靠前的素材会被优先推荐给水印制作者使用测试，也会优先被平台用户看到。
                                    </p>
                                </Card>
                            </Col>
                        </Row>
                    </div>
                </div>
            </Base>
        );
    }
}
export default Home;