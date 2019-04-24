import { message } from 'antd';
import { routerRedux } from 'dva/router';
import {
  getRecycleList,
  deleteRecycle,
  reductionRecycle,
  cleanAllRecycle
}
  from '@/services/fileApi';

export default {
  namespace: 'recycle',

  state: {
    status: undefined,
    recycleFiles: {
      recycleFilesPagination: {},
      recycleFilesList: []
    },
  },

  effects: {
    *getRecycleList({ payload }, { call, put }) {
      const response = yield call(getRecycleList, payload);
      if(response){
        if(response.status === '0'){
          yield put({
            type: 'saveRecycleFile',
            payload: response,
            params: payload,
          })
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *deleteRecycle({ payload }, { call, put }) {
      const responseJSON = yield call(deleteRecycle, payload);
      const response = JSON.parse(responseJSON);
      if(response){
        if(response.status === '0'){
          message.success('删除成功');
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *reductionRecycle({ payload }, { call, put }) {
      const response  = yield call(reductionRecycle, payload);
      if(response){
        if(response.status === '0'){
          message.success('还原成功')
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *cleanAllRecycle({ _ },{ call,put }) {
      const responseJSON = yield call(cleanAllRecycle);
      const response = JSON.parse(responseJSON);
      if(response){
        if(response.status === '0'){

        }
        else {
          message.error(response.errMessage);
        }
      }
    }

  },

  reducers: {
    saveRecycleFile(state, action) {
      let { recycleFiles } = state;
      recycleFiles.recycleFilesPagination.current = action.params.pageNumber;
      recycleFiles.recycleFilesList = action.payload.data.rows;
      recycleFiles.recycleFilesPagination.total = action.payload.data.total;
      return {
        ...state,
        recycleFiles
      }
    }
  },

}
