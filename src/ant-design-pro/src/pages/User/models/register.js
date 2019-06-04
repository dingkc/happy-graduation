// import { fakeRegister } from '@/services/api';
import { setAuthority } from '@/utils/authority';
import { reloadAuthorized } from '@/utils/Authorized';
import { message } from 'antd';
import { routerRedux } from 'dva/router';
import {
  getVerifyCodes,
  fakeRegister
}
  from '@/services/user';

export default {
  namespace: 'register',

  state: {
    status: undefined,
  },

  effects: {
    *submit({ payload }, { call, put }) {
      const response = yield call(fakeRegister, payload);
      if (response !== undefined) {
        if (response.status === '0') {
          message.success('恭喜，注册成功');
          setTimeout(yield put(routerRedux.push('/user/login')) ,3000);
        } else {
          message.error(response.errMessage);
        }
      }
    },

    *getVerifyCodes({ payload, callback}, { call }) {
      const response = yield call(getVerifyCodes, payload);
      if (response) {
        if(response.status === '0'){
          callback(response);
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

  },

  reducers: {
    registerHandle(state, { payload }) {
      setAuthority('user');
      reloadAuthorized();
      return {
        ...state,
        status: payload.status,
      };
    },
  },
};
