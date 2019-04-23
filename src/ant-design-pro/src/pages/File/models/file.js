import { message } from 'antd';
import { routerRedux } from 'dva/router';
import {
  getFileList,
  deleteFile,
  downloadFile,
  moveFile,
  uploadFile
}
  from '@/services/fileApi';

export default {
  namespace: 'file',

  state: {
    status: undefined,
    fileList: {
      fileListPagination: {},
      fileListList: []
    },
    moveFiles: {
      moveFilesPagination: {},
      moveFilesList: [],
    },
    breads: [],
  },

  effects: {
    *getFileList({ payload }, { call, put }) {
      const response = yield call(getFileList, payload);
      if(response){
        if(response.status === '0'){
          yield put({
            type:'saveFileList',
            payload: response,
            params: payload,
          })
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *getMoveFileList({ payload }, { call, put }) {
      const response = yield call(getFileList, payload);
      if(response){
        if(response.status === '0'){
          yield put({
            type: 'saveMoveFiles',
            payload: response,
            params: payload,
          })
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *deleteFile({ payload }, { call, put }) {
      const responseJSON = yield call(deleteFile, payload);
      // console.log(response)
      const response = JSON.parse(responseJSON);
      if(response){
        if(response.status === '0'){
          message.success('删除成功');
          // yield put({
          //   type: 'getFileList',
          //   payload: {
          //     pageNumber: 1,
          //     pageSize: 10,
          //   }
          // })
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *changeBreads({ payload }, { put }) {
      yield put({
        type: 'saveBreads',
        payload: payload,
      })
    },

    *cleanParams({ payload }, { put }) {
      yield put({
        type: 'cleanParam',
        payload: payload
      })
    },

    *downloadFile({ payload }, { call, put }) {
      const response = yield call(downloadFile, payload);
      if(response){
        if(response.status === '0'){
          message.success('下载成功')
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *uploadFile({ payload }, { call, put }) {
      const response = yield call(uploadFile, payload);
      if(response){
        if(response.status === '0'){
          message.success('上传成功')
        }
        else {
          message.error(response.errMessage)
        }
      }
    },

    *moveFile({ payload }, { call, put }) {
      const response = yield call(moveFile, payload);
      if(response){
        if(response.status === '0'){
          message.success('移动成功')
        }
        else {
          message.error(response.errMessage);
        }
      }
    }

  },

  reducers: {
    saveFileList(state, action) {
      let { fileList } = state;
      fileList.fileListPagination.current = action.params.pageNumber;
      fileList.fileListList = action.payload.data.rows;
      fileList.fileListPagination.total = action.payload.data.total;
      return{
        ...state,
        fileList,
      }
    },

    saveMoveFiles(state, action) {
      let { moveFiles } = state;
      moveFiles.moveFilesPagination.current = action.params.pageNumber;
      moveFiles.moveFilesList = action.payload.data.rows;
      // moveFiles.moveFilesPagination.total = action.payload.data.total;
      return{
        ...state,
        moveFiles,
      }
    },

    saveBreads(state, action) {
      let { breads } = state;
      breads.push(action.payload)
      return {
        ...state,
        breads,
      }
    },

    cleanParam(state, action) {
      return {
        ...state,
        breads: action.payload,
      }
    }

  },
};
