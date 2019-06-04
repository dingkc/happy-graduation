import React, { PureComponent } from 'react';
import { Modal, Form, Input } from 'antd';
import { connect } from 'dva';

const FormItem = Form.Item;

@connect(({ note }) => ({
  note,
}))
@Form.create()
export default class AddNoteModal extends PureComponent{
  render() {
    const { getFieldDecorator } = this.props.form;
    const { visible } = this.props;
    return (
      <div>
        <Modal
          visible={visible}
        >
          <Form>
            <FormItem label='记事本名称'>
              {getFieldDecorator('noteName', {
                rules: [{ required: true, message: '请输入记事本名称' }],
              })(
                <Input />
              )}
            </FormItem>
          </Form>
        </Modal>
      </div>
    );
  }
}
