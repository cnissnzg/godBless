import React from 'react';
import Base from '../superbase';
import { Table, Layout,Card} from 'antd';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Api, Url } from '../../common/common';
import '../../css/base.css';
import { DownOutlined, SortAscendingOutlined } from '@ant-design/icons';
import Title from 'antd/lib/typography/Title';
const getDownload = (name,uid) => {
    window.location.href=Api.code.getFile(name,uid);
}
const columns = [{
    title: 'ID',
    dataIndex: 'algorithmId',
    width: '10%',
  }, {
    title: '标题',
    dataIndex: 'name',
    width: '70%',
  }, {
    title: '作者',
    dataIndex: 'uid',
    width: '10%',
  }, {
    title: '操作',
    dataIndex: 'code',
    width: '10%',
    render: (text, record, index) => {
        return <Link onClick={e=>{e.preventDefault();getDownload(record.name,record.uid)}}>下载</Link>;
      },
  }];
  const PageSize = 20;
class CodeList extends React.Component {
    state = {
        data: [],
        pagination: { defaultPageSize: PageSize, current: 1 },
        loading: false,
    }
    fetch = (currentPage = this.state.pagination.current) => {
        this.setState({ loading: true });
        axios.get(Api.code.list(PageSize * (currentPage - 1), PageSize * currentPage)).then((response) => {
            const pagination = { ...this.state.pagination };
            pagination.total = response.data.total;
            this.setState({
                loading: false,
                data: response.data.data,
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
        return (
            <Base chosen="2">
            <Layout>
                <Card title="算法列表" className="contentContainer">
                    <Table
                        columns={columns}
                        rowKey={record => record.algorithmId}
                        dataSource={this.state.data}
                        pagination={this.state.pagination}
                        loading={this.state.loading}
                        onChange={this.handleTableChange}
                    />
                </Card>
            </Layout>
            </Base>
        );
    }
}
export default CodeList;