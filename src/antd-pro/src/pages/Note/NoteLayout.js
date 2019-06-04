import React, { PureComponent } from 'react';
import {Menu, Icon, Layout} from 'antd';
import { connect } from 'dva';
import Link from "umi/link";
// import AllFiles from './AllFiles';
import NoteList from './Custom/NoteList';

const { Content, Sider } = Layout;
const { SubMenu } = Menu;

@connect(({ menu }) => ({
  menu
}))
export default class NoteLayout extends PureComponent{
  render() {
    return (
      <Layout style={{ padding: '24px 0', background: '#fff' }}>
        <Sider width={200} style={{ background: '#fff' }}>
          <Menu
            mode="inline"
            defaultSelectedKeys={['1']}
            defaultOpenKeys={['sub1']}
            style={{ height: '100%' }}
          >
            <SubMenu key="sub1" title={<span><Icon type="file-text" />全部记事本</span>}>
              <Menu.Item key="1">学校记事本</Menu.Item>
              <Menu.Item key="2">工作记事本</Menu.Item>
              <Menu.Item key="3">生活记事本</Menu.Item>
              <Menu.Item key="4">快递记事本</Menu.Item>
              <Menu.Item key="5">家庭记事本</Menu.Item>
            </SubMenu>
          </Menu>
        </Sider>
        <Content>
          <NoteList />
        </Content>
      </Layout>
    );
  }
}
