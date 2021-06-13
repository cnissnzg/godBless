import React from 'react';
import Base from '../superbase';
import axios from 'axios';
import { Api, Url } from '../../common/common';
import { Redirect } from 'react-router-dom';
import { Typography, Form, Input, Select,Button,message} from 'antd';
import '../../css/base.css';

const { Title } = Typography;
const { Option } = Select;
const layout = {
    labelCol: { span: 24 , offset:6},
    wrapperCol: { span: 24 ,offset:6},
};
const tailLayout = {
    wrapperCol: { span: 14, offset:14},
  };
  const showUserName = () => {
    let nowTime = new Date();
    let deadtime = localStorage.getItem("tokendeadtime");
    console.log(localStorage.hasOwnProperty("username"));
    return deadtime != null && nowTime.getTime() < deadtime && localStorage.hasOwnProperty("hojxtoken") && localStorage.hasOwnProperty("username");
  }
class ProblemSubmit extends React.Component {
    state = {
        algorithms:{data:[]},
        success:false,
    }
    componentDidMount(){
        axios.get(Api.code.list(0,1000)).then((response)=>{
            this.setState({
                algorithms:response.data,
            });
        }).catch((error) => { console.log(error) })
    }
    handleSubmit = (values) => {
        if(!showUserName()){
            message.error("请先登录")
            return;
        }
        let uid = localStorage.getItem("username");
        axios.get(Api.judge.start(values.algorithmId,uid,values.pid)).then((response)=>{
            message.success("已提交至测评队列")
            this.setState({
                success:true,
            });
        }).catch((error) => {
            message.error("提交失败")
             console.log(error) })
    }
    render() {
        if(this.state.success){
            return <Redirect push to={Url.judge.status} />;
        }
        return (
            <Base chosen="4">
                <div class="topbar" style={{ marginLeft: "8rem",marginRight:"8rem",marginTop:"4rem" }}>
                    <Title level={1}>Submit Code</Title>
                </div>
                <div class="topbar" style={{marginLeft: "8rem",marginRight:"8rem"}}>
                    <Form
                        {...layout}
                        name="basic"
                        initialValues={{ pid: this.props.match.params.pid }}
                        style={{width:"80%"}}
                        onFinish={this.handleSubmit}
                        
                    >
                        <Form.Item
                            label="Problem"
                            name="pid"
                        >
                            <Input disabled />
                        </Form.Item>

                        <Form.Item
                            label="Algorithm"
                            name="algorithmId"
                        >
                            <Select>
                                {this.state.algorithms.data.map(item=>(<Option value={item.algorithmId}>{item.name}</Option>))}
                            </Select>
                        </Form.Item>


                        <Form.Item {...tailLayout}>
                            <Button type="primary" htmlType="submit">
                                Submit
                            </Button>
                        </Form.Item>
                    </Form>

                </div>
            </Base>
        );
    }

}
export default ProblemSubmit;