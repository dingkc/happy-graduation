import React, { PureComponent } from 'react';
import { Modal, Button } from 'antd';
import { connect } from 'dva';
import styles from './FriendInformation.less';

export default class FriendInformation extends PureComponent{
  render() {
    return (
      <div>
        <div className={styles.borderSty}>
          <div className={styles.centerSty}>
            <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
            <div className={styles.fontSty}>秦秀兰</div>
            <hr className={styles.hrSty} />
            <div className={styles.nameSty}>昵称：可云</div>
            <div className={styles.mailSty}>邮箱：q.hnunusou@gmail.com</div>
            <div className={styles.buttonSty}><Button type='primary' className={styles.btnSty}>分享文件</Button></div>
            <div className={styles.buttonSty}><Button className={styles.btnSty}>删除好友</Button></div>

          </div>
        </div>
      </div>
    );
  }
}
