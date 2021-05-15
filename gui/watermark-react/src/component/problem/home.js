import React from 'react';
import Base from '../superbase';
import { Table, Menu, Dropdown, Divider, Breadcrumb, Tooltip, Input, Tag, Row, Col} from 'antd';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Api, Url } from '../../common/common';
import '../../css/base.css';
import { DownOutlined, SortAscendingOutlined } from '@ant-design/icons';
import Title from 'antd/lib/typography/Title';
const { Search } = Input;
const { CheckableTag } = Tag;
const columns = [{
  title: 'ID',
  dataIndex: 'pid',
  width: '10%',
  sorter: (a, b) => a.pid - b.pid,
}, {
  title: '标题',
  dataIndex: 'title',
  width: '45%',
  render: (text, record, index) => {
    const link = Url.problem.detail(record.pid);
    return <Link to={link}>{text}</Link>;
  },
}, {
  title: '素材数',
  dataIndex: 'materialCnt',
  width: '10%',
  sorter: (a, b) => a.materialCnt - b.materialCnt,
}, {
  title: '测试数',
  dataIndex: 'testCnt',
  width: '10%',
  sorter: (a, b) => a.testCnt - b.testCnt,
}, {
  title: '热度',
  dataIndex: 'cCnt',
  width: '10%',
  sorter: (a, b) => a.cCnt - b.cCnt,
}];

const menu = (
  <Menu>
    <Menu.Item>
      <a href="https://www.antgroup.com" class="black-link-small menu-item">
        全部测试集
      </a>
    </Menu.Item>
    <Menu.Item class="menu-item">
      <a href="https://www.antgroup.com" class="black-link-small menu-item">
        已提交测试集
      </a>
    </Menu.Item>
    <Menu.Item class="menu-item">
      <a href="https://www.antgroup.com" class="black-link-small menu-item">
        未提交测试集
      </a>
    </Menu.Item>
  </Menu>
);


const PageSize = 20;

class ColorIcon extends React.Component {
  render() {
    return (
      <SortAscendingOutlined style={{ color: this.props.color, fontSize: '1.2rem' }} />
    );
  }
}

const tagsData = ['官方', '自定', 'RST矫正', '盗摄','短视频滤镜','全部测试项','几何攻击','信号攻击'];

class HotTags extends React.Component {
  state = {
    selectedTags: ['自定'],
  };

  handleChange(tag, checked) {
    const { selectedTags } = this.state;
    const nextSelectedTags = checked ? [...selectedTags, tag] : selectedTags.filter(t => t !== tag);
    console.log('You are interested in: ', nextSelectedTags);
    this.setState({ selectedTags: nextSelectedTags });
  }

  render() {
    const { selectedTags } = this.state;
    return (
      <>
        {tagsData.map(tag => (
          <CheckableTag
            key={tag}
            checked={selectedTags.indexOf(tag) > -1}
            onChange={checked => this.handleChange(tag, checked)}
          >
            {tag}
          </CheckableTag>
        ))}
      </>
    );
  }
}
class ProblemList extends React.Component {
  state = {
    data: [],
    pagination: { defaultPageSize: PageSize, current: 1 },
    loading: false,
  }
  fetch = (currentPage = this.state.pagination.current) => {
    this.setState({ loading: true });
    axios.get(Api.problem.list(PageSize * (currentPage - 1), PageSize * currentPage)).then((response) => {
      // set ac fla
      /*
      const aclist = response.data.aclist;
      let aclist_id = 0;
      for (let i in response.data.problems){
        if (aclist_id<aclist.length && response.data.problems[i].pid === aclist[aclist_id]){
          response.data.problems[i].acflag = true;
          aclist_id++;
        }else{
          response.data.problems[i].acflag = false;
        }
      }
      */
      const pagination = { ...this.state.pagination };
      pagination.total = response.data.total;
      this.setState({
        loading: false,
        data: response.data.problems,
        pagination,
      })
    }).catch((error) => { console.log('wtf') });
  }
  componentDidMount() {
    this.fetch(1);
  }
  handleTableChange = (pagination) => {
    const pager = { ...this.state.pagination };
    pager.current = pagination.current;
    this.setState({
      pagination: pager,
    });
    this.fetch(pager.current);
  }

  render() {
    const inputStyle = {
      borderColor: "#334454",
      backgroundColor: '#001529',
      color: '#fff',
      borderRadius: 0
    }
    const onSearch = value => console.log(value);
    return (
      <Base menu="normal" chosen="4">
        <div class="conta">
          <div class="topbar">
            <Dropdown overlay={menu} className="dropdown">
              <a className="black-link" onClick={e => e.preventDefault()}>
                全部测试集 <DownOutlined />
              </a>
            </Dropdown>
            <Divider type="vertical" className="vdivider" />
            <Breadcrumb separator="  ">
              <Breadcrumb.Item href=""><span class="green-link">视频测试集</span></Breadcrumb.Item>
              <Breadcrumb.Item href=""><span className="black-link">图片测试集</span></Breadcrumb.Item>
            </Breadcrumb>
            <Search
              placeholder="搜索ID"
              allowClear
              enterButton
              size="large"
              onSearch={onSearch}
              style={{ width: 200, marginLeft: 'auto' }}
            />
          </div>
          <Row gutter={16}>
            <Col span={16}>
          <Table
            columns={columns}
            rowKey={record => record.pid}
            dataSource={this.state.data}
            pagination={this.state.pagination}
            loading={this.state.loading}
            onChange={this.handleTableChange}
          />
          </Col>
          <Col span={8}>
            <div style={{backgroundColor:"white"}}>
              <div style={{padding:"1rem 1rem"}}>
                <Title level={4}>按标签筛选</Title>
                <HotTags/>
              </div>
            </div>
          </Col>
          </Row>
        </div>
      </Base>
    );
  }
}

export default ProblemList;
