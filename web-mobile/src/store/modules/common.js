const common = {
  state: {
    isLoading: false
  },
  getters: {
    getIsLoading: state => state.isLoading
  },
  mutations: {
    updateLoadingStatus (state, data) {
      state.isLoading = data.isLoading
    }
  }
}

export default common
