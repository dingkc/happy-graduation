import React, { PureComponent } from 'react';
import { Button, Table, Input, Row, Col, Tooltip, Popconfirm, Select } from 'antd';
import { connect } from 'dva';
import styles from './AcceptFile.less';
import deleteIcon from '@/assets/delete.svg';
import downloadIcon from '@/assets/download.svg';
import moveIcon from '@/assets/move.svg';
import renameIcon from '@/assets/rename.svg';
import shareIcon from '@/assets/share.svg';
import folderIcon from '@/assets/folder.svg';

const InputGroup = Input.Group;
const { Option } = Select;
const Search = Input.Search;

export default class AcceptFile extends PureComponent{

  state = {
    selectedRowKeys: [],
  }

  onSelectChange = (selectedRowKeys,selectRows) => {
    this.setState({
      selectedRowKeys
    })
  }

  render() {
    const { selectedRowKeys } =this.state;
    const data = [
      {
        fileId: 1,
        fileName: '名称名称',
        shareName: '谭敏仪',
        state: '有效',
        fileSize: '3.57G',
        shareDate: '2018-03-08'
      },
      {
        fileId: 2,
        fileName: '名称名称名称名称',
        shareName: '谢敏',
        state: '有效',
        fileSize: '43MB',
        shareDate: '2018-03-08'
      },
      {
        fileId: 3,
        fileName: '名称名称名称名称名称名称',
        shareName: '雷军',
        state: '有效',
        fileSize: '1.6G',
        shareDate: '2018-10-01'
      },
      {
        fileId: 4,
        fileName: '名称',
        shareName: '萧明',
        state: '撤回',
        fileSize: '23K',
        shareDate: '2018-08-08'
      },
      {
        fileId: 5,
        fileName: '名称名称',
        shareName: '姚超',
        state: '源文件丢失',
        fileSize: '23MB',
        shareDate: '2018-09-26'
      },
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
              <Col span={20}>
                <img src={folderIcon} /> <a className={styles.marginIcon}>{text}</a>
              </Col>
              <Col span={4}>
                <Tooltip placement="top" title='分享'>
                  <img src={shareIcon} />
                </Tooltip>
                <Tooltip placement="top" title='下载'>
                    {/*<a download="">*/}
                      <img src={downloadIcon} className={styles.marginIcon}/>
                    {/*</a>*/}
                  </Tooltip>
                <Tooltip placement="top" title='删除'>
                  <Popconfirm
                    title="你确定删除这条记录吗?"
                    // onConfirm={confirm}
                    // onCancel={cancel}
                    okText="确定"
                    cancelText="取消"
                  >
                    <img src={deleteIcon} className={styles.marginIcon}/>
                  </Popconfirm>

                </Tooltip>
              </Col>
            </Row>
          )
        }
      },
      {
        key: 'shareName',
        title: '分享人',
        dataIndex: 'shareName',
        width: '10%',
      },
      {
        key: 'state',
        title: '状态',
        dataIndex: 'state',
        width: '10%',
      },
      {
        key: 'fileSize',
        title: '大小',
        dataIndex: 'fileSize',
        width: '10%',
      },
      {
        key: 'shareDate',
        title: '分享时间',
        dataIndex: 'shareDate',
        width: '10%',
      },
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
    return(
      <div>
        <Row>
          <Col span={14}>
            <InputGroup>
              <Button className={styles.leftBtnSty}>保存</Button>
              <Button className={styles.middleBtnSty}>分享</Button>
              <Button className={styles.rightBtnSty}>删除</Button>
            </InputGroup>
          </Col>
          <Col span={10}>
            <Select defaultValue="全部" style={{width: '20%'}}>
              <Option value='全部'>全部</Option>
            </Select>
            <Search
              placeholder="搜索"
              onSearch={value => console.log(value)}
              style={{ width: '79%', marginLeft: '1%' }}
            />
          </Col>
        </Row>

        <Table
          rowKey='fileId'
          className={styles.tableHead}
          rowSelection={rowSelection}
          columns={columns}
          dataSource={data}
        />
      </div>
    )
  }
}
