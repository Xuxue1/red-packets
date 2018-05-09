const user = {
  state: {
    info: null,
    macdList: null
  },
  getters: {
    getUserInfo: state => state.info,
    getMacdList: state => state.macdList
  },
  mutations: {
    updateUserInfo (state, data) {
      state.info = data.info
    },
    updateMacdList (state, data) {
      state.macdList = data.macdList
    }
  }
}

export default user
