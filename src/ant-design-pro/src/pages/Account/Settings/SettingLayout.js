import React, { PureComponent } from 'react';
import {Menu, Icon, Layout} from 'antd';
import { connect } from 'dva';
import Link from "umi/link";
import BaseView from "./BaseView";
import SecurityView from "./SecurityView";
import styles from './SettingLayout.less';
// import AllFiles from './AllFiles';
// import NoteList from './Custom/NoteList';

const { Content, Sider } = Layout;
const { SubMenu } = Menu;

@connect(({ menu }) => ({
  menu
}))
export default class SettingLayout extends PureComponent{

  state = {
    selectedMenu: '1',
  }

  selectMenu = (value) => {
    console.log('value====',value)
    this.setState({
      selectedMenu: value.key
    })
  }

  render() {
    const { selectedMenu } = this.state;
    return (
      <Layout style={{ padding: '24px 0', background: '#fff' }}>
        <Sider width={200} style={{ background: '#fff' }}>
          <Menu
            mode="inline"
            defaultSelectedKeys={['1']}
            // defaultOpenKeys={['sub1']}
            style={{ height: '100%' }}
            onSelect={this.selectMenu}
          >
            {/*<SubMenu key="sub1" title={<span><Icon type="file-text" />个人</span>}>*/}
              <Menu.Item key="1">基本设置</Menu.Item>
              <Menu.Item key="2">安全设置</Menu.Item>
            {/*</SubMenu>*/}
          </Menu>
        </Sider>
        <Content>
          {selectedMenu === '1' ?
            (
              <div className={styles.main}>
                <span className={styles.titleSty}>基本设置</span>
                <BaseView />
              </div>

            )
            :
            (
              <div className={styles.main}>
                <span className={styles.titleSty}>安全设置</span>
                <SecurityView />
              </div>
            )}
        </Content>
      </Layout>
    );
  }
}
