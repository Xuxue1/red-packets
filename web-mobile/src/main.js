// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueRouter from 'vue-router'
import {router} from './router'
import ElementUI from 'element-ui'
Vue.use(ElementUI);
import store from './store';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(VueRouter);

Vue.config.productionTip = false;

new Vue({
  router: router,
  store: store,
  render: h => h(App)
}).$mount('#app');
