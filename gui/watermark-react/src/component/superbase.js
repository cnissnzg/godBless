import React, { useState } from 'react';
import { render } from 'react-dom';
import { Layout, Menu, Breadcrumb,Typography, Space } from 'antd';
import imgUrl from "../img/logo2.jpeg"
import 'antd/dist/antd.css';
import './superbase.css';
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
            <Menu.Item key="1">主页</Menu.Item>
            <Menu.Item key="2">提交代码</Menu.Item>
            <Menu.Item key="3">宏编辑器</Menu.Item>
            <Menu.Item key="4">申请测试</Menu.Item>
            <Menu.Item key="5">评价平台</Menu.Item>
            <Menu.Item key="6">用户</Menu.Item>
          </Menu>
        </Header>
        <Content className="content">
        {this.props.children}
        </Content>
        <Footer style={{ textAlign: 'center' }}>哈尔滨工业大学计算学部 软件工程专业 1173710124 惠一锋</Footer>
      </Layout>
    );
  }
}

export default SuperBase