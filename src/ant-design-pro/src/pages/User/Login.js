import React, { Component } from 'react';
import { connect } from 'dva';
import { formatMessage, FormattedMessage } from 'umi/locale';
import Link from 'umi/link';
import { Checkbox, Alert, Icon } from 'antd';
import Login from '@/components/Login';
import styles from './Login.less';

const { Tab, UserName, Password, Mobile, Captcha, Submit } = Login;

@connect(({ login, loading }) => ({
  login,
  submitting: loading.effects['login/login'],
}))
class LoginPage extends Component {
  state = {
    type: 'account',
    autoLogin: true,
  };

  onTabChange = type => {
    this.setState({ type });
  };

  onGetCaptcha = () =>
    new Promise((resolve, reject) => {
      this.loginForm.validateFields(['mobile'], {}, (err, values) => {
        if (err) {
          reject(err);
        } else {
          const { dispatch } = this.props;
          dispatch({
            type: 'login/getCaptcha',
            payload: values.mobile,
          })
            .then(resolve)
            .catch(reject);
        }
      });
    });

  handleSubmit = (err, values) => {
    const { type } = this.state;
    if (!err) {
      const { dispatch } = this.props;
      dispatch({
        type: 'login/login',
        payload: {
          ...values,
          type,
        },
      });
    }
  };

  changeAutoLogin = e => {
    this.setState({
      autoLogin: e.target.checked,
    });
  };

  renderMessage = content => (
    <Alert style={{ marginBottom: 24 }} message={content} type="error" showIcon />
  );

  render() {
    const { login, submitting } = this.props;
    const { type, autoLogin } = this.state;
    return (
      <div className={styles.main}>
        <Login
          defaultActiveKey={type}
          onTabChange={this.onTabChange}
          onSubmit={this.handleSubmit}
          ref={form => {
            this.loginForm = form;
          }}
        >
          {/*<Tab key="account" tab={formatMessage({ id: 'app.login.tab-login-credentials' })}>*/}
          <div className={styles.divSty}>
          <div className={styles.titleFont}>帐号登录</div>
          <div className={styles.userSty}>
            {login.status === 'error' &&
              login.type === 'account' &&
              !submitting &&
              this.renderMessage(formatMessage({ id: '账户名或密码错误' }))}
            <UserName
              name="username"
              // placeholder='请输入用户名'
              rules={[
                {
                  required: true,
                  message: '请输入用户名',
                },
              ]}
            />
            <Password
              name="password"
              // placeholder='请输入密码'
              rules={[
                {
                  required: true,
                  message: '请输入密码',
                },
              ]}
              onPressEnter={e => {
                e.preventDefault();
                this.loginForm.validateFields(this.handleSubmit);
              }}
            />
            <div className={styles.other}>
              {/*<Link className={styles.leftSty} to="/user/register">注册帐号</Link>*/}
              <Link className={styles.leftSty} to="/user/register">注册帐号</Link>
              <Link className={styles.register} to="/user/forgetPassword">忘记密码 |</Link>
              <a className={styles.register}>反馈</a>
              {/*<Link className={styles.register} to="/user/forget">*/}
                {/*忘记密码 |*/}
              {/*</Link>*/}
              {/*<Link className={styles.register} to="/user/register">*/}
                {/*反馈*/}
              {/*</Link>*/}
            </div>
            <Submit loading={submitting} className={styles.submitSty}>
              <FormattedMessage id="app.login.login" />
            </Submit>
          </div>
          </div>
          {/*</Tab>*/}


        </Login>
      </div>
    );
  }
}

export default LoginPage;
