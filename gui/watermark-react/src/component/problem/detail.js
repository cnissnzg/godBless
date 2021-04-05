import React from 'react';
import ReactDOM from 'react-dom';
import Base from '../superbase'
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Api, Url } from '../../common/common';
import { Row, Col, Typography, Card, Image, List, Collapse, Tag ,Button} from 'antd';
import { UserOutlined, InfoCircleOutlined,BarChartOutlined,SendOutlined } from '@ant-design/icons';
import '../../css/base.css';

const { Title, Text } = Typography;
const { Panel } = Collapse;

const data = [
    {
        url: '1.jpeg',
        title: '平移',
        list: [{ name: 'x', value: '10' }, { name: 'y', value: '5' }],
    },
    {
        url: '2.jpeg',
        title: '旋转',
        list: [{ name: 'angle', value: '45' }]
    },
    {
        url: '3.jpeg',
        title: '缩放',
        list: [{ name: 'x', value: '0.15' }, { name: 'y', value: '0.25' }],
    },
    {
        url: '4.jpeg',
        title: '裁剪',
        list: [{ name: 'x', value: '0.15' }, { name: 'y', value: '0.25' }],
    },
    {
        url: '5.jpeg',
        title: '对比度调整',
        list: [{ name: 'rate', value: '0.1' }]
    },
    {
        url: '6.jpeg',
        title: '亮度调整',
        list: [{ name: 'rate', value: '0.2' }]
    },
    {
        url: '7.jpeg',
        title: 'jpeg压缩',
        list: [{ name: 'rate', value: '0.9' }]
    },
    {
        url: '8.jpeg',
        title: '视角变换',
        list: [{ name: 'x_rate', value: '0.05' }, { name: 'y_rate', value: '0.08' }]
    },
];
const tagsData = ['官方', '全部测试项', '开发样例'];
const limits = {
    time : "3000ms",
    memory : "256MB",
    bitError : "30%",
    psnr : "25"
}
class Tags extends React.Component {
    render() {
        return (
            <>
                {tagsData.map(tag => (<Tag>{tag}</Tag>))}
            </>
        );
    }
}
class ProblemDetail extends React.Component {
    render() {
        return (
            <Base chosen="4">
                <div style={{ margin: "1rem 1rem" }}>
                    <div class="topbar">
                        <Title level={3} style={{ color: "black", textAlign: "center" }}>{this.props.match.params.pid} - test</Title>
                        <div style={{ marginLeft: "auto" }}><UserOutlined /><Text type="secondary" style={{ marginLeft: "0.5rem" }}>admin</Text></div>
                    </div>
                    <Row gutter={8}>
                        <Col span={20}>
                            <Card title="Testset Description" bordered={false} className="contentContainer">
                                <p>对视频进行各类图像处理（包括锐化、模糊攻击和对比度、饱和度、亮度、色度攻击），各类几何变换攻击（包括剪切、镜像、缩放、翻转、旋转、等比缩放、非等比缩放、高动态转标），各类视频压缩和编码格式的转换（分辨率下采样、码率降低、编码格式转换）。这一部分主要采用ffmpeg的命令行参数和opencv进行实现，将对应攻击编写成相应的python脚本文件，评分时自动调用该类脚本。</p>
                                <p>本例供测试使用，包括开发阶段测试的攻击测试方法和评价指标</p>
                            </Card>
                            <Card title="Input Overview" bordered={false} className="contentContainer">
                                <List
                                    grid={{ gutter: 16, column: 4 }}
                                    dataSource={data}
                                    renderItem={item => (
                                        <List.Item>
                                            <Image src={require('../../img/temp/' + item.url).default}></Image>
                                        </List.Item>
                                    )}
                                />
                            </Card>
                            <Card title="Challenge List" bordered={false} className="contentContainer">

                                <List
                                    dataSource={data}
                                    renderItem={item => (
                                        <Collapse expandIconPosition='right'>
                                            <Panel header={item.title}>
                                                <List
                                                    dataSource={item.list}
                                                    renderItem={des => (
                                                        <List.Item>
                                                            <div>
                                                                <InfoCircleOutlined style={{ verticalAlign: "middle" }} /><span>  {des.name} : {des.value}<br /></span>
                                                            </div>
                                                        </List.Item>

                                                    )} />

                                            </Panel>
                                        </Collapse>
                                    )} />

                            </Card>
                            <Card title="Tags" bordered={false} className="contentContainer">
                                <Tags />
                            </Card>

                        </Col>
                        <Col span={4}>
                            <Card title="Limits" className="contentContainer">
                                    <Title level={5} style={{margin:"auto"}}>Time Limit : {limits.time}</Title>
                                    <Title level={5} style={{margin:"auto"}}>Memory Limit : {limits.memory}</Title>
                                    <Title level={5} style={{margin:"auto"}}>Bit Error Limit : {limits.bitError}</Title>
                                    <Title level={5} style={{margin:"auto"}}>Quality Limit : {limits.psnr}</Title>
                            </Card>
                            <Card title="Action" className="contentContainer">
                                <Button icon={<BarChartOutlined />} style={{width:"100%",marginBottom:"1rem"}}>Statistics</Button>
                                <Link to={Url.problem.submit(this.props.match.params.pid)} tabIndex="-1"><Button type="primary" icon={<SendOutlined />} style={{width:"100%"}}>Submit</Button></Link>
                            </Card>

                        </Col>
                    </Row>
                </div>
            </Base>
        );
    }
}

export default ProblemDetail;