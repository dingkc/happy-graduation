import { message } from 'antd';
import {
  getFriendsList
} from '@/services/shareApi';

export default {
  namespace: 'share',

  state: {
    friends: {
      friendsList: [],
    },
    addFriendsModal: {
      visible: false,
    },
  },

  effects: {
    *getFriends({ payload }, { call, put }) {
      const response = yield call(getFriendsList, payload);
      console.log('fdsf')
      if(response){
        if(response.status === '0'){
          yield put({
            type: 'saveFriends',
            payload: response,
          })
        }
        else {
          message.error(response.errMessage);
        }
      }
    },

    *showAddModal({ payload }, { put }) {
      yield put({
        type: 'showAddFriends',
        params: payload,
      })
    }

  },

  reducers: {
    saveFriends(state, action) {
      console.log('action=======',action.payload.data)
      return{
        ...state
      }
    },

    showAddFriends(state, action) {
      let { addFriendsModal } = state;
      addFriendsModal.visible = action.params.visible;
      console.log('paramdddd====',addFriendsModal)
      return {
        ...state,
        addFriendsModal,
      }
    }

  }
}
