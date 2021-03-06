import React, { PureComponent } from 'react';
import {Menu, Icon, Layout} from 'antd';
import { connect } from 'dva';
import Link from "umi/link";
import AllFiles from './AllFiles';
import RecycleList from './RecycleList';

const { Content, Sider } = Layout;
const { SubMenu } = Menu;
const MenuItemGroup = Menu.ItemGroup;

@connect(({ menu }) => ({
  menu
}))
export default class FileLayout extends PureComponent{
  state = {
    menuKey: 'file',
  };
  getNavMenuItems = menusData => {
    if (!menusData) {
      return [];
    }
    return menusData
      .filter(item => item.name && !item.hideInMenu)
      .map(item => this.getSubMenuOrItem(item))
      .filter(item => item);
  };

  getSubMenu = subMenu => {
    if(subMenu){
      const children = subMenu.children ? subMenu.children : [];
      return <SubMenu key={subMenu.path} title={<span>{subMenu.name}</span>}>{children.map((item) => this.getMenuItem(item))}</SubMenu>
      // return <SubMenu key="sub1" title={<span><Icon type="user" />subnav 1</span>}></SubMenu>
    }
  }

  getMenuItem = menuItem => {
    if(menuItem){
      console.log('menuItem====',menuItem)
      return <Menu.Item key={menuItem.path}>{menuItem.name}</Menu.Item>
    }
  }

  getSubMenuOrItem = item => {
    // doc: add hideChildrenInMenu
    if (item.children && !item.hideChildrenInMenu && item.children.some(child => child.name)) {
      const { name } = item;
      return (
        <SubMenu
          title={
            item.icon ? (
              <span>
                {getIcon(item.icon)}
                <span>{name}</span>
              </span>
            ) : (
              name
            )
          }
          key={item.path}
        >
          {/*{this.getNavMenuItems(item.children)}*/}
        </SubMenu>
      );
    }
    return <Menu.Item key={item.path}>{this.getMenuItemPath(item)}</Menu.Item>;
  };

  getMenuItemPath = item => {
    const { name } = item;
    const itemPath = this.conversionPath(item.path);
    const icon = getIcon(item.icon);
    const { target } = item;
    // Is it a http link
    if (/^https?:\/\//.test(itemPath)) {
      return (
        <a href={itemPath} target={target}>
          {icon}
          <span>{name}</span>
        </a>
      );
    }
    const { location, isMobile, onCollapse } = this.props;
    return (
      <Link
        to={itemPath}
        target={target}
        replace={itemPath === location.pathname}
        onClick={
          isMobile
            ? () => {
              onCollapse(true);
            }
            : undefined
        }
      >
        {icon}
        <span>{name}</span>
      </Link>
    );
  };

  menuClick = (item) => {
    console.log('item====',item)
    this.setState({
      menuKey: item.key
    })
  }


  render() {
    const { menuData } = this.props.menu;
    const { menuKey } = this.state;
    console.log('布局menuData=====',menuData)
    return (
      <Layout style={{ padding: '24px 0', background: '#fff' }}>
        <Sider width={200} style={{ background: '#fff' }}>
          <Menu
            mode="inline"
            defaultSelectedKeys={['file']}
            // defaultOpenKeys={['sub1']}
            style={{ height: '100%' }}
            onClick={this.menuClick}
          >
            {/*{this.getNavMenuItems(menuData.children)}*/}
            {/*{menuData.map((item) => this.getSubMenu(item))}*/}
            {/*<SubMenu key="sub1" title={<span><Icon type="file-text" />文件</span>}>*/}
            {/*<MenuItemGroup title={<span><Icon type="file-text" /> 全部文件</span>}>*/}
              <Menu.Item key="file"><Icon type="file-text" /> 全部文件</Menu.Item>
              {/*<Menu.Item key="2">图片</Menu.Item>*/}
              {/*<Menu.Item key="3">文档</Menu.Item>*/}
              {/*<Menu.Item key="4">视频</Menu.Item>*/}
              {/*<Menu.Item key="5">音乐</Menu.Item>*/}
            {/*</SubMenu>*/}
            {/*<SubMenu key="sub2" title={<span><Icon type="delete" />回收站</span>}>*/}
            {/*</SubMenu>*/}
            {/*</MenuItemGroup>*/}
            {/*<MenuItemGroup title={<span><Icon type="delete" /> 全部文件</span>}>*/}
              <Menu.Item key='recycle'><Icon type="delete" /> 回收站</Menu.Item>
            {/*</MenuItemGroup>*/}
          </Menu>
        </Sider>
        <Content>
          {menuKey === 'file' ? <AllFiles /> : <RecycleList />}
        </Content>
      </Layout>
    );
  }
}
