import React, { PureComponent } from 'react';
import { Button, Col, Input, Row } from 'antd';
import { connect } from 'dva';
import styles from './Friends.less';

export default class FriendsApprove extends PureComponent{
  render(){
    return(
      <div>
        <div className={styles.borderSty}>
          <div className={styles.borderSty1}>
            <Row>
              <Col span={2}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
              </Col>
              <Col span={18}>
                <div className={styles.messageSty}>
                  <div className={styles.nameSty}>周莘宇</div>
                  <div>对方已添加您为好友，您是否需要将对方加入您的好友列表</div>
                </div>
              </Col>
              <Col span={4}>
                <div className={styles.divBtnSty}>
                  <Button type='primary'>同意</Button>
                  <Button className={styles.btnLeftSty}>忽略</Button>
                </div>
              </Col>
            </Row>
          </div>
          <div className={styles.borderSty1}>
            <Row>
              <Col span={2}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
              </Col>
              <Col span={18}>
                <div className={styles.messageSty}>
                  <div className={styles.nameSty}>朱静</div>
                  <div>对方已成为您的好友</div>
                </div>
              </Col>
              <Col span={4}>
                <div className={styles.addSty}>
                  已添加
                </div>
              </Col>
            </Row>
          </div>
        </div>
      </div>
    )
  }
}
