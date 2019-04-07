import React, { Component, Fragment } from 'react';
import { formatMessage, FormattedMessage } from 'umi/locale';
import { List, Modal, message } from 'antd';
import styles from './SecurityView.less';
import { connect } from 'dva';
import EmailModal from '../../../components/Dingkc/UserPersonalMessage/EmailModal';
import PasswordModal from '../../../components/Dingkc/UserPersonalMessage/PasswordModal';
// import { getTimeDistance } from '@/utils/utils';

const passwordStrength = {
  strong: (
    <font className="strong">
      <FormattedMessage id="app.settings.security.strong" defaultMessage="Strong" />
    </font>
  ),
  medium: (
    <font className="medium">
      <FormattedMessage id="app.settings.security.medium" defaultMessage="Medium" />
    </font>
  ),
  weak: (
    <font className="weak">
      <FormattedMessage id="app.settings.security.weak" defaultMessage="Weak" />
      Weak
    </font>
  ),
};

@connect(({ user }) => ({
  currentUser: user.currentUser,
}))
class SecurityView extends Component {
  state = {
    visible: false,
    modalId:'',
    modalTitle:'',
  };
  getData = (currentUser) => [
    {
      title: formatMessage({ id: 'app.settings.security.password' }, {}),
      description: (
        <Fragment>
          {formatMessage({ id: 'app.settings.security.password-description' })}：
          {passwordStrength.strong}
        </Fragment>
      ),
      actions: [
        <a onClick={() => this.showModal(1)}>
          <FormattedMessage id="app.settings.security.modify" defaultMessage="Modify" />
        </a>,
      ],
    },
    {
      title: formatMessage({ id: 'app.settings.security.email' }, {}),
      description: (
        <Fragment>
          {formatMessage({ id: 'app.settings.security.email-description' })}
          {currentUser.email}
        </Fragment>
      ),
      actions: [
        <a onClick={() => this.showModal(4)}>
          <FormattedMessage id="app.settings.security.modify" defaultMessage="Modify" />
        </a>,
      ],
    },
  ];

  showModal = (modalId) => {
    this.setState({
      visible: true,
      modalId: modalId,
    });
    if(modalId === 1) {
      this.setState({
        modalTitle: "修改登录密码",
      });
    } else if(modalId === 2) {
      this.setState({
        modalTitle: "修改密保手机",
      });
    } else if(modalId === 3) {
      this.setState({
        modalTitle: "设置密保问题",
      });
    } else if(modalId === 4) {
      this.setState({
        modalTitle: "修改邮箱",
      });
    }
  };

  getModal = (modalId) => {
    if(modalId === 1){
      return <PasswordModal ref="getModalFormValue" />
    } else if(modalId === 2){

    } else if(modalId === 3){

    } else if(modalId === 4){
      return <EmailModal ref="getModalFormValue" username={this.props.currentUser.username}/>;
    }
  };

  ModalFormSubmit = () => {
    let modalform = this.refs.getModalFormValue;//通过refs属性可以获得对话框内form对象
    modalform.validateFields((err, values) => {
      if(!err) {
        const params = {
          userId: this.props.currentUser.userId,
          ...values,
        };
        this.updateUser(params);
      }
    });
  };

  updateUser = (param) => {
    const { dispatch } = this.props;
    const thiz = this;
    dispatch({
      type: 'user/updateUser',
      payload: param,
      callback(resp) {
        if (resp) {
          if (resp.status === '0') {
            thiz.hideModal();
            message.success("修改用户信息成功");
            dispatch({
              type: 'user/fetchCurrent',
              payload:{queryType:1}
            });
          } else {
            message.error(resp.errMessage);
          }
        }
      },
    });
  };

  hideModal = () => {
    this.setState({
      visible: false,
    });
  };

  render() {
    const { currentUser } = this.props;
    return (
      <Fragment>
        <List
          itemLayout="horizontal"
          dataSource={this.getData(currentUser)}
          renderItem={item => (
            <List.Item actions={item.actions}>
              <List.Item.Meta title={item.title} description={item.description} />
            </List.Item>
          )}
        />
        <Modal
          title={this.state.modalTitle}
          visible={this.state.visible}
          onOk={this.ModalFormSubmit}
          onCancel={this.hideModal}
          destroyOnClose={true}
        >
          {
            this.getModal(this.state.modalId)
          }
        </Modal>
      </Fragment>
    );
  }
}

export default SecurityView;
