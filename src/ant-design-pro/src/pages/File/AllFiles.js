import React, { PureComponent } from 'react';
import {
  Table,
  Button,
  Row,
  Col,
  Input,
  Icon,
  Breadcrumb,
  Modal,
  Tooltip,
  Upload,
  message,
  InputNumber,
  Form
} from 'antd';
import styles from './AllFile.less';
import deleteIcon from '@/assets/delete.svg';
import downloadIcon from '@/assets/download.svg';
import moveIcon from '@/assets/move.svg';
import renameIcon from '@/assets/rename.svg';
import shareIcon from '@/assets/share.svg';
import folderIcon from '@/assets/folder.svg';
import jpgIcon from '@/assets/jpg.svg';
import docIcon from '@/assets/doc.svg';
import mp3Icon from '@/assets/mp3.svg';
import vedioIcon from '@/assets/vedio.svg';
import { connect } from 'dva';
import { routerRedux } from 'dva/router';
import RequestUtils from '@/components/Dingkc/Common/RequestUtils';

const Search = Input.Search;
const InputGroup = Input.Group;

let newParentFileId = -1;
let moveFile = {};

const FormItem = Form.Item;
const EditableContext = React.createContext();
const EditableRow = ({ form, index, ...props }) => (
  <EditableContext.Provider value={form}>
    <tr {...props} />
  </EditableContext.Provider>
);

const EditableFormRow = Form.create()(EditableRow);

class EditableCell extends React.Component {
  state = {
    editing: false,
  }

  toggleEdit = () => {
    const editing = !this.state.editing;
    this.setState({ editing }, () => {
      if (editing) {
        this.input.focus();
      }
    });
  }

  save = (e) => {
    const { record, handleSave, handleEdit } = this.props;
    this.form.validateFields((error, values) => {
      if (error && error[e.currentTarget.id]) {
        return;
      }
      this.toggleEdit();
      handleSave({ ...record, ...values });
      handleEdit();
    });
  }

  render() {
    const { editing } = this.state;
    const {
      editable,
      dataIndex,
      title,
      fileKey,
      record,
      index,
      handleSave,
      handleEdit,
      ...restProps
    } = this.props;
    return (
      <td {...restProps}>
        {editable ? (
          <EditableContext.Consumer>
            {(form) => {
              this.form = form;
              return (
                record.ftpFileId === fileKey ? (
                  <FormItem style={{ margin: 0 }}>
                    {form.getFieldDecorator(dataIndex, {
                      rules: [{
                        required: true,
                        message: `${title}不能为空`,
                      }],
                      initialValue: record[dataIndex],
                    })(
                      <Input
                        ref={node => (this.input = node)}
                        onPressEnter={this.save}
                        onBlur={this.save}
                      />
                    )}
                  </FormItem>
                ) : (
                  <div
                    className="editable-cell-value-wrap"
                    style={{ paddingRight: 24 }}
                    // onClick={this.toggleEdit}
                  >
                    {restProps.children}
                  </div>
                )
              );
            }}
          </EditableContext.Consumer>
        ) : restProps.children}
      </td>
    );
  }
}

@connect(({ file, user }) => ({
  file,
  user
  // loading: loading.models.file,
}))
@Form.create()
export default class AllFiles extends PureComponent{

  constructor(props){
    super(props);
    this.state = {
      selectedRowKeys: [],
      visible: false,
      addVisible: false,
      fileKey: undefined
    }
    this.columns = [
      {
        key: 'fileName',
        title: '文件名',
        dataIndex: 'fileName',
        editable: true,
        width: '60%',
        render: (text, record) => {
          return (
            <Row>
              <Col span={20}>
                {record.fileType === 'dir' ? <img src={folderIcon} /> //文件夹
                  : record.fileType === 'doc' || record.fileType === 'docx' ? <img src={docIcon} /> //文档
                    : record.fileType === 'jpg' || record.fileType === 'png' ? <img src={jpgIcon}/> //图片
                      : record.fileType === 'avi' || record.fileType === 'MP4' || record.fileType === 'wmv' ? <img src={vedioIcon}/> //视频
                        : record.fileType === 'MP3' ? <img src={mp3Icon}/> //音乐
                          : null}
                <a onClick={()=>this.clickFile(record)} className={styles.marginIcon}>{text}</a>
              </Col>
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
                  <img src={renameIcon} className={styles.marginIcon} onClick={() => this.resetFileName(record.ftpFileId)}/>
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
      },
    ]

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
        }).then((response) => {
            thiz.getFileList();
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
    this.setState({
      visible: false
    })
  }

  handleCancle = () => {
    this.setState({
      visible: false
    })
  }

  handleAddOk = (e) => {
    e.preventDefault();
    const { dispatch } = this.props;
    const thiz = this;
    this.props.form.validateFieldsAndScroll((err, values) => {
      console.log('values=== ',values)
      if (!err) {
        const params = {
          fileType: 'dir',
          fileName: values.fileName,
          parentFileId: newParentFileId,
        };
        this.setState({
          addVisible: false
        })
        dispatch({
          type: 'file/addFile',
          payload: params
        }).then((response) => {
          thiz.getFileList();
        });
      }
    })
  }

  handleAddCancle = () => {
    this.setState({
      addVisible: false
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

  resetFileName = (id) => {
    this.setState({
      fileKey: id
    })
  }

  handleEdit = (values) => {
    this.setState({
      fileKey:undefined
    })
  }

  handleSave = (values) => {
    console.log('传过来的值====',values)
    const { dispatch } = this.props;
    const thiz = this;
    // if(values.fileSize){
    //   delete values.fileSize;
    // }
    const params = values;
    dispatch({
      type: 'file/moveFile',
      payload: params,
    }).then((response) => {
      thiz.getFileList();
    });
  }

  showAddModal = () => {
    this.setState({
      addVisible: true
    })
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    const { selectedRowKeys, visible,addVisible,fileKey } = this.state;
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
      onChange:this.uploadFile,
    };
    const components = {
      body: {
        row: EditableFormRow,
        cell: EditableCell,
      },
    };
    const columns = this.columns.map((col) => {
      if (!col.editable) {
        return col;
      }
      return {
        ...col,
        onCell: record => ({
          record,
          editable: col.editable,
          dataIndex: col.dataIndex,
          title: col.title,
          fileKey: fileKey,
          handleEdit:this.handleEdit,
          handleSave: this.handleSave,
        }),
      };
    });
    return (
      <div className={styles.main}>
        <Row>
          <Col span={2}>
            <Upload {...props}>
              <Button type='primary' icon='upload'>上传</Button>
            </Upload>
          </Col>
          <Col span={4}><Button icon='file-add' className={styles.marginSty} onClick={this.showAddModal}>新建文件夹</Button></Col>
          <Col span={10}>
            <InputGroup
              style={{display: selectedRowKeys.length === 0 ? 'none' : 'block'}}
            >
              <Button className={styles.leftBtnSty}>分享</Button>
              <Button className={styles.middleBtnSty}>下载</Button>
              <Button className={styles.middleBtnSty}>重命名</Button>
              <Button className={styles.middleBtnSty}>移动到</Button>
              <Button className={styles.rightBtnSty}>删除</Button>
            </InputGroup>
          </Col>
          <Col span={8}>
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
            components={components}
            columns={columns}
            rowKey='ftpFileId'
            // rowSelection={rowSelection}
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
        <Modal
          title='新建文件夹'
          visible={addVisible}
          onOk={this.handleAddOk}
          onCancel={this.handleAddCancle}
        >
          <Form>
            <FormItem label='文件夹名称'>
              {getFieldDecorator('fileName', {
                rules: [
                  {
                    required: true, message: '文件名称不能为空!',
                  }],
              })(
                <Input/>,
              )}
            </FormItem>
          </Form>
        </Modal>
      </div>
    );
  }
}
