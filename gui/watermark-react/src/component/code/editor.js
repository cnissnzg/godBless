import React, { Component, useState } from 'react';
import ReactDOM from 'react-dom';
import Base from '../superbase'
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Api, Url } from '../../common/common';
import { Row, Col, Menu, Dropdown, Layout, Card, Button, Form, message, Input } from 'antd';
import getIcon from '../../common/myIcon';
import Icon from '@ant-design/icons';
import '../../css/base.css';
import { ReactCodeJar, useCodeJar } from "react-codejar";
import { withLineNumbers } from 'codejar/linenumbers';
import 'prismjs/themes/prism-okaidia.css';
var Prism = require('prismjs');
require('prismjs/components/prism-c');
require('prismjs/components/prism-cpp');
require('prismjs/components/prism-bash');
require('prismjs/components/prism-makefile');
require('prismjs/components/prism-markdown');
require('prismjs/components/prism-python');
const { SubMenu } = Menu;

const switchType = (fileType, lang) => {
    return (editor) => {
        let code = editor.textContent;
        editor.innerHTML = Prism.highlight(
            code,
            lang,
            fileType,
        );
    };
}
const getType = (file, key, path) => {
    //console.log(file,key,path);
    if (key == path + '/' + file.name) {
        return file.type;
    } else if (file.type == 'dir') {
        for (var i = 0; i < file.children.length; i++) {
            let tmp = getType(file.children[i], key, path + '/' + file.name);
            if (tmp != null) return tmp;
        }
    }
    return null;
}
const searchType = (files, key) => {
    for (var i = 0; i < files.length; i++) {
        let tmp = getType(files[i], key, '');
        if (tmp != null) return tmp;
    }
    return null;
}

const getMenuItem = (item, path) => {
    if (item.type != 'dir') {
        return (
            <Menu.Item key={path + '/' + item.name} icon={getIcon(item.type)}>
                {item.name}
            </Menu.Item>
        );
    } else {
        return (
            <SubMenu key={path + '/' + item.name} title={item.name} icon={getIcon(item.type)}>
                {item.children.map(child => getMenuItem(child, path + '/' + item.name))}
            </SubMenu>
        );
    }
}

const getPrism = (type) => {
    console.log(type);
    switch (type) {
        case "cpp":
        case "h":
        case "hpp":
        case "cc":
            return Prism.languages.cpp;
        case "c":
            return Prism.languages.c;
        case "sh":
            return Prism.languages.bash;
        case "md":
            return Prism.languages.markdown;
        case "py":
            return Prism.languages.python;
        default:
            return Prism.languages.makefile;
    }
}
class InputSample extends React.Component {
    render() {
        return (
            <Form
                name="inputSample"
                initialValues={{ text: '' }}
                onFinish={this.props.handleSubmit}
            >
                <Row>
                    <Col span={12} offset={4}>
                        <Form.Item
                            label={this.props.hint}
                            name='text'
                            rules={[{ required: true, message: '请输入 ' + this.props.hint }]}
                        >
                            <Input placeholder={'请输入 ' + this.props.hint} />

                        </Form.Item>
                    </Col>
                    <Col span={3} offset={1}>
                        <Form.Item>
                            <Button type="primary" htmlType="submit">
                                提交
                        </Button>
                        </Form.Item>
                    </Col>
                </Row>
            </Form>
        );
    }
};
class ControlPanel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            files: props.files,
        }
    };
    tryAddFile = (structure, path, dep, file) => {
        if (dep == path.length - 2) {
            for (var i = 0; i < structure.length; i++) {
                if (structure[i].name == file.name) {
                    message.error('文件已存在');
                    return { res: -1 };
                }
            }
            structure.push(file);
            return { res: 0, obj: structure };
        } else {
            for (var i = 0; i < structure.length; i++) {
                if (structure[i].type == 'dir' && structure[i].name == path[dep + 1]) {
                    var ret = this.tryAddFile(structure[i].children, path, dep + 1, file);
                    if (ret.res == -1) {
                        return { res: -1 };
                    } else {
                        structure[i].children = ret.obj;
                        return { res: 0, obj: structure };
                    }
                }
            }
            message.error('文件路径未创建');
            return { res: -1 };
        }
    };
    newFile = (values) => {
        let files = this.state.files;
        let tmp = searchType(files, values.text);
        if (tmp) {
            message.error('文件已存在！');
            return;
        }
        var path = values.text.split('/');
        if (path.length < 2) {
            message.error('文件需包含完整路径，以根目录"/"开头');
            return;
        }
        if (!path[1]) {
            message.error('文件名不能为空');
            return;
        }
        var file = { name: path[path.length - 1], type: path[path.length - 1].split('.')[1] }
        var ret = this.tryAddFile(files, path, 0, file);
        if (ret.res == 0) {
            this.props.setFiles(ret.obj);
            this.props.setPanel('');
            message.success('创建文件成功');
        }
    };
    tryRmFile = (structure, path, dep, file) => {
        if (dep == path.length - 2) {
            for (var i = 0; i < structure.length; i++) {
                if (structure[i].name == file.name) {
                    structure.splice(i, 1);
                    return { res: 0, obj: structure };
                }
            }
            message.error('文件不存在')
            return { res: -1 };
        } else {
            for (var i = 0; i < structure.length; i++) {
                if (structure[i].type == 'dir' && structure[i].name == path[dep + 1]) {
                    var ret = this.tryAddFile(structure[i].children, path, dep + 1, file);
                    if (ret.res == -1) {
                        return { res: -1 };
                    } else {
                        structure[i].children = ret.obj;
                        return { res: 0, obj: structure };
                    }
                }
            }
            message.error('文件路径未创建');
            return { res: -1 };
        }
    };
    rmFile = (values) => {
        let files = this.state.files;
        var path = values.text.split('/');
        if (path.length < 2) {
            message.error('文件需包含完整路径，以根目录"/"开头');
            return;
        }
        if (!path[1]) {
            message.error('文件名不能为空');
            return;
        }
        var file = { name: path[path.length - 1], type: path[path.length - 1].split('.')[1] }
        var ret = this.tryRmFile(files, path, 0, file);
        if (ret.res == 0) {
            this.props.setFiles(ret.obj);
            this.props.setPanel('');
            message.success('删除文件成功');
        }
    }
    tryAddDir = (structure, path, dep, file) => {
        if (dep == path.length - 2) {
            for (var i = 0; i < structure.length; i++) {
                if (structure[i].name == file.name) {
                    message.error('文件夹已存在');
                    return { res: -1 };
                }
            }
            structure.push(file);
            return { res: 0, obj: structure };
        } else {
            for (var i = 0; i < structure.length; i++) {
                if (structure[i].type == 'dir' && structure[i].name == path[dep + 1]) {
                    var ret = this.tryAddDir(structure[i].children, path, dep + 1, file);
                    if (ret.res == -1) {
                        return { res: -1 };
                    } else {
                        structure[i].children = ret.obj;
                        return { res: 0, obj: structure };
                    }
                }
            }
            message.error('文件夹路径未创建');
            return { res: -1 };
        }
    };
    newDir = (values) => {
        let files = this.state.files;
        let tmp = searchType(files, values.text);
        if (tmp) {
            message.error('文件夹已存在！');
            return;
        }
        var path = values.text.split('/');
        if (path.length < 2) {
            message.error('文件夹需包含完整路径，以根目录"/"开头');
            return;
        }
        if (!path[1]) {
            message.error('文件夹名不能为空');
            return;
        }
        var file = { name: path[path.length - 1], type: 'dir', children: [] }
        var ret = this.tryAddDir(files, path, 0, file);
        if (ret.res == 0) {
            this.props.setFiles(ret.obj);
            this.props.setPanel('');
            message.success('创建文件夹成功');
        }
    };
    tryRmDir = (structure, path, dep, file) => {
        for (var i = 0; i < structure.length; i++) {
            if (structure[i].name == file.name) {
                structure.splice(i, 1);
                return { res: 0, obj: structure };
            }
        }
        if (dep < path.length - 2) {
            for (var i = 0; i < structure.length; i++) {
                if (structure[i].type == 'dir' && structure[i].name == path[dep + 1]) {
                    var ret = this.tryRmDir(structure[i].children, path, dep + 1, file);
                    if (ret.res == -1) {
                        return { res: -1 };
                    } else {
                        structure[i].children = ret.obj;
                        return { res: 0, obj: structure };
                    }
                }
            }
        }
        message.error('文件夹不存在');
        return { res: -1 };

    };
    rmDir = (values) => {
        let files = this.state.files;
        var path = values.text.split('/');
        if (path.length < 2) {
            message.error('文件夹需包含完整路径，以根目录"/"开头');
            return;
        }
        if (!path[1]) {
            message.error('文件夹名不能为空');
            return;
        }
        var file = { name: path[path.length - 1], type: 'dir' }
        var ret = this.tryRmDir(files, path, 0, file);
        if (ret.res == 0) {
            this.props.setFiles(ret.obj);
            this.props.setPanel('');
            message.success('删除文件成功');
        }
    }
    render() {
        console.log(this.props.panel);
        if (this.props.panel == 'submit') {
            return (
                <Card title="Submit" className="contentContainer">
                    <Row>
                        <Col span={3} offset={8}>
                            <Button style={{ width: "100%" }}>查看配置信息</Button>
                        </Col>
                        <Col span={3} offset={2}>
                            <Button type="primary" style={{ width: "100%" }}>提交编译</Button>
                        </Col>
                    </Row>

                </Card>
            );
        } else if (this.props.panel == 'newFile') {
            return (
                <Card title="Files" className="contentContainer">

                    <InputSample hint="文件名" handleSubmit={this.newFile} />

                </Card>
            );
        } else if (this.props.panel == 'rmFile') {
            return (
                <Card title="Files" className="contentContainer">

                    <InputSample hint="文件名" handleSubmit={this.rmFile} />

                </Card>
            );
        } else if (this.props.panel == 'newDir') {
            return (
                <Card title="Files" className="contentContainer">

                    <InputSample hint="文件夹名" handleSubmit={this.newDir} />

                </Card>
            );
        } else if (this.props.panel == 'rmDir') {
            return (
                <Card title="Files" className="contentContainer">

                    <InputSample hint="文件夹名" handleSubmit={this.rmDir} />

                </Card>
            );
        }
    }
}
class LangBug extends React.Component {
    render() {
        return (
            <ReactCodeJar
                style={{ height: "320px" }}
                code={this.props.code} // Initial code value
                onUpdate={this.props.setCode} // Update the text
                highlight={withLineNumbers(switchType(this.props.lang, getPrism(this.props.lang)))}
            />
        );
    }
}
const CodeEditor = () => {
    const [code, setCode] = useState(' ');
    const [files, setFiles] = useState([
        { type: "py", name: 'main.py' },
        {
            type: "dir", name: "header", children: [
                { type: "h", name: 'code.h' }
            ]
        }]);
    const [savedCode, saveCode] = useState({});
    const [fileNow, setFileNow] = useState('/main.py');
    const [panel, setPanel] = useState('submit');
    const [clipboard, setClipboard] = useState('');

    const cut = (e) => {
        setClipboard(code);
        setCode('');
    }
    const copy = (e) => {
        setClipboard(code);
    }
    const paste = (e) => {
        setCode(clipboard)
    }


    const fileMenu = (
        <Menu theme="dark" mode="horizontal" >
            <Menu.Item key="newFile" onClick={(e) => { setPanel('newFile'); }}>
                新建文件
        </Menu.Item>
            <Menu.Item key="rmFile" onClick={(e) => { setPanel('rmFile'); }}>
                删除文件
        </Menu.Item>
            <Menu.Item key="newDir" onClick={(e) => { setPanel('newDir'); }}>
                新建文件夹
        </Menu.Item>
            <Menu.Item key="rmDir" onClick={(e) => { setPanel('rmDir'); }}>
                删除文件夹
        </Menu.Item>
        </Menu>
    );
    const editMenu = (
        <Menu theme="dark" mode="horizontal" >
            <Menu.Item key="cut" onClick={cut}>
                剪切
        </Menu.Item>
            <Menu.Item key="copy" onClick={copy}>
                复制
        </Menu.Item>
            <Menu.Item key="paste" onClick={paste}>
                粘贴
        </Menu.Item>
        </Menu>
    );
    const vmMenu = (
        <Menu theme="dark" mode="horizontal" >
            <Menu.Item key="env" >
                环境变量
        </Menu.Item>
            <Menu.Item key="opt">
                编译选项
        </Menu.Item>
            <Menu.Item key="dep">
                库依赖
        </Menu.Item>
        </Menu>
    );
    const compileMenu = (
        <Menu theme="dark" mode="horizontal" >
            <Menu.Item key="submit" onClick={(e) => { setPanel('submit'); }}>
                提交编译
        </Menu.Item>
        </Menu>
    );

    const handleSelect = (e) => {

        let tmp = savedCode;
        tmp[fileNow] = code;
        saveCode(tmp);
        setFileNow(e.key);
        setCode(tmp[e.key] ? tmp[e.key] : '');
        console.log(tmp);
    }
    return (
        <Base menu="normal" chosen="2">
            <div>

                <Layout style={{ backgroundColor: "black" }}>
                    <Row style={{ paddingBottom: "0px", marginBottom: "0px" }}>
                        <Col span={2} className="codePanel">
                            <Dropdown overlay={fileMenu} trigger={['click']}>
                                <a style={{ color: "white" }}>
                                    文件(F)
                            </a>
                            </Dropdown>
                        </Col>
                        <Col span={2} className="codePanel">
                            <Dropdown overlay={editMenu} trigger={['click']}>
                                <a style={{ color: "white" }}>
                                    编辑(E)
                        </a>
                            </Dropdown>
                        </Col>
                        <Col span={2} className="codePanel">
                            <Dropdown overlay={vmMenu} trigger={['click']}>
                                <a style={{ color: "white" }}>
                                    配置(O)
                            </a>
                            </Dropdown>
                        </Col>
                        <Col span={2} className="codePanel">
                            <Dropdown overlay={compileMenu} trigger={['click']}>
                                <a style={{ color: "white" }}>
                                    编译(C)
                            </a>
                            </Dropdown>
                        </Col>
                    </Row>

                    <Row style={{ paddingTop: "0px", marginTop: "0px" }}>
                        <Col span={4}>
                            <Menu theme="dark" mode="inline" style={{ height: "340px", overflowY: "auto", borderBottomLeftRadius: "6px", borderTopLeftRadius: "6px" }}
                                onSelect={handleSelect}
                                defaultSelectedKeys={['/main.py']}>
                                {files.map(child => getMenuItem(child, ''))}
                            </Menu>
                        </Col>
                        <Col span={20}>
                            {searchType(files, fileNow) == 'cpp' ? (<div class="editor"><LangBug lang={'cpp'} code={code} setCode={setCode} /></div>) : null}
                            {searchType(files, fileNow) == 'py' ? (<div class="editor"><LangBug lang={'py'} code={code} setCode={setCode} /></div>) : null}


                        </Col>
                    </Row>
                </Layout>
                <Layout>
                    <ControlPanel panel={panel} files={files} setFiles={setFiles} setPanel={setPanel} />
                </Layout>
            </div>
        </Base>
    );
};
export default CodeEditor;