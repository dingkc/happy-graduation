import React, { PureComponent } from 'react';
import { Form, Icon, Input } from 'antd';

const FormItem = Form.Item;
class PasswordModalForm extends PureComponent {
  state = {
    visible: false,
    help: '',
  };

  checkConfirm = (rule, value, callback) => {
    const { form } = this.props;
    if (value && value !== form.getFieldValue('password')) {
      callback('两次输入的密码不匹配!');
    } else {
      callback();
    }
  };

  checkPassword = (rule, value, callback) => {
    if (!value) {
      this.setState({
        help: '请输入密码！',
        visible: !!value,
      });
      callback();
    } else {
      this.setState({
        help: '',
      });
      if (!this.state.visible) {
        this.setState({
          visible: !!value,
        });
      }
      if (value.length < 6) {
        callback('密码长度不能少于6位');
      } else {
        const { form } = this.props;
        form.validateFields(['confirm'], { force: true });
        callback();
      }
    }
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
    return (
      <Form>
        <FormItem {...formItemLayout} label="旧密码">
          {getFieldDecorator('oldPassword', {
            rules: [
              {
                required: true,
                message: '请输入旧密码！',
              },
            ],
          })(
            <Input
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder="旧密码"
            />
          )}
        </FormItem>
        <FormItem {...formItemLayout} label="新密码">
          {getFieldDecorator('password', {
            rules: [
              {
                required: true,
                message: '请输入密码！',
              },
              {
                validator: this.checkPassword,
              },
            ],
          })(
            <Input
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder="新密码"
            />
          )}
        </FormItem>
        <FormItem {...formItemLayout} label="确认新密码">
          {getFieldDecorator('confirm', {
            rules: [
              {
                required: true,
                message: '请确认密码！',
              },
              {
                validator: this.checkConfirm,
              },
            ],
          })(
            <Input
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder="确认新密码"
            />
          )}
        </FormItem>
      </Form>
    );
  }
}

const PasswordModal = Form.create()(PasswordModalForm);

export default PasswordModal;
