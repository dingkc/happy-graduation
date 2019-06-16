import React, { PureComponent } from 'react';
import { Tabs } from 'antd';
import styles from './Share.less';
import Friends from './Custom/Friends';
import { connect } from 'dva';
import FileTab from './Custom/FileTab';

const { TabPane } = Tabs;

@connect(({ share, user }) => ({
  share,
  user
}))
export default class Share extends PureComponent{

  componentDidMount = () => {
    this.getFriendsList();
  }

  getFriendsList = (param) => {
    const { dispatch } = this.props;
    const { currentUser } = this.props.user;
    console.log('分享的当前用户信息====',currentUser)
    const params = {
      ownerId: 2,
      pageNumber: 1,
      pageSize: 10,
      ...param,
    }
    dispatch({
      type: 'share/getFriends',
      payload: params,
    })
  }

  render() {
    return(
      <div className={styles.main}>
        <Tabs defaultActiveKey='1'>
          <TabPane tab='好友' key='1'>
            <Friends />
          </TabPane>
          <TabPane tab='文件' key='2'>
            <FileTab />
          </TabPane>
        </Tabs>
      </div>
    );
  }
}
