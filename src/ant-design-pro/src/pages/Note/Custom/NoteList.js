import React, { PureComponent } from 'react';
import { Breadcrumb, Button, Row, Col, Checkbox, Card } from 'antd';
import styles from './NoteList.less';
import AllNoteIcon from '@/assets/全部记事本.svg';
import deleteNoteIcon from '@/assets/删除记事本.svg';
import createNoteIcon from '@/assets/新建记事.svg';
import createIcon from '@/assets/新建记事本.svg';
import greyIcon from '@/assets/普通@2x.png';
import yellowIcon from '@/assets/一般@2x.png';
import redIcon from '@/assets/重要@2x.png';
import { connect } from 'dva';
import AddNoteModal from './AddModal/AddNoteModal';

@connect(({ note }) => ({
  note,
}))
export default class NoteList extends PureComponent{

  showModal = () => {
    const { dispatch } = this.props;
    const params = {
      visible: true,
    }
    dispatch({
      type: 'note/showModal',
      payload: params,
    })
  }

  render() {
    return (
      <div className={styles.main}>
        <Breadcrumb>
          <Breadcrumb.Item>记事本</Breadcrumb.Item>
          <Breadcrumb.Item><a>学校记事本</a></Breadcrumb.Item>
        </Breadcrumb>
        <Row>
          <Col span={15}>
            <Row><span className={styles.titleSty}>学校记事本</span></Row>
            <Row><span className={styles.recordSty}>丁可成 创建于 2019-3-26</span></Row>
          </Col>
          <Col span={9}>
            <Button type='primary'><img src={createIcon} className={styles.iconSty} onClick={this.showModal}/> 新建记事本</Button>
            <Button type='primary' style={{marginLeft: '8px'}}><img src={createNoteIcon} className={styles.iconSty}/> 新建记事</Button>
            <Button style={{marginLeft: '8px'}}><img src={deleteNoteIcon} className={styles.iconSty}/> 删除记事本</Button>
          </Col>
        </Row>
        <hr className={styles.hrSty}/>
        <div>
          <Checkbox>全选</Checkbox>
          <Button style={{marginLeft: '10px', display: 'bolck'}}>删除</Button>
        </div>
        <Row gutter={16} style={{marginTop: '5px'}}>
          <Col span={6}>
            <Card className={styles.cardSty}>
              <Row>
                <Col span={6}>
                  <img src={redIcon} /> <span style={{fontSize: '12px', marginLeft: '3px'}}>重要</span>
                </Col>
                <Col span={18}>
                  <span className={styles.cardTitleSty}>丁可成 创建于 2019-3-26</span>
                </Col>
              </Row>
              <span className={styles.twoFontSty}>记事</span>
              <span className={styles.commonFontSty}>记事记事记事记事记事记事记事记事记事记事记事记事</span>
            </Card>
          </Col>
          <Col span={6}>
            <Card className={styles.cardSty}>
              <Row>
                <Col span={6}>
                  <img src={yellowIcon} /> <span style={{fontSize: '12px', marginLeft: '3px'}}>重要</span>
                </Col>
                <Col span={18}>
                  <span className={styles.cardTitleSty}>丁可成 创建于 2019-3-27</span>
                </Col>
              </Row>
              <span style={{fontWeight: '800', fontSize: '16px'}}>记事</span>
              <span>记事记事记事记事记事记事记事记事记事记事记事记事</span>
            </Card>
          </Col>
          <Col span={6}>
            <Card className={styles.cardSty}>
              <Row>
                <Col span={6}>
                  <img src={greyIcon} /> <span style={{fontSize: '12px', marginLeft: '3px'}}>重要</span>
                </Col>
                <Col span={18}>
                  <span className={styles.cardTitleSty}>丁可成 创建于 2019-3-28</span>
                </Col>
              </Row>
              <span style={{fontWeight: '800', fontSize: '16px'}}>记事</span>
              <span>记事记事记事记事记事记事记事记事记事记事记事记事</span>
            </Card>
          </Col>
          <Col span={6}>
            <Card className={styles.cardSty}>
              <Row>
                <Col span={6}>
                  <img src={greyIcon} /> <span style={{fontSize: '12px', marginLeft: '3px'}}>重要</span>
                </Col>
                <Col span={18}>
                  <span className={styles.cardTitleSty}>丁可成 创建于 2019-4-7</span>
                </Col>
              </Row>
              <span style={{fontWeight: '800', fontSize: '16px'}}>记事</span>
              <span>记事记事记事记事记事记事记事记事记事记事记事记事</span>
            </Card>
          </Col>
        </Row>
        <AddNoteModal/>
      </div>
    );
  }
}
