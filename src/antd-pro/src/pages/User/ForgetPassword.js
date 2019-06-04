import React, { PureComponent } from 'react';
import { connect } from 'dva';
import { Link } from 'dva/router';
import { Form, Input, Button, Select, Row, Col, Popover, Progress,Icon,message } from 'antd';
import styles from './ForgetPassword.less';
import { routerRedux } from 'dva/router';

const FormItem = Form.Item;

const passwordStatusMap = {
  ok: <div className={styles.success}>强度：强</div>,
  pass: <div className={styles.warning}>强度：中</div>,
  poor: <div className={styles.error}>强度：太短</div>,
};

const passwordProgressMap = {
  ok: 'success',
  pass: 'normal',
  poor: 'exception',
};

@connect(({ forgetPassword,loading }) => ({
  forgetPassword,
  loading: loading.models.forgetPassword,
}))

@Form.create()
export default class ForgetPassword extends PureComponent {
  state = {
    count: 0,
    confirmDirty: false,
    visible: false,
    help: '',
    verifyCodeId:'',
  };

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  //获取邮箱验证码
  onGetCaptcha = () => {
    const { dispatch,form } = this.props;
    const thiz = this;
    form.validateFields('username',{ force: true },(err, values) =>{
      if(!err){
        let count = 59;
        this.setState({ count });
        this.interval = setInterval(() => {
          count -= 1;
          this.setState({ count });
          if (count === 0) {
            clearInterval(this.interval);
          }
        }, 1000);

        dispatch({
          type: 'forgetPassword/getVerifyCodes',
          payload: {
            verifyCodeType: 2,
            username:this.props.form.getFieldValue('username'),
          },
          callback(resp) {
            // console.log("获取回调验证码id====",resp);
            if(resp.status==='0'){
              thiz.setState({
                verifyCodeId: resp.data,
              });
            }
            else {
              message.error(resp.errMessage);
            }
          },
        });
      }
    })
  };

  getPasswordStatus = () => {
    const { form } = this.props;
    const value = form.getFieldValue('password');
    if (value && value.length > 9) {
      return 'ok';
    }
    if (value && value.length > 5) {
      return 'pass';
    }
    return 'poor';
  };

  pageJumpLogin = () => {
    const { dispatch } = this.props;
    dispatch(
      routerRedux.push({
        pathname: '/user/login',
      })
    );
  };

  handleSubmit = e => {
    e.preventDefault();
    const thiz = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        let params={
          ...values,
          verifyCodeId: this.state.verifyCodeId,
        };
        this.props.dispatch({
          type: 'forgetPassword/submit',
          payload: params,
          callback(rep){
            if (rep.status === '0') {
              message.success('恭喜，重置密码成功');
              // yield put(routerRedux.push('/user/login'));
              thiz.pageJumpLogin();
            } else {
              message.error(rep.errMessage);
            }
          }
        });
      }
    });
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
        visible: !!value,
      });
      callback('');
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
        callback('请至少输入 6 个字符');
      } else {
        const { form } = this.props;
        if (value && this.state.confirmDirty) {
          form.validateFields(['confirm'], { force: true });
        }
        callback();
      }
    }
  };

  renderPasswordProgress = () => {
    const { form } = this.props;
    const value = form.getFieldValue('password');
    const passwordStatus = this.getPasswordStatus();
    return value && value.length ? (
      <div className={styles[`progress-${passwordStatus}`]}>
        <Progress
          status={passwordProgressMap[passwordStatus]}
          className={styles.progress}
          strokeWidth={6}
          percent={value.length * 10 > 100 ? 100 : value.length * 10}
          showInfo={false}
        />
      </div>
    ) : null;
  };

  //校验邮箱验证码
  checkVerifyCode = (rule, value, callback) => {
    const { dispatch, form } = this.props;
    const thiz = this;
    const verifyCode = value;
    if (verifyCode === undefined || verifyCode.trim() === '') {
      callback();
      return;
    }
    const verifyCodeId = this.state.verifyCodeId;

    if (verifyCodeId === undefined || verifyCodeId === '') {
      callback('请先获取验证码！');
      return;
    }
    dispatch({
      type: 'forgetPassword/checkVerifyCode',
      payload: { verifyCode: verifyCode, verifyCodeId: verifyCodeId,verifyCodeType:2},
      callback(resp) {
        if (resp.status === "0") {
          callback();
        } else {
          callback(resp.errMessage);
        }
      },
    });
  };


  render() {
    const { form, submitting } = this.props;
    const { getFieldDecorator} = form;
    const { count } = this.state;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 },
      },
    };
    const tailFormItemLayout = {
      wrapperCol: {
        xs: {
          span: 24,
          offset: 0,
        },
        sm: {
          span: 18,
          offset: 6,
        },
      },
    };

    return (
      <div className={styles.main}>
        <div className={styles.divSty}>
          <div className={styles.titleFont}>忘记密码</div>
          <div className={styles.formSty}>
            <Form onSubmit={this.handleSubmit} className={styles.bottomSty}>
              <FormItem {...formItemLayout} label="用户名">
                {getFieldDecorator('username', {
                  rules: [
                    {
                      required:true,
                      message: '请输入用户名',
                    },
                    {
                      pattern: /^[^\s]*$/,
                      message: '用户名不能包含空格！'
                    }
                  ],
                })(
                  <Input placeholder="用户名"/>
                )}
              </FormItem>
              <FormItem {...formItemLayout} label="邮箱验证码">
                <Row>
                  <Col span={16}>
                    {getFieldDecorator('verifyCode', {
                      rules: [
                        {
                          required: true,
                          message: '请输入邮箱验证码！',
                        },
                        {
                          validator: this.checkVerifyCode,
                          validateStatus: 'error',
                        },
                      ],
                    })(<Input placeholder="邮箱验证码" className={styles.inputSty}/>)}
                  </Col>
                  <Col span={8}>
                    <Button
                      disabled={count}
                      className={styles.getCaptcha}
                      onClick={this.onGetCaptcha}
                    >
                      {count ? `${count} s` : '获取验证码'}
                    </Button>
                  </Col>
                </Row>
              </FormItem>
              <FormItem {...formItemLayout} label="新密码">
                <Popover
                  content={
                    <div style={{ padding: '4px 0' }}>
                      {passwordStatusMap[this.getPasswordStatus()]}
                      {this.renderPasswordProgress()}
                      <div style={{ marginTop: 10 }}>
                        请至少输入 6 个字符。请不要使用容易被猜到的密码。
                      </div>
                    </div>
                  }
                  overlayStyle={{ width: 240 }}
                  placement="right"
                  visible={this.state.visible}
                >
                  {getFieldDecorator('password', {
                    rules: [
                      {
                        required: true,
                        message:"请输入密码",
                      },
                      {
                        validator: this.checkPassword,
                      },
                    ],
                  })(<Input type="password" placeholder="至少6位密码，区分大小写"/>)}
                </Popover>
              </FormItem>
              <FormItem {...formItemLayout} label="确认新密码">
                {getFieldDecorator('confirm', {
                  rules: [
                    {
                      required: true,
                      message: '请确认新密码！',
                    },
                    {
                      validator: this.checkConfirm,
                    },
                  ],
                })(<Input type="password" placeholder="确认新密码"/>)}
              </FormItem>
              <FormItem {...tailFormItemLayout} >
                <Button
                  loading={submitting}
                  className={styles.submit}
                  type="primary"
                  htmlType="submit"
                >
                  提交
                </Button>
                <Link className={styles.login} to="/user/login">
                  使用已有账户登录
                </Link>
              </FormItem>
            </Form>
          </div>
        </div>
      </div>
    );
  }
}
