import React from 'react';
import ReactDOM from 'react-dom';
import Base from '../superbase'
import axios from 'axios';
import { Redirect,Link } from 'react-router-dom';
import { Api, Url } from '../../common/common';
import { Row, Col, Typography, Card, Collapse, Tag, Button, Radio, Input, Form, Upload, message, Modal, Dropdown, Menu, List } from 'antd';
import { UploadOutlined, PlusOutlined } from '@ant-design/icons';
import '../../css/base.css';

const { Title, Text } = Typography;
const { Panel } = Collapse;
const layout = {
    labelCol: {
        span: 6,
    },
    wrapperCol: {
        span: 12,
    },
};
const tailLayout = {
    wrapperCol: {
        offset: 8,
        span: 8,
    },
};
var files = [];
class PicturesWall extends React.Component {
    state = {
        previewVisible: false,
        previewImage: '',
        previewTitle: '',
        fileList: [],
        files:this.props.fileList,
    };

    handleCancel = () => this.setState({ previewVisible: false });

    handlePreview = async file => {
        if (file['type'].indexOf('video/') == 0) {
            var gifFileName = file['name'].split('.')[0] + '.gif';
            this.setState({
                previewImage: 'http://39.105.21.114:11451/library/gif/' + gifFileName,
                previewVisible: true,
                previewTitle: file.name || file.url.substring(file.url.lastIndexOf('/') + 1),
            });
        } else {
            this.setState({
                previewImage: 'http://39.105.21.114:11451/library/' + file['name'],
                previewVisible: true,
                previewTitle: file.name || file.url.substring(file.url.lastIndexOf('/') + 1),
            });
        }
        console.log(file);

    };

    handleChange = ({ fileList }) => {
        this.setState({files:fileList})
        this.setState({ fileList:fileList });
        files = fileList;
    }
    handleThumbnail = (file) => {
        if (file['type'].indexOf('video/') == 0) {
            return new Promise((resolve) => {
                var gifFileName = file['name'].split('.')[0] + '.gif';
                resolve('http://39.105.21.114:11451/library/gif/' + gifFileName);
            })
        } else {
            return new Promise((resolve) => {
                resolve('http://39.105.21.114:11451/library/' + file['name']);
            })
        }
    }
    render() {
        const { previewVisible, previewImage, fileList, previewTitle } = this.state;
        const uploadButton = (
            <div>
                <PlusOutlined />
                <div style={{ marginTop: 8 }}>Upload</div>
            </div>
        );
        return (
            <>

                <Upload
                    action={Api.problem.uploadMaterial}
                    listType="picture-card"
                    fileList={fileList}
                    onPreview={this.handlePreview}
                    onChange={this.handleChange}
                    previewFile={this.handleThumbnail}
                >
                    {fileList.length >= 20 ? null : uploadButton}
                </Upload>
                <Modal
                    visible={previewVisible}
                    title={previewTitle}
                    footer={null}
                    onCancel={this.handleCancel}
                >
                    <img alt="example" style={{ width: '100%' }} src={previewImage} />
                </Modal>
            </>
        );
    }
};
class ChallengeList extends React.Component {
    state = {
        meta: [
            { name: 'blur', params: [{ name: 'rate', domain: '-' }, { name: 'size', domain: '-' }] },
            { name: 'chop', params: [{ name: 'code', domain: '-' }, { name: 'rate', domain: '-' }] },
            { name: 'cover', params: [{ name: 'height', domain: '(0,)' }, { name: 'width', domain: '(0,)' }] },
        ],
        data: this.props.data,
    }
    componentDidMount() {
        axios.get(Api.problem.getParam).then((response) => {
            this.setState({
                meta: response.data,
            });
        }).catch((error) => { console.log(error) })
    }
    handleClick = menuItem => {
        var tmp = this.state.data;
        var newItem = {
            cid: menuItem.key,
            pcid: tmp.length,
            params: [],
        }
        var lenMeta = this.state.meta.length;
        for (var i = 0; i < lenMeta; i++) {
            if (this.state.meta[i].name == menuItem.key) {
                var paramLength = this.state.meta[i].params.length;
                for (var j = 0; j < paramLength; j++) {
                    newItem.params.push({
                        name: this.state.meta[i].params[j].name,
                        value: "",
                        type: "str",
                        no: j,
                    })
                }
                break;
            }
        }
        tmp.push(newItem);
        this.setState({ state: tmp });
        console.log(tmp);
    }
    render() {
        return (
            <>
                <Dropdown overlay={
                    <Menu onClick={this.handleClick}>
                        {this.state.meta.map(param => (<Menu.Item key={param.name}>
                            {param.name}
                        </Menu.Item>))}
                    </Menu>
                }>
                    <Button type="primary" style={{ marginBottom: "1rem" }}>添加测试项</Button>
                </Dropdown>
                <List dataSource={this.state.data}
                    renderItem={
                        item => (
                            <Collapse expandIconPosition='right'>
                                <Panel header={item.pcid + " - " + item.cid}>
                                    <List dataSource={item.params}
                                        renderItem={
                                            param => (
                                                <List.Item>
                                                    <Input addonBefore={param.name} onChange={
                                                        e => {
                                                            var tmp = this.state.data;
                                                            console.log('stf', item.pcid, param.no, tmp);
                                                            tmp[item.pcid].params[param.no].value = e.target.value;
                                                            this.setState({ data: tmp });
                                                        }
                                                    } />
                                                </List.Item>
                                            )
                                        }
                                    />
                                </Panel>
                            </Collapse>
                        )
                    } />
            </>
        );
    }
};
const FormItem = Form.Item;
const showUserName = () => {
    let nowTime = new Date();
    let deadtime = localStorage.getItem("tokendeadtime");
    console.log(localStorage.hasOwnProperty("username"));
    return deadtime != null && nowTime.getTime() < deadtime && localStorage.hasOwnProperty("hojxtoken") && localStorage.hasOwnProperty("username");
  }
class AddProblem extends React.Component {

    state = {
        fileList:[],
        data:[],
    }    
    handleSubmit = (values) => {
        if(!showUserName()){
            message.error("请先登录")
            return;
        }
        let uid = localStorage.getItem("username");
        values['fileList'] = files
        values['data'] = this.state.data
        values['uid'] = uid
        message.destroy();
        message.loading('提交中...',0);
        axios.post(Api.problem.add,values).then((response) => {
          if(response.status !== 200){
            message.destroy();
            message.error("添加失败")
          }else{
            message.destroy();
            message.success("添加成功");
            this.setState({
                success:true,
            });
          }
        }).catch((error) => {
          console.log(error)
          message.destroy();
          message.error("添加失败")
        });
    }
    render() {
        if(this.state.success){
            return <Redirect push to={Url.homepage} />;
        }
        return (
            <Base chosen="4">
                <div style={{ margin: "1rem 1rem" }}>
                    <div class="topbar">
                        <Title level={3} style={{ color: "black", textAlign: "center" }}>添加测试集</Title>
                    </div>
                    <Form {...layout} onFinish={this.handleSubmit}>
                        <Card title="基本信息" bordered={false} className="contentContainer">
                            <FormItem
                                label="测试集名称"
                                name="title"
                                rules={[{ required: true, message: "Please input problemset's name!" }]}
                            >
                                <Input />

                            </FormItem>

                            <FormItem
                                label="描述"
                                name="description"
                                rules={[{ required: true, message: "Can't be empty!" }]}
                            >
                                <Input.TextArea />

                            </FormItem>
                            <FormItem
                                label="类型"
                                name="type"
                                rules={[{ required: true, message: "Can't be empty!" }]}
                            >
                                <Radio.Group>
                                    <Radio.Button value="0">图片测试集</Radio.Button>
                                    <Radio.Button value="1">视频测试集</Radio.Button>
                                </Radio.Group>

                            </FormItem>
                        </Card>
                        <Card title="测试素材" bordered={false} className="contentContainer">
                            <FormItem
                                valuePropName='fileList'
                                rules={[{ required: true, message: "Can't be empty!" }]}
                            >
                                <PicturesWall fileList={this.state.fileList}/>
                            </FormItem>
                        </Card>
                        <Card title="测试项目" bordered={false} className="contentContainer">
                            <FormItem
                                valuePropName='data'
                                rules={[{ required: true, message: "Can't be empty!" }]}
                                wrapperCol={22}
                            >
                                <ChallengeList data={this.state.data}/>
                            </FormItem>
                        </Card>
                        <Card title="测试限制" bordered={false} className="contentContainer">
                            <FormItem
                                label="内存限制"
                                name="memoryLimit"
                                rules={[{ required: true, message: "Please input!" }]}

                            >
                                <Input value="1000" addonAfter="MB" />

                            </FormItem>
                            <FormItem
                                label="运行时间限制"
                                name="timeLimit"
                                rules={[{ required: true, message: "Please input!" }]}

                            >
                                <Input value="1000" addonAfter="ms" />

                            </FormItem>
                            <FormItem
                                label="错误率限制"
                                name="bitErrorLimit"
                                rules={[{ required: true, message: "Please input!" }]}

                            >
                                <Input value="30" addonAfter="%" />

                            </FormItem>
                            <FormItem
                                label="误差质量限制"
                                name="qualityLimit"
                                rules={[{ required: true, message: "Please input!" }]}
                            >
                                <Input value="25" />

                            </FormItem>
                        </Card>
                        <Card title="提交" bordered={false} className="contentContainer">
                            <FormItem {...tailLayout}>
                                <Button type="primary" htmlType="submit" style={{ width: '100%' }}>
                                    确认提交
                                </Button>
                            </FormItem>
                        </Card>
                    </Form>
                </div>
            </Base>
        );
    }
};
export default AddProblem;