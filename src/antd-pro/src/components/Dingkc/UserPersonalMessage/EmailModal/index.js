import React, { PureComponent } from 'react';
import { Form, Icon, Input, Row, Col, Button } from 'antd';
import styles from './index.less';
import { connect } from 'dva/index';
const FormItem = Form.Item;

@connect(({ register }) => ({
  
}))
class EmailModalForm extends PureComponent {
  state = {
    count: 0,
  };

  onGetCaptcha = () => {
    let count = 59;
    this.setState({ count });
    this.interval = setInterval(() => {
      count -= 1;
      this.setState({ count });
      if (count === 0) {
        clearInterval(this.interval);
      }
    }, 1000);
    const { dispatch, form } = this.props;
    const email = form.getFieldValue('email');
    form.validateFields('email', { force: true }, (err, values) => {
      if (!err) {
        dispatch({
          type: 'register/getVerifyCodes',
          payload: {
            mailAccount: email,
            username:this.props.username,
            verifyCodeType: 3,
          },
          callback(resp) {
            form.setFieldsValue({
              verifyCodeId: resp.data,
            })
          },
        });
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 },
      },
    };
    const { count } = this.state;
    return (
      <Form>
        <FormItem {...formItemLayout} label="邮箱">
          {getFieldDecorator('email', {
            rules: [
              { required: true, message: '请输入邮箱!' },
              { type: 'email', message: '邮箱地址格式错误！', validateStatus: 'error' },
            ],
          })(
            <Input
              prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="邮箱"
            />
          )}
        </FormItem>
        <FormItem {...formItemLayout} label="邮箱验证码">
          <Row gutter={8}>
            <Col span={16}>
              {getFieldDecorator('verifyCode', {
                rules: [{ required: true, message: '请输入邮箱验证码!' }],
              })(
                <Input
                  prefix={<Icon type="code" style={{ color: 'rgba(0,0,0,.25)' }} />}
                  placeholder="邮箱验证码"
                />
              )}
            </Col>
            <Col span={8} className={styles.position}>
              <Button
                type="primary"
                disabled={count}
                className={styles.verifyCode}
                onClick={this.onGetCaptcha}
              >
                {count ? `${count} s` : '获取验证码'}
              </Button>
            </Col>
          </Row>
        </FormItem>
        <FormItem>
          {getFieldDecorator('verifyCodeId', {
          })(
            <Input className={styles.displat}/>
          )}
        </FormItem>
      </Form>
    );
  }
}

const EmailModal = Form.create()(EmailModalForm);

export default EmailModal;
