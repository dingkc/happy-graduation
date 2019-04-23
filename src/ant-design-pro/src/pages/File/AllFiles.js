import React, { PureComponent } from 'react';
import { Table, Button, Row, Col, Input, Icon, Breadcrumb, Modal, Tooltip, Upload, message } from 'antd';
import styles from './AllFile.less';
import deleteIcon from '@/assets/delete.svg';
import downloadIcon from '@/assets/download.svg';
import moveIcon from '@/assets/move.svg';
import renameIcon from '@/assets/rename.svg';
import shareIcon from '@/assets/share.svg';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import RequestUtils from '@/components/Dingkc/Common/RequestUtils';

const Search = Input.Search;
const InputGroup = Input.Group;

let newParentFileId = -1;
let moveFile = {};

@connect(({ file, user }) => ({
  file,
  user
  // loading: loading.models.file,
}))
export default class AllFiles extends PureComponent{

  state = {
    selectedRowKeys: [],
    visible: false
  }

  componentDidMount = () => {
    const params = [];
    this.cleanParams(params);
    this.getFileList(null);
  }

  cleanParams = (params) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'file/cleanParams',
      payload: params,
    })
  }

  getFileList = (param) => {
    const { dispatch } = this.props;
    const params = {
      pageNumber: 1,
      pageSize: 10,
      ...param,
    };
    dispatch({
      type: 'file/getFileList',
      payload: params,
    })
  }

  onSelectChange = (selectedRowKeys,selectRows) => {
    this.setState({
      selectedRowKeys
    })
  }

  handleChange = (pagination,fileType) => {
    console.log('pagination=====',pagination)
    const params = {
      pageNumber: pagination.current,
      pageSize: pagination.pageSize,
    };
      this.getFileList(params);
  }

  handleMoveChange = (pagination) => {
  const params = {
    pageNumber: pagination.current,
    pageSize: pagination.pageSize,
  };
    this.getMoveFileList(params)
}

  searchFile = (value) => {
    const params = {
      fileName: value
    }
    this.getFileList(params);
  }

  deleteFile = (ftpFileId) => {
    const { dispatch } = this.props;
    const thiz = this;
    const params = {
      ftpFileId: ftpFileId
    }
    Modal.confirm({
      title: '提示',
      content: '确定删除该文件？',
      okText: '确定',
      cancelText: '取消',
      onOk() {
        dispatch({
          type: 'file/deleteFile',
          payload: params,
        }).then((data) => {
          console.log('data===',data)
          // if(data) {
            thiz.getFileList();
          // }
        });
      },
    });
  }

  clickFile = (record,type) => {
    const { dispatch } = this.props;
    if(record.fileType === 'dir' && type !== 'move'){
      const params = {
        parentFileId: record.ftpFileId,
      }
      this.changeBread(record);
      this.getFileList(params);
    }
    else {
      //跳转到文件预览页面
      // this.pageJump(`/file/${record.ftpFileId}/fileDetail`)
    }
    if(record.fileType === 'dir' && type === 'move'){
      const params = {
        parentFileId: record.ftpFileId,
      }
      this.getMoveFileList(params);
      newParentFileId = record.ftpFileId
    }
  }

  getMoveFileList = (param) => {
    const { dispatch } = this.props;
    const params = {
      pageNumber: 1,
      pageSize: 10,
      ...param,
    };
    dispatch({
      type: 'file/getMoveFileList',
      payload: params,
    })
  }

  changeBread = (record) => {
    const { dispatch } = this.props;
    console.log('ftyfile===',record)
    dispatch({
      type: 'file/changeBreads',
      payload: record,
    })
  }

  pageJump = (pathname) => {
    const { dispatch } = this.props;
    dispatch(routerRedux.push({
      pathname: pathname,
    }));
  }

  downloadFile = (fileUuidName, fileName) => {
    const { dispatch } = this.props;
    const params = {
      fileUuidName: fileUuidName,
      fileName: fileName
    }
    dispatch({
      type: 'file/downloadFile',
      payload: params
    })
  }

  showMoveModal = (record) => {
    moveFile = record;
    this.setState({
      visible: true
    })
    const params = {
      fileType: 'dir'
    }
    this.getMoveFileList(params)
  }

  handleOk = () => {
    const { dispatch } = this.props;
    let params = moveFile;
    params.newParentFileId = newParentFileId;
    params.ftpFileId = Number(params.ftpFileId)
    dispatch({
      type: 'file/moveFile',
      payload: params
    })
    console.log('params=====',params)
    this.setState({
      visible: false
    })
  }

  handleCancle = () => {
    this.setState({
      visible: false
    })
  }

  backToMain = (record) => {
    if(record){
      const { breads } = this.props.file;
      let params = [];
      for(let i = 0;i < breads.length; i++){
        params.push(breads[i]);
        if(breads[i].ftpFileId === record.ftpFileId){
          break;
        }
      }
      this.cleanParams(params);
      this.getFileList({parentFileId: record.ftpFileId});
    }
    else {
      const params = [];
      this.getFileList(null);
      this.cleanParams(params);
    }
  }

  uploadFile = (info) => {
    // const { dispatch } = this.props;
    // dispatch({
    //   type: 'file/uploadFile',
    //   payload: { file: file },
    // })
      console.log('info=====',info)
      // if (info.file.status !== 'uploading') {
      //   console.log(info.file, info.fileList);
      // }
      // if (info.file.status === 'done') {
      //   message.success(`${info.file.name} file uploaded successfully`);
      // } else if (info.file.status === 'error') {
      //   message.error(`${info.file.name} file upload failed.`);
      // }
      if(info.file.response){
        if(info.file.response.status === '0'){
          message.success('上传成功');
          this.getFileList();
        }
        else {
          message.error(info.file.response.errMessage)
        }
      }
  }

  render() {
    const { selectedRowKeys, visible } = this.state;
    const { fileList, breads, moveFiles } = this.props.file;
    const moveColumns = [
      {
        key: 'fileName',
        title: '文件名',
        dataIndex: 'fileName',
        width: '60%',
        render: (text, record) => {
          return (
            <div span={20}><Icon type="folder-open" theme="filled" className={styles.iconSty}/> <a onClick={()=>this.clickFile(record,'move')}>{text}</a></div>
          )
        }
      },
      {
        key: 'updateDate',
        title: '修改日期',
        dataIndex: 'doneDate',
        width: '40%',
      }
    ];
    const columns = [
      {
        key: 'fileName',
        title: '文件名',
        dataIndex: 'fileName',
        width: '60%',
        render: (text, record) => {
          return (
            <Row>
              <Col span={20}><Icon type="folder-open" theme="filled" className={styles.iconSty}/> <a onClick={()=>this.clickFile(record)}>{text}</a></Col>
              <Col span={4} className={styles.showIcon}>
                <Tooltip placement="top" title='分享'>
                  <img src={shareIcon} />
                </Tooltip>
                <Tooltip placement="top" title='下载'>
                  <a href={`${RequestUtils.requestPath}/ftpFiles/downloads?fileUuidName=${record.fileUuidName}&fileName=${record.fileName}}`} download="">
                    <img src={downloadIcon} className={styles.marginIcon} onClick={() => this.downloadFile(record.fileUuidName, record.fileName)}/>
                  </a>
                </Tooltip>
                <Tooltip placement="top" title='重命名'>
                  <img src={renameIcon} className={styles.marginIcon}/>
                </Tooltip>
                <Tooltip placement="top" title='移动到'>
                  <img src={moveIcon} className={styles.marginIcon} onClick={() => this.showMoveModal(record)}/>
                </Tooltip>
                <Tooltip placement="top" title='删除'>
                  <img src={deleteIcon} onClick={()=>this.deleteFile(record.ftpFileId)} className={styles.marginIcon}/>
                </Tooltip>
              </Col>
            </Row>
          )
        }
      },
      {
        key: 'fileSize',
        title: '大小',
        dataIndex: 'fileSize',
        width: '20%',
      },
      {
        key: 'updateDate',
        title: '修改日期',
        dataIndex: 'doneDate',
        width: '20%',
      }
    ];
    const rowSelection = {
      selectedRowKeys,
      onChange: this.onSelectChange,
      //   (selectedRowKeys, selectedRows) => {
      //   console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      // },
      getCheckboxProps: record => ({
        disabled: record.name === 'Disabled User', // Column configuration not to be checked
        name: record.name,
      }),
    };
    const props = {
      name: 'file',
      action: '/api/v1/ftpFiles/uploads',
      showUploadList: false,
      // headers: {
      //   authorization: 'authorization-text',
      // },
      // supportServerRender: true,
      onChange:this.uploadFile,
      // onChange(info) {
      //   console.log('info=====',info)
      //   // if (info.file.status !== 'uploading') {
      //   //   console.log(info.file, info.fileList);
      //   // }
      //   // if (info.file.status === 'done') {
      //   //   message.success(`${info.file.name} file uploaded successfully`);
      //   // } else if (info.file.status === 'error') {
      //   //   message.error(`${info.file.name} file upload failed.`);
      //   // }
      //   if(info.file.response){
      //     if(info.file.response.status === '0'){
      //       message.success('上传成功');
      //     }
      //     else {
      //       message.error(info.file.response.errMessage)
      //     }
      //   }
      // },
    };
    return (
      <div className={styles.main}>
        <Row>
          <Col span={2}>
            <Upload {...props}>
              <Button type='primary' icon='upload'>上传</Button>
            </Upload>
          </Col>
          <Col span={4}><Button icon='file-add' className={styles.marginSty}>新建文件夹</Button></Col>
          <Col span={10}>
            <InputGroup style={{display: selectedRowKeys.length === 0 ? 'none' : 'block'}}>
              <Button className={styles.leftBtnSty}>分享</Button>
              <Button className={styles.middleBtnSty}>下载</Button>
              <Button className={styles.middleBtnSty}>重命名</Button>
              <Button className={styles.middleBtnSty}>移动到</Button>
              <Button className={styles.rightBtnSty}>删除</Button>
            </InputGroup>
          </Col>
          <Col span={6}>
            <Search
              placeholder="搜索"
              onSearch={this.searchFile}
              className={styles.searchSty}
            />
          </Col>
        </Row>
        <Breadcrumb style={{marginTop: '15px'}}>
          <Breadcrumb.Item>文件</Breadcrumb.Item>
          <Breadcrumb.Item><a onClick={() => this.backToMain()}>文件夹</a></Breadcrumb.Item>
          {breads.map(d => <Breadcrumb.Item key={d.ftpFileId}><a onClick={() => this.backToMain(d)}>{d.fileName}</a></Breadcrumb.Item>)}
        </Breadcrumb>
        <Table
        columns={columns}
        rowKey='ftpFileId'
        rowSelection={rowSelection}
        className={styles.tableHead}
        dataSource={fileList.fileListList}
        pagination={fileList.fileListPagination}
        onChange={this.handleChange}
        />
        <Modal
          title="移动到"
          visible={visible}
          okText='移动'
          onOk={this.handleOk}
          onCancel={this.handleCancle}
        >
          <Table
            columns={moveColumns}
            rowKey='ftpFileId'
            dataSource={moveFiles.moveFilesList}
            pagination={moveFiles.moveFilesPagination}
            onChange={this.handleMoveChange}
          />
        </Modal>
      </div>
    );
  }
}
