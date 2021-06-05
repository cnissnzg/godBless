import React from 'react';
import Base from '../superbase';
import { Table, Layout, Card, Tag } from 'antd';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Api, Url } from '../../common/common';
import '../../css/base.css';
import { DownOutlined, SortAscendingOutlined } from '@ant-design/icons';
import Title from 'antd/lib/typography/Title';
const columns = [
    {
        title: 'ID',
        dataIndex: 'runId',
        width: '10%',
    }, {
        title: '提交时间',
        dataIndex: 'ctime',
        width: '15%',
    }, {
        title: '算法名称',
        dataIndex: 'algorithmName',
        width: '20%',
    },{
        title: '测试集',
        dataIndex: 'title',
        width: '20%',
    },{
        title: '提交者',
        dataIndex: 'callerUid',
        width: '10%',
    },{
        title: '状态',
        dataIndex: 'status',
        width: '10%',
        render: (text, record, index) => {
            if(record.state=='2' || record.state=='5' || record.state=='11'){
                return <Tag color="green">{record.status}</Tag>;
            }else if(record.state=='3' || record.state=='6' || record.state=='12'){
                return <Tag color="volcano">{record.status}</Tag>;
            }else{
                return <Tag color="geekblue">{record.status}</Tag>;
            }
        }
    },{
        title: '操作',
        dataIndex: 'authorUid',
        render: (text, record, index) => {
            if(record.state=='2'){
                return <span>
                    <Link>编译</Link>
                </span>;
            }else if(record.state=='5'){
                return <span>
                <Link>运行</Link>
            </span>;
            }else if(record.state=='11'){
                return <span>
                <Link>查看报告</Link>
            </span>;
            }else if(record.state=='3' || record.state=='6' || record.state=='12'){
                return <span>
                <Link>重试</Link> | <Link>撤销</Link> | <Link>日志</Link>
            </span>;
            }else{
                return <span>
                <Link>撤销</Link>
            </span>;
            }
        }
    }
]
class JudgeQueue extends React.Component {
    state = {
        data: [
            { runId: '307f7da9', status: '请求已提交', state: '0', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:21' ,title: '1000 - test'},
            { runId: '034ab5dc', status: '代码发送至虚拟环境', state: '1', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:22' ,title: '1000 - test'},
            { runId: '5be5693d', status: '构建成功', state: '2', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:23' ,title: '1000 - test'},
            { runId: 'a0bd3225', status: '构建失败', state: '3', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:24' ,title: '1000 - test'},
            { runId: 'abedcc76', status: '正在编译', state: '4', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:25' ,title: '1000 - test'},
            { runId: '1c6f59dc', status: '编译成功', state: '5', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:26' ,title: '1000 - test'},
            { runId: '7c4b4734', status: '编译失败', state: '6', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:27' ,title: '1000 - test'},
            { runId: '155facc2', status: '正在运行', state: '7', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:28' ,title: '1000 - test'},
            { runId: 'e6b6caa1', status: '嵌入水印成功', state: '8', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:29' ,title: '1000 - test'},
            { runId: 'd0a60e60', status: '鲁棒性脚本运行成功', state: '9', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:30' ,title: '1000 - test'},
            { runId: 'aef0ee69', status: '水印提取成功', state: '10', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:31' ,title: '1000 - test'},
            { runId: 'f32cd1cf', status: '测评结束', state: '11', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:32' ,title: '1000 - test'},
            { runId: '086eaf13', status: '运行失败', state: '12', callerUid: '667', authorUid: '667', algorithmName: 'LSB_TEST', ctime: '5-31 23:33' ,title: '1000 - test'},
        ]
    }
    render() {
        return (
            <Base chosen="5">
                <div style={{ margin: "1rem 1rem" }}>
                    <div class="topbar">
                        <Title level={3} style={{ color: "black", textAlign: "center" }}>测评队列</Title>
                    </div>
                    <Card bordered={false} className="contentContainer">
                        <Table columns={columns} dataSource={this.state.data} rowKey={record => record.runId}/>
                    </Card>
                </div>
            </Base>
        );
    }
};
export default JudgeQueue;