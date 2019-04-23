export default {
  namespace: 'note',

  state: {
    visible: false,
  },

  effects: {
    *showModal({ payload }, { put }) {
      yield put({
        type: 'modalShow',
        payload: payload,
      })
    }
  },

  reducers: {
    modalShow(state, action) {
      let { visible } = state;
      visible = action.payload.visible;
      return {
        ...state,
        visible,
      }
    }
  },
}
