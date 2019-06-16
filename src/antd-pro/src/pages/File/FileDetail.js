import React, { PureComponent } from 'react';
import { Breadcrumb, Table, Button } from 'antd';
import { connect } from 'dva';
import styles from './FileDetail.less'
import { routerRedux } from 'dva/router';

@connect(({ file, user }) => ({
  file,
  user
  // loading: loading.models.file,
}))
export default class FileDetail extends PureComponent{

  componentDidMount = () => {
    this.getFileDetail();
    console.log('this.props===',this.props.match.params.ftpFileId)
  }

  getFileDetail = () => {
    const { dispatch } = this.props;
    const params = {
      ftpFileId: this.props.match.params.ftpFileId
    }
    dispatch({
      type: 'file/getFileDetail',
      payload: params,
    })
  }

  pageJump = (pathname) => {
    const { dispatch } = this.props;
    dispatch(routerRedux.push({
      pathname: pathname,
    }));
  }

  render() {
    const { fileList, breads, moveFiles } = this.props.file;
    return (
      <div className={styles.main}>
        <Breadcrumb style={{marginTop: '15px'}}>
          <Breadcrumb.Item><a onClick={() => this.pageJump('/file')}>文件</a></Breadcrumb.Item>
          <Breadcrumb.Item>文件夹</Breadcrumb.Item>
          {/*{breads.map(d => <Breadcrumb.Item key={d.ftpFileId}><a onClick={() => this.backToMain(d)}>{d.fileName}</a></Breadcrumb.Item>)}*/}
          <Breadcrumb.Item><a style={{ color: '#40A9FF'}}>信息传播工程学院毕业论文撰写信息</a></Breadcrumb.Item>
        </Breadcrumb>
        <div style={{ marginTop: '10px'}}>
          <Button>分享</Button>
          <Button style={{ marginLeft: '5px'}}>下载</Button>
          <Button style={{ marginLeft: '5px'}}>移动到</Button>
        </div>
        <div style={{ marginTop: '10px', width: '70%', marginLeft: '15%'}}>
          <p style={{ fontWeight: 800}}>1.引言</p>
          <p style={{ fontWeight: 800}}>1.1 目的</p>
          <p>为了规范我院本科生论文的格式，提高论文撰写水平，便于信息系统的收集、存储、处理、加工、检索、利用、交流和传播，特制定本规范。</p>
          <p style={{ fontWeight: 800}}>1.2 制定原则</p>
          <p>本规范参照《长春工业大学毕业设计（论文）规范化要求》，并部分依据国家标准制定。</p>
          <p></p>
          <p style={{ fontWeight: 800}}>2.论文内容格式</p>
          <p style={{ fontWeight: 800}}>2.1 论文类别</p>
          <p>2.1.1 论文自审稿，评阅稿和存档稿；</p>
          <p>2.1.2 自审稿为指导老师检查时使用，没有致谢段落，在论文封面、封底等各部分均不能提及关于指导老师的任何信息，封底为指导老师意见；</p>
          <p>2.1.3 评阅稿为论文背对背评审时使用，没有致谢段落，在论文封面、封底等各部分均不能提及关于指导老师的任何信息，封底为评阅老师意见；</p>
          <p>2.1.4 存档稿为论文答辩及学院最终存档使用。</p>
          <p style={{ fontWeight: 800}}>2.2 论文结构</p>
        </div>
      </div>
    )
  }
}
