import React from 'react';
import Base from '../superbase';
import axios from 'axios';
import { Typography, Form, Input, Select,Button} from 'antd';
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
class ProblemSubmit extends React.Component {
    render() {
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
                        
                    >
                        <Form.Item
                            label="Problem"
                            name="pid"
                        >
                            <Input disabled />
                        </Form.Item>

                        <Form.Item
                            label="Algorithm"
                            name="program"
                        >
                            <Select>
                            <Option value="1">DCT</Option>
              <Option value="2">DFT</Option>
              <Option value="3">DWT</Option>
              <Option value="4">DL</Option>
              <Option value="5">DCT-DWT</Option>
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