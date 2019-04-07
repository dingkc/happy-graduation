import React, { PureComponent } from 'react';
import { Table, Button, Row, Col, Input, Icon, Breadcrumb } from 'antd';
import styles from './AllFile.less';
import deleteIcon from '@/assets/delete.svg';
import downloadIcon from '@/assets/download.svg';
import moveIcon from '@/assets/move.svg';
import renameIcon from '@/assets/rename.svg';
import shareIcon from '@/assets/share.svg';

const Search = Input.Search;
const InputGroup = Input.Group;

export default class AllFiles extends PureComponent{

  state = {
    selectedRowKeys: [],
  }

  onSelectChange = (selectedRowKeys,selectRows) => {
    console.log('条数====',selectRows)
    this.setState({
      selectedRowKeys
    })
  }

  render() {
    const { selectedRowKeys } = this.state;
    const columns = [
      {
        key: 'fileName',
        title: '文件名',
        dataIndex: 'fileName',
        width: '60%',
        render: (text, record) => {
          return (
            <Row>
              <Col span={20}><Icon type="folder-open" theme="filled" className={styles.iconSty}/> <span>{text}</span></Col>
              <Col span={4} className={styles.showIcon}>
                <img src={shareIcon} /> <img src={downloadIcon} /> <img src={renameIcon} /> <img src={moveIcon}/> <img src={deleteIcon} />
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
        dataIndex: 'updateDate',
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
    const data = [
      {
        fileId: 1,
        fileName: '名称',
        fileSize: '3.57G',
        updateDate: '2018-03-08'
      },
      {
        fileId: 2,
        fileName: '名称名称名称名称',
        fileSize: '43MB',
        updateDate: '2018-03-08'
      },
      {
        fileId: 3,
        fileName: '名称名称名称名称名称名称',
        fileSize: '1.6G',
        updateDate: '2018-03-08'
      },
      {
        fileId: 4,
        fileName: '名称',
        fileSize: '23K',
        updateDate: '2018-03-08'
      },
      {
        fileId: 5,
        fileName: '名称名称',
        fileSize: '23MB',
        updateDate: '2018-03-08'
      }
    ];
    return (
      <div className={styles.main}>
        <Row>
          <Col span={6}>
            <Button type='primary' icon='upload'>上传</Button>
            <Button icon='file-add' className={styles.marginSty}>新建文件夹</Button>
          </Col>
          <Col span={10}>
            <InputGroup style={{display: selectedRowKeys.length === 0 ? 'none' : 'block'}}>
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
              onSearch={value => console.log(value)}
              className={styles.searchSty}
            />
          </Col>
        </Row>
        <Breadcrumb style={{marginTop: '15px'}}>
          <Breadcrumb.Item>文件</Breadcrumb.Item>
          <Breadcrumb.Item><a>文件夹</a></Breadcrumb.Item>
        </Breadcrumb>
        <Table
        columns={columns}
        rowKey='fileId'
        rowSelection={rowSelection}
        className={styles.tableHead}
        dataSource={data}
        pagination={false}
        />
      </div>
    );
  }
}
