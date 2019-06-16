import React, { PureComponent } from 'react';
import { Button, Col, Input, Modal, Row } from 'antd';
import { connect } from 'dva';

@connect(({ share }) => ({
  share,
  // loading: loading.models.file,
}))
export default class AddFriendsModal extends PureComponent{

  handleOk = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'share/showAddModal',
      payload: { visible: false },
    })
  }

  handleCancel = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'share/showAddModal',
      payload: { visible: false },
    })
  }

  render() {
    const { addFriendsModal } =this.props.share;
    console.log('addFriendsModal====',addFriendsModal)
    return (
      <div>
        <Modal
          title='添加好友'
          visible={addFriendsModal.visible}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
        >
          <div>
            邮箱：
          </div>
          <Row>
            <Col span={19}><Input /></Col>
            <Col offset={1} span={4}><Button type='primary'>搜索</Button></Col>
          </Row>
        </Modal>
      </div>
    );
  }
}
