import React, { useState } from 'react';
import { render } from 'react-dom';
import { Layout, Menu, Breadcrumb,Typography, Space } from 'antd';
import imgUrl from "../img/logo2.jpeg"
import 'antd/dist/antd.css';
import './superbase.css';
import { Link } from 'react-router-dom';
import { Url } from '../common/common';
import {
  DesktopOutlined,
  PieChartOutlined,
  FileOutlined,
  TeamOutlined,
  UserOutlined,
} from '@ant-design/icons';

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;
const {Text} = Typography;

class SuperBase extends React.Component {

  render() {
    return (
      <Layout className="layout">
        <Header className="header">
          <img className="logo" src={imgUrl}/>
          <Text className="title">Watermark Testing Platform</Text>
          <Menu theme="dark" mode="horizontal" defaultSelectedKeys={[this.props.chosen]}>
            <Menu.Item key="1"><Link to={Url.homepage}>主页</Link></Menu.Item>
            <Menu.Item key="2">提交代码</Menu.Item>
            <Menu.Item key="3">宏编辑器</Menu.Item>
            <Menu.Item key="4"><Link to={Url.problem.list}>申请测试</Link></Menu.Item>
            <Menu.Item key="5">测试集</Menu.Item>
            <Menu.Item key="6">评价平台</Menu.Item>
            <Menu.Item key="7">用户</Menu.Item>
          </Menu>
        </Header>
        <div style={{backgroundColor:"#f5f5f5"}}>
        <Content className="content">
        {this.props.children}
        </Content></div>
        <Footer style={{ textAlign: 'center' }}>哈尔滨工业大学计算学部 软件工程专业 1173710124 惠一锋</Footer>
      </Layout>
    );
  }
}

export default SuperBase