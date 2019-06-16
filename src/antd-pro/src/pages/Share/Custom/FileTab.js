import React, { PureComponent } from 'react';
import { Tabs } from 'antd';
import { connect } from 'dva';
import AcceptFile from './AcceptFile';
import ShareFile from './ShareFile';

const { TabPane } = Tabs;

export default class FileTab extends PureComponent{
  render(){
    return(
      <div>
        <Tabs type='card'>
          <TabPane tab="接收文件" key='1'>
            <AcceptFile/>
          </TabPane>
          <TabPane tab="分享文件" key='2'>
            <ShareFile />
          </TabPane>
        </Tabs>
      </div>
    )
  }
}
