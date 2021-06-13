import React from 'react';
import Base from '../superbase'
import { Card, Typography, Form,Rate,Row,Col,Button,message } from 'antd';
import '../../css/base.css';
import Player from 'griffith'
const { Title } = Typography;
function tran(v) {
    return {
        hd: {
            play_url: v,
        }
    }
}
const layout = {
    labelCol: {
        span: 12,
    },
    wrapperCol: {
        span: 8,
    },
};
const tailLayout = {
    wrapperCol: {
        offset: 8,
        span: 8,
    },
};
const FormItem = Form.Item;
class RandomWatch extends React.Component {
    render() {
        return (
            <Base chosen='7'>
                <>
                        <div class="topbar">
                            <Title level={3} style={{ color: "black", textAlign: "center" }}>主观性评测</Title>
                        </div>
                        <Card title="原视频" bordered={false} className="contentContainer">
                            <Player sources={tran('http://39.105.21.114:11451/video_debug/f.mp4')} />
                        </Card>
                        <Card title="嵌入后视频" bordered={false} className="contentContainer">
                            <Player sources={tran('http://39.105.21.114:11451/video_debug/c.mp4')} />
                        </Card>
                        <Card title="评分" bordered={false} className="contentContainer">
                        <Form {...layout}>
                            <FormItem
                                label="清晰度变化"
                                name="clearitude"
                            >
                                <Rate allowHalf defaultValue={3} />

                            </FormItem>
                            <FormItem
                                label="流畅度变化"
                                name="fluency"
                            >
                                <Rate allowHalf defaultValue={3} />

                            </FormItem>
                            <FormItem
                                label="观看体验变化"
                                name="experience"
                            >
                                <Rate allowHalf defaultValue={3} />

                            </FormItem>
                            <FormItem
                                label="素材质量"
                                name="quality"
                            >
                                <Rate allowHalf defaultValue={3} />

                            </FormItem>
                            
                        </Form>
                        </Card>
                        <Card title="提交" className="contentContainer">
                    <Row>
                        <Col span={3} offset={8}>
                            <Button style={{ width: "100%" }} onClick={e=>{message.warn("没有更多了")}}>下一条</Button>
                        </Col>
                        <Col span={3} offset={2}>
                            <Button type="primary" style={{ width: "100%" }} onClick={e=>{message.success("评价成功")}}>提交评价</Button>
                        </Col>
                    </Row>

                </Card>
                </>
            </Base>
        );
    }
};
export default RandomWatch;
