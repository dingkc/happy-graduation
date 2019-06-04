import { message } from 'antd';
import { routerRedux } from 'dva/router';
import {
  checkVerifyCode,
  getVerifyCodes,
  resetPassword
} from '@/services/user';

export default {
  namespace: 'forgetPassword',

  state: {
  },

  effects: {
    *submit({ payload, callback }, { call, put}) {
      const response = yield call(resetPassword, payload);
      if (response !== undefined) {
        callback(response);
        // if (response.status === '0') {
        //   message.success('恭喜，重置密码成功');
        //   yield put(routerRedux.push('/user/login'));
        // } else {
        //   message.error(response.errMessage);
        // }
      }
    },

    *getVerifyCodes({ payload, callback}, { call }) {
      const response = yield call(getVerifyCodes, payload);
      if (response !== undefined) {
        callback(response);
      }
    },
    *checkVerifyCode({ payload, callback}, { call }) {
      const response = yield call(checkVerifyCode, payload);
      if (response !== undefined) {
        callback(response);
      }
    },
    * checkUserNotExist({ payload, callback }, { call }){
      const response = yield call(checkUserNotExist, payload);
      if (response !== undefined) {
        callback(response);
      }
    },

  },

  reducers: {
  },
};

