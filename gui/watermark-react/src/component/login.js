import React, { Component } from 'react';
import Base from './superbase'
import { Icon, Input, Button, Row, Col, Card, message,Modal,Spin,Form } from 'antd';
import '../css/base.css';
import { Layout } from 'antd';
import axios from 'axios';
import { Api,Url } from '../common/common';
import { Redirect,Link } from 'react-router-dom';
import {UserOutlined,SafetyOutlinedSvg,LockOutlined, SafetyOutlined} from '@ant-design/icons';
const { Content, Footer } = Layout;

const FormItem = Form.Item;

class LoginForm extends React.Component {
  state = {
    token: undefined,
    loading:true,
    captcha: undefined,
  }
  fetchCaptcha = ()=>{
    console.log("here");
    this.setState({loading:true});
    axios.get(Api.user.getCaptcha,{
      headers:{"Authorization":"none" }
    }).then((response) => {
      this.setState({
        loading:false,
        captcha:response.data,
        token:"!todo!",
      })
      console.log(response.data);
    }).catch((error) => {});
  }
  componentDidMount(){
    this.fetchCaptcha();
  }
  forgetPasswordInfo = ()=>{
    Modal.info({
      title: '找回密码',
      content: (
        <div>
          <p>联系我，email：984753891@qq.com</p>
        </div>
      ),
      onOk() {},
    });
  }
  handleSubmit = (values) => {

        
        message.loading('Logging...',0);
        console.log(values);
        axios.post(Api.login,{
          uid : values.userName,
          pwd : values.password,
          verCode : values.captcha,
        }).then((response) => {
          localStorage.setItem('hojxtoken',response.data);
          localStorage.setItem('username',values.userName);
          if (values.userName === 'hoj_admin' || values.userName === 'test'){
            localStorage.setItem('hojadminmark','true');
          }
          let nowdate = new Date();
          localStorage.setItem('tokendeadtime',nowdate.getTime()+1000*60*60*24*8);
          message.destroy();
          //reset header token
          axios.defaults.headers.common['Authorization'] =
            localStorage.getItem('hojxtoken') === null ? '' : localStorage.getItem('hojxtoken');
          this.props.success();
        }).catch((error) => {
          if (error.response){
            if (error.response.status === 400){
              message.destroy();
              message.error("用户名或密码错误")
              this.fetchCaptcha();
            }else if(error.response.status === 403){
              message.destroy();
              message.error("验证码错误")
              this.fetchCaptcha();
            }
          }
        });

  }
  render() {
    return (
      <Form onFinish={this.handleSubmit} className="login-form">
        <FormItem
        name = 'userName'
        rules={[{ required: true, message: 'Please input your username!' }]}>
            <Input prefix={<UserOutlined style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username" />
        </FormItem>
        <FormItem
          name='password'
          rules={[{ required: true, message: 'Please input your password!' }]}>

            <Input prefix={<LockOutlined style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
        </FormItem>
        <FormItem>
        <FormItem
         style={{width:'50%',float:'left'}}
        name='captcha'
            rules={[{ required: true, message: 'Please input captcha!' }]}>
            <Input prefix={<SafetyOutlined type="safety" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Captcha"/>


        

        </FormItem>
        {
          this.state.loading?
            <Spin className="login-form-forgot"/>
            :
            <img src={this.state.captcha} alt="" onClick={this.fetchCaptcha} className="login-form-forgot"/>
        }
        </FormItem>
        <FormItem>
          <Button type="primary" htmlType="submit" className="login-form-button">
            Log in
          </Button>
          Or <Link to={Url.user.register}>register now!</Link><a className="login-form-forgot"  onClick={this.forgetPasswordInfo}>Forgot password</a>
        </FormItem>
      </Form>
    );
  }
}


class Login extends Component {
  state = {
    redirectToReferrer:false,
  }
  success = ()=>{
    console.log("!!!!");
    this.setState({redirectToReferrer:true});
  }
  render() {
    const {from} = this.props.location.state || { from: {pathname:"/"} }
    const {redirectToReferrer} = this.state;
    if (redirectToReferrer){
      return <Redirect push to={from} />;
    }
    return (
      <Base>
      <div style={{ padding: '0 50px' }}>
        <div style={{ margin: '16px 0' ,padding: 24, minHeight: 280 }}>
          <Row><Col span={6} offset={9}>
            <Card title="Login" >
              <LoginForm success={this.success.bind(this)}/>
            </Card>
          </Col></Row>
        </div>
      </div>
    </Base>
    );
  }
}

export default Login;