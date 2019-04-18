import React, { Component } from 'react';
import { connect } from 'dva';
import { formatMessage, FormattedMessage } from 'umi/locale';
import Link from 'umi/link';
// import router from 'umi/router';
import { Form, Input, Button, Select, Row, Col, Popover, Progress } from 'antd';
import styles from './Register.less';

const FormItem = Form.Item;
const { Option } = Select;
const InputGroup = Input.Group;

const passwordStatusMap = {
    ok: (
      <div className={styles.success}>
  <FormattedMessage id="validation.password.strength.strong" />
  </div>
),
pass: (
<div className={styles.warning}>
<FormattedMessage id="validation.password.strength.medium" />
  </div>
),
poor: (
<div className={styles.error}>
<FormattedMessage id="validation.password.strength.short" />
  </div>
),
};

const passwordProgressMap = {
  ok: 'success',
  pass: 'normal',
  poor: 'exception',
};
let verifyCodeId= '';

@connect(({ register, loading }) => ({
  register,
  submitting: loading.effects['register/submit'],
}))
@Form.create()
export default class Register extends Component {
  state = {
    count: 0,
    confirmDirty: false,
    visible: false,
    help: '',
    prefix: '86',
    gitLoading: false,
    gitPasswordCheckIcon: null,
    userUnique: true,
  };

  // componentDidUpdate() {
  //   const { form, register } = this.props;
  //   const account = form.getFieldValue('mail');
  //   if (register.status === 'ok') {
  //     router.push({
  //       pathname: '/user/register-result',
  //       state: {
  //         account,
  //       },
  //     });
  //   }
  // }

  componentWillReceiveProps(nextProps) {
    const account = this.props.form.getFieldValue('email');
    if (nextProps.register.status === 'ok') {
      this.props.dispatch(
        routerRedux.push({
          pathname: '/user/login',
          state: {
            account,
          },
        }),
      );
    }
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  };

  componentDidMount = () =>{

  }

  onGetCaptcha = () =>{
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
    const { thiz } = this;
    const email = form.getFieldValue('email');
    form.validateFields('email',{ force: true },(err, values) =>{
      if(!err){
        dispatch({
          type: 'register/getVerifyCodes',
          payload: {
            mailAccount: email,
            verifyCodeType: 1,
          },
          callback(resp) {
            verifyCodeId = resp.data;
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

  handleSubmit = e => {
    e.preventDefault();
    const { form, dispatch } = this.props;
    form.validateFields({ force: true }, (err, values) => {
      let params = {
        ...values,
        verifyCodeId: verifyCodeId,
      };
      if (!err) {
        this.props.dispatch({
          type: 'register/submit',
          payload: params,
        });
      }
    });
  };

  handleConfirmBlur = e => {
    const { value } = e.target;
    const { confirmDirty } = this.state;
    this.setState({ confirmDirty: confirmDirty || !!value });
  };

  checkConfirm = (rule, value, callback) => {
    const { form } = this.props;
    if (value && value !== form.getFieldValue('password')) {
      callback(formatMessage({ id: 'validation.password.twice' }));
    } else {
      callback();
    }
  };

  checkVerifyCode = (rule, value, callback) => {
    const { dispatch, form } = this.props;
    const verifyCode = value;
    const email = form.getFieldValue('email');
    if (verifyCode === undefined || verifyCode.trim() === '' || verifyCode.length==5) {
      callback();
      return;
    }
    const verifyCodeId = verifyCodeId;
    if (verifyCodeId === undefined || verifyCodeId === '') {
      callback('请先发送验证码！');
      return;
    }
    // dispatch({
    //   type: 'register/checkVerifyCode',
    //   payload: { verifyCode: verifyCode, verifyCodeId: verifyCodeId, email: email },
    //   callback(resp) {
    //     if (resp.status === "0") {
    //       callback();
    //     } else {
    //       callback(resp.errMessage);
    //     }
    //   },
    // });
  };

  checkPassword = (rule, value, callback) => {
    const { visible, confirmDirty } = this.state;
    if (!value) {
      this.setState({
        help: formatMessage({ id: 'validation.password.required' }),
        visible: !!value,
      });
      callback('error');
    } else {
      this.setState({
        help: '',
      });
      if (!visible) {
        this.setState({
          visible: !!value,
        });
      }
      if (value.length < 6) {
        callback('密码不能少于6从头再来');
      } else {
        const { form } = this.props;
        if (value && confirmDirty) {
          form.validateFields(['confirm'], { force: true });
        }
        callback();
      }
    }
  };

  changePrefix = value => {
    this.setState({
      prefix: value,
    });
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

  render() {
    const { form, submitting } = this.props;
    const { getFieldDecorator } = form;
    const { count, prefix, help, visible } = this.state;
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
          <div className={styles.titleFont}>注册帐号</div>
          <div className={styles.formSty}>
            <Form onSubmit={this.handleSubmit} className={styles.bottomSty}>
              <FormItem {...formItemLayout} label='用户名'>
                {
                  getFieldDecorator('username', {
                    rules: [
                      {
                        required: true,
                        message: '请输入用户名！',
                      },
                      {
                        pattern: /^[^\s]*$/,
                        message: '用户名不能包含空格！'
                      },
                      {
                        max: 20,
                        message: '用户名不能超过20个字符！',
                      },
                      {
                        min: 4,
                        message: '用户名不能少于4个字符！',
                      },
                    ]
                  })(<Input placeholder="请输入用户名" />)
                }
              </FormItem>
              <FormItem {...formItemLayout} label="姓名" hasFeedback>
                {getFieldDecorator('name', {
                  validateTrigger: 'onBlur',
                  rules: [
                    {
                      required: true,
                      message: '请输入姓名！',
                    },
                    {
                      max: 10,
                      message: '姓名不能超过10个字符！',
                    },
                    {
                      pattern: /^[^\s]*$/,
                      message: '姓名不能包含空格！'
                    }
                  ],
                })(<Input placeholder="请输入真实姓名" />)}
              </FormItem>
              <FormItem {...formItemLayout} label="密码" hasFeedback help={help}>
                <Popover
                  getPopupContainer={node => node.parentNode}
                  content={
                    <div style={{ padding: '4px 0' }}>
                      {passwordStatusMap[this.getPasswordStatus()]}
                      {this.renderPasswordProgress()}
                      <div style={{ marginTop: 10 }}>
                        <FormattedMessage id="validation.password.strength.msg" />
                      </div>
                    </div>
                  }
                  overlayStyle={{ width: 240 }}
                  placement="right"
                  visible={visible}
                >
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
                      type="password"
                      placeholder={formatMessage({ id: 'form.password.placeholder' })}
                    />
                  )}
                </Popover>
              </FormItem>
              <FormItem {...formItemLayout} label='确认密码'>
                {getFieldDecorator('confirm', {
                  rules: [
                    {
                      required: true,
                      message: formatMessage({ id: 'validation.confirm-password.required' }),
                    },
                    {
                      validator: this.checkConfirm,
                    },
                  ],
                })(
                  <Input
                    type="password"
                    placeholder={formatMessage({ id: 'form.confirm-password.placeholder' })}
                  />
                )}
              </FormItem>
              <FormItem {...formItemLayout} label="邮箱" hasFeedback>
                {getFieldDecorator('email', {
                  rules: [
                    {
                      required: true,
                      message: formatMessage({ id: 'validation.email.required' }),
                    },
                    {
                      type: 'email',
                      message: formatMessage({ id: 'validation.email.wrong-format' }),
                    },
                  ],
                })(
                  <Input placeholder={formatMessage({ id: 'form.email.placeholder' })} />
                )}
              </FormItem>
              <FormItem {...formItemLayout}
                        label="邮箱验证码">
                <Row>
                  <Col span={16}>
                    {getFieldDecorator('verifyCode', {
                      rules: [
                        {
                          required: true,
                          message: '请输入验证码！',
                        },
                        {
                          validator: this.checkVerifyCode,
                          validateStatus: 'error',
                        },
                      ],
                    })(<Input placeholder="验证码" className={styles.inputSty}/>)}
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
              <FormItem {...tailFormItemLayout}>
                <Button
                  loading={submitting}
                  className={styles.submit}
                  type="primary"
                  htmlType="submit"
                >
                  <FormattedMessage id="app.register.register" />
                </Button>
                <Link className={styles.login} to="/user/login">
                  <FormattedMessage id="app.register.sign-in" />
                </Link>
              </FormItem>
            </Form>
          </div>
        </div>
      </div>
  );
  }
}
