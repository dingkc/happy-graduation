import React, { PureComponent } from 'react';
import { Table,Row,Col, Button, Modal, Input, List, Spin } from 'antd';
import styles from './Friends.less';
import { connect } from 'dva';
import InfiniteScroll from 'react-infinite-scroller';
import AddFriendsModal from './AddFriendsModal';
import FriendInformation from './FriendInformation';
import FriendsApprove from './FriendsApprove';

@connect(({ share }) => ({
  share,
  // loading: loading.models.file,
}))
export default class Friends extends PureComponent{

  state = {
    clicked: false,
    friendClick: true
  }

  componentDidMount = () => {
    this.getFriendsList();
  }

  getFriendsList = (param) => {
    const { dispatch } = this.props;
    const params = {
      pageNumber: 1,
      pageSize: 10,
      ...param,
    }
    dispatch({
      type: 'getFriendsList',
      payload: params,
    })
  }

  friendsRequest = () => {
    this.setState({
      clicked: true,
      friendClick: false
    })
  }

  friendClick = () => {
    this.setState({
      friendClick: true,
      clicked: false
    })
  }

  showAddFriend = () => {
    const { dispatch } = this.props;
    dispatch({
      type: 'share/showAddModal',
      payload: { visible: true },
    })
  }

  render() {
    const { clicked,friendClick } = this.state;
    const { showAddFriendsModal } = this.props.share;
    return (
      <div>
        <Row>
          <Col span={4}>
            <div className={styles.borderSty}>
              <div className={clicked ? styles.hoverTitleSty : styles.titleSty} onClick={this.friendsRequest}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>好友请求</div>
              </div>
              <div className={styles.numberSty}>A</div>
              <div className={friendClick ? styles.hoverTitleSty : styles.titleSty} onClick={this.friendClick}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>a傅小小</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>a徐芳</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>a贾杰</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>a赖秀英</div>
              </div>
              <div className={styles.numberSty}>B</div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>b段娟</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>b范敏</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>b尚尚</div>
              </div>
              <div className={styles.numberSty}>C</div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>c夏秀英</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>c常霞</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>c钱磊</div>
              </div>
              <div className={styles.titleSty}>
                <div className={styles.avatar}><img src='https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' alt='avatar' /> </div>
                <div className={styles.avatar_name}>c郭杰</div>
              </div>

              <div>
                <Button className={styles.leftBtnSty}>创建群组</Button>
                <Button className={styles.rightBtnSty} onClick={this.showAddFriend}>添加好友</Button>
              </div>
            </div>
          </Col>
          <Col span={20}>
            {/*<FriendsApprove />*/}
            {clicked ? <FriendsApprove /> : <FriendInformation/>}
          </Col>
        </Row>
        <AddFriendsModal />
      </div>
    );
  }
}
