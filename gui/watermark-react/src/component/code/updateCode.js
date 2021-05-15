import React from 'react';
import ReactDOM from 'react-dom';
import Base from '../superbase'
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import { Api, Url } from '../../common/common';
import { Form,Input, Typography, Card, Collapse, Button } from 'antd';
import { UserOutlined, InfoCircleOutlined, BarChartOutlined, SendOutlined } from '@ant-design/icons';
import '../../css/base.css';
import Layout from 'antd/lib/layout/layout';

const { Title, Text } = Typography;
const { Panel } = Collapse;
const layout = {
    labelCol: { span: 8 },
    wrapperCol: { span: 8 },
};
const tailLayout = {
    wrapperCol: { offset: 11, span: 2 },
};
class UpdateCode extends React.Component {
    state = {
        algorithm:'',
        done:false,
      }
    handleSubmit = (values) => {
        this.setState(
            {algorithm:values.algorithm,
            done:true}
            );
        localStorage.setItem('algorithm',values.algorithm);
    }
    render() {
        console.log(this.props.match);
        return (
            <div>
                {this.state.done?<Redirect push to={Url.code.editor(this.state.algorithm)}/>
                :
            <Base chosen="2">
                <Layout>
                    <Card title="新建算法" className="contentContainer">
                        <Form {...layout} onFinish={this.handleSubmit}>
                            <Form.Item
                                label="算法名称"
                                name="algorithm"
                                rules={[{ required: true, message: "Please input algorithm's name!" }]}
                            >
                                <Input />
                            </Form.Item>

                            <Form.Item {...tailLayout}>
                                    <Button type="primary" htmlType="submit" style={{width:"100%"}}>
                                        下一步
                                    </Button>
                            </Form.Item>
                        </Form>
                    </Card>
                </Layout>
            </Base>
                }
            </div>
        );
    }
}

export default UpdateCode;