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

class ProblemDetail extends React.Component {
    state={
        problem:{tags:['loading']},
        Materials:[],
        Challenges:[],

    }
    componentDidMount(){
        var pid = this.props.match.params.pid;
        axios.get(Api.problem.getOne(pid)).then((response)=>{
            this.setState({
                problem:response.data,
            });
        }).catch((error) => { console.log(error) })
        axios.get(Api.problem.getChallenge(pid)).then((response)=>{
            this.setState({
                Challenges:response.data,
            });
        }).catch((error) => { console.log(error) })
        axios.get(Api.problem.getMaterial(pid)).then((response)=>{
            this.setState({
                Materials:response.data,
            });
        }).catch((error) => { console.log(error) })
    }
    render() {
        return (
            <Base chosen="4">
                <div style={{ margin: "1rem 1rem" }}>
                    <div class="topbar">
                        <Title level={3} style={{ color: "black", textAlign: "center" }}>{this.props.match.params.pid} - {this.state.problem.title}</Title>
                        <div style={{ marginLeft: "auto" }}><UserOutlined /><Text type="secondary" style={{ marginLeft: "0.5rem" }}>admin</Text></div>
                    </div>
                    <Row gutter={8}>
                        <Col span={20}>
                            <Card title="Testset Description" bordered={false} className="contentContainer">
                                <p>{this.state.problem.description}</p>
                            </Card>
                            <Card title="Input Overview" bordered={false} className="contentContainer">
                                <List
                                    grid={{ gutter: 16, column: 4 }}
                                    dataSource={this.state.Materials}
                                    renderItem={item =>{
                                        if(item.type == 1){
                                            return (
                                                <List.Item>
                                                    <Image src={Url.resource + 'photo/' + item.pid + '/'+item.filename+'.'+item.suffix}></Image>
                                                </List.Item>
                                            )
                                        }else{
                                            return (
                                                <List.Item>
                                                    <Image src={Url.resource + 'video/' + item.pid + '/gif/'+item.filename+'.gif'}></Image>
                                                </List.Item>
                                            )
                                        }
                                    }}
                                />
                            </Card>
                            <Card title="Challenge List" bordered={false} className="contentContainer">

                                <List
                                    dataSource={this.state.Challenges}
                                    renderItem={item => (
                                        <Collapse expandIconPosition='right'>
                                            <Panel header={item.cid}>
                                                <List
                                                    dataSource={JSON.parse(item.params)}
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
                                {this.state.problem.tags.map(tag => (<Tag>{tag}</Tag>))}
                            </Card>

                        </Col>
                        <Col span={4}>
                            <Card title="Limits" className="contentContainer">
                                    <Title level={5} style={{margin:"auto"}}>Time Limit : {this.state.problem.timeLimit}ms</Title>
                                    <Title level={5} style={{margin:"auto"}}>Memory Limit : {this.state.problem.memoryLimit}MB</Title>
                                    <Title level={5} style={{margin:"auto"}}>Bit Error Limit : {this.state.problem.bitErrorLimit*100}%</Title>
                                    <Title level={5} style={{margin:"auto"}}>Quality Limit : {this.state.problem.qualityLimit}</Title>
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