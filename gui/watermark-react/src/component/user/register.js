import React from 'react';
import Base from '../superbase'
import { message,Button,Input,Row,Col,Card,Icon,Form } from 'antd';
import { Layout } from 'antd';
import axios from 'axios';
import { Api,Url } from '../../common/common';
import '../../css/base.css';
import { Redirect } from 'react-router-dom';
const FormItem = Form.Item;
const { Content, Footer } = Layout;

class RegisterForm extends React.Component{
    state={
      confirmDirty: false,
    }
    handleSubmit = (values) => {
      console.log('fuck');
          message.destroy();
          message.loading('Submitting...',0);
          axios.post(Api.user.register,{
            uid : values.username,
            email : values.email,
            pwd : values.password,
          }).then((response) => {
            if(response.status !== 200){
              message.destroy();
              message.error("注册失败")
            }else{
              message.destroy();
              message.success("注册成功");
              this.props.callbacks();
            }
          }).catch((error) => {
            console.log(error)
            message.destroy();
            message.error("注册失败")
          });
    }
    isStrongPassword = (value) => {
      let sum = 0,Modes = 0;
      for (let i=0;i<value.length;i++){
        let charType = 0;
        let t = value.charCodeAt(i)
        if(t>=48 && t <=57){charType=1;}
        else if(t>=65 && t <=90){charType=2;}
        else if(t>=97 && t <=122){charType=4;}
        else{charType=8;}
        Modes |= charType;
      }
      for(let i=0;i<4;i++){
        if(Modes & 1){sum++;}
        Modes>>>=1;
      }
      if (value.length < 8) sum = 0;
      return sum >= 3;
    }
    validateToNextPassword = (_, value) => {
      if (!value || this.isStrongPassword(value)) {
        return Promise.resolve();
      }
      return Promise.reject(new Error('密码必须包含大写字母、小写字母、数字和特殊符号中的至少三种，且长度8位或以上'));
    }
    render(){
      return (
        <Form onFinish={this.handleSubmit} className="login-form">
          <FormItem 
          name='username'
          rules={[{required: true}]}>
              <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username"/>
          </FormItem>
  
          <FormItem
              name = "email"
              rules={[{
                type: 'email', message: 'The input is not valid E-mail!',
              }, {
                required: true, message: 'Please input your E-mail!',
              }]}
            >
              <Input prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Email" />
          </FormItem>
          <FormItem
              name = "password"
              rules={[{
                required: true, message: 'Please input your password!'
              }, 
              {validator: this.validateToNextPassword}]}>
              <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
          </FormItem>
          <FormItem
              name = "confirm"
              rules={[{
                required: true, message: 'Please confirm your password!',
              },  ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue('password') === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error('The two passwords that you entered do not match!'));
                },
              })]}>
              <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" onBlur={this.handleConfirmBlur} placeholder="Confirm Password" />
          </FormItem>
  
          <FormItem >
            <Button type="primary" htmlType="submit" className="login-form-button">Register</Button>
          </FormItem>
        </Form>
      );
    }
  }


  class Register extends React.Component {
    state={done:false};
    changestate=()=>{this.setState({done:true})};
    render(){
        return (
            <div>
            {this.state.done ?
              <Redirect push to={Url.homepage}/>
              :
    
              <Base>
                <div style={{ padding: '0 50px' }}>
                  <div style={{ margin: '16px 0' ,padding: 24, minHeight: 280 }}>
                    <Row><Col span={6} offset={9}>
                      <Card title="Register" >
                        <RegisterForm callbacks={this.changestate.bind(this)}/>
                      </Card>
                    </Col></Row>
                  </div>
                </div>
              </Base>
            }
          </div>
        );
    }
  }
  
  export default Register;