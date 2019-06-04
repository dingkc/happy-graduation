import React, { PureComponent } from 'react';
import {Table, Button, Input, Row, Col, Tooltip, Modal } from 'antd';
import styles from './RecycleList.less';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import folderIcon from '@/assets/folder.svg';
import deleteIcon from '@/assets/delete.svg';
import jpgIcon from '@/assets/jpg.svg';
import docIcon from '@/assets/doc.svg';
import mp3Icon from '@/assets/mp3.svg';
import vedioIcon from '@/assets/vedio.svg';
import reductionIcon from '@/assets/reduction.svg';

const Search = Input.Search;

@connect(({ recycle }) => ({
  recycle
}))
export default class RecycleList extends PureComponent{

  componentDidMount = () => {
    this.getRecycleList(null);
  }

  getRecycleList = (param) => {
    const { dispatch } = this.props;
    const params = {
      pageNumber: 1,
      pageSize: 10,
      ...param
    }
    dispatch({
      type: 'recycle/getRecycleList',
      payload: params
    })
  }

  tableChange = (pagination) => {
    const params = {
      pageNumber: pagination.current,
      pageSize: pagination.pageSize,
    }
    this.getRecycleList(params);
  }

  searchRecycle = (value) => {
    const params = {
      recycleBinName: value
    }
    this.getRecycleList(params);
  }

  deleteRecycleFile = (recycleBinId) => {
    const { dispatch } = this.props;
    const thiz = this;
    const params = {
      recycleBinId: recycleBinId
    }
    Modal.confirm({
      title: '提示',
      content: '删除后将无法找回，确定删除该文件？',
      okText: '确定',
      cancelText: '取消',
      onOk() {
        dispatch({
          type: 'recycle/deleteRecycle',
          payload: params,
        }).then((response) => {
          thiz.getRecycleList(null);
        });
      },
    });
  }

  reductionRecycle = (recycleBinId) => {
    const { dispatch } = this.props;
    const thiz = this;
    const params = {
      recycleBinId: recycleBinId
    }
    Modal.confirm({
      title: '提示',
      content: '确定还原该文件？',
      okText: '确定',
      cancelText: '取消',
      onOk() {
        dispatch({
          type: 'recycle/reductionRecycle',
          payload: params,
        }).then((response) => {
          thiz.getRecycleList(null);
        });
      },
    });
  }

  cleanAllRecycle = () => {
    const { dispatch } = this.props;
    const thiz = this;
    Modal.confirm({
      title: '提示',
      content: '清空后将不可恢复，确定清空回收站？',
      okText: '确定',
      cancelText: '取消',
      onOk() {
        dispatch({
          type: 'recycle/cleanAllRecycle',
          payload: '',
        }).then((response) => {
          thiz.getRecycleList(null);
        });
      },
    });
  }


  render(){
    const { recycleFiles } = this.props.recycle;
    const columns = [
      {
        key: 'fileName',
        dataIndex: 'fileName',
        title: '文件名',
        width: '50%',
        render: (text, record) => {
          return (
            <div>
              <Row>
                <Col span={22}>
                  {record.fileType === 'dir' ? <img src={folderIcon} /> //文件夹
                    : record.fileType === 'doc' || record.fileType === 'docx' ? <img src={docIcon} /> //文档
                      : record.fileType === 'jpg' || record.fileType === 'png' ? <img src={jpgIcon}/> //图片
                        : record.fileType === 'avi' || record.fileType === 'MP4' || record.fileType === 'wmv' ? <img src={vedioIcon}/> //视频
                          : record.fileType === 'MP3' ? <img src={mp3Icon}/> //音乐
                            : null}
                  <a className={styles.marginIcon}>{text}</a>
                </Col>
                <Col span={2}>
                  <Tooltip placement="top" title="还原">
                    <img src={reductionIcon} onClick={() => this.reductionRecycle(record.recycleBinId)}/>
                  </Tooltip>
                  <Tooltip placement="top" title='删除'>
                    <img src={deleteIcon} onClick={()=>this.deleteRecycleFile(record.recycleBinId)} className={styles.marginIcon}/>
                  </Tooltip>
                </Col>
              </Row>
            </div>
          )
        }
      },
      {
        key: 'fileSize',
        dataIndex: 'fileSize',
        title: '大小',
        width: '10%',
      },
      {
        key: 'doneDate',
        dataIndex: 'doneDate',
        title: '删除时间 ',
        width: '20%',
      },
      {
        key: 'expireDate',
        dataIndex: 'expireDate',
        title: '有效时间',
        width: '20%',
      }
    ];
    return(
      <div className={styles.main}>
        <Row>
          <Col span={16}><Button type='primary' onClick={this.cleanAllRecycle}>清空回收站</Button></Col>
          <Col span={8}>
            <Search
              placeholder="搜索"
              onSearch={this.searchRecycle}
              className={styles.searchSty}
            />
          </Col>
        </Row>
        <Table
          rowKey='recycleBinId'
          columns={columns}
          dataSource={recycleFiles.recycleFilesList}
          pagination={recycleFiles.recycleFilesPagination}
          className={styles.tableHead}
          onChange={this.tableChange}
        />
      </div>
    )
  }
}
