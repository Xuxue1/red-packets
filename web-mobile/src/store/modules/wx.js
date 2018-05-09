const wx = {
  state: {
    appId: 'wx309fad204b2a744f',
    dappAddress: 'n1rgn8N7JnKrN4B8WnnWFRKqL2ZJChxzm3d',
    shareImg: '',
    password: '',
    address: '',
    redAddress:'',
    redId:'',
    hash:'',
    nickname:'',
    word:'',
    shareUrl:'',
    status:false,
    init:false,
  },
  getters: {
    getAppId: state => state.appId,
    getShareImg: state => state.shareImg,
    getDappAddress: status=>status.dappAddress,
    getPassword: status=>status.password,
    getAddress: status=>status.address,
    getRedAddress: status=>status.redAddress,
    getRedId: status=>status.redId,
    hash: status=>status.hash,
    getNickname: status=>status.nickname,
    getWord: status=>status.word,
    getShareUrl: status=>status.shareUrl,
    getStatus: status=>status.status,
    getInit:status=>status.init
  },
  mutations: {
    updatePassword (state, data) {
      state.password = data.password
    },
    updateStatus(state,data){
      state.status  = data.status;
      console.log("set getGetStatus"+state.status)
    },
    updateInit(state,data){
      state.init = data.init
    },
    updateAddress(state,data){
      state.address = data.address;
    },
    updateRedAddress(state,data){
      state.redAddress = data.redAddress
    },
    updateShareUrl(state,data){
      state.shareUrl = data.shareUrl
    },
    updateRedId(state,data){
      state.redId = data.redId
    },
    updateHash(state,data){
      this.hash = data.hash
    },
    updateNickname(state,data){
      state.nickname = data.nickname
    },
    updateWord(state,data){
      this.word = data.word
    }
  }
};

export default wx
