import Vue from 'vue'
import Vuex from 'vuex'
import common from './modules/common';
import user from './modules/user';
import wx from './modules/wx';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    //
  },
  mutations: {
    //
  },
  actions: {

  },
  modules: {
    common,
    user,
    wx
  }
})

export default store
