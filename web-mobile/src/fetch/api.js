import axios from 'axios'
import Vue from 'vue'
import qs from 'qs'
// import store from './../vuex/store'
Vue.prototype.$http = axios

// axios 配置
axios.defaults.timeout = 5000
// axios.defaults.headers.get['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
axios.defaults.headers = {
  'Content-Type': 'application/x-www-form-urlencoded;application/json;charset=UTF-8',
  'X-Requested-With': 'XMLHttpRequest',
  'Accept': 'application/json'
}
// axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*'
axios.defaults.withCredentials = true
// axios.defaults.baseURL = 'http://api.coin2rich.com:8016/'

// POST传参序列化
axios.interceptors.request.use((config) => {
  if (config.method === 'post') {
    config.data = qs.stringify(config.data)
  }
  return config
}, (error) => {
  return Promise.reject(error)
})

// 返回状态判断
axios.interceptors.response.use((response) => {
  if (response.status) {
    // return Promise.reject(response)
  }
  return response
}, (error) => {
  if (error && error.response) {
    // switch (error.response.status) {
    //   case 400:
    //     // alert('请求错误')
    //     Vue.prototype.$Message.error('请求错误')
    //     break
    //   case 401:
    //     // alert('未授权，请登录')
    //     window.location.href = '#/401';
    //     Vue.prototype.$Message.error('未授权，请登录')
    //     break
    //   case 403:
    //     window.location.href = '#/401';
    //     Vue.prototype.$Message.error('未授权，请登录')
    //     // alert('拒绝访问')
    //     break
    //   case 404:
    //     window.location.href = '#/404';
    //     // alert('请求地址出错')
    //     break
    //   case 408:
    //     Vue.prototype.$Message.error('请求超时')
    //     // alert('请求超时')
    //     break
    //   case 500:
    //     Vue.prototype.$Message.error('服务器内部错误')
    //     // window.location.href = '#/500';
    //     // alert('服务器内部错误')
    //     break
    //   case 501:
    //     Vue.prototype.$Message.error('服务未实现')
    //     // alert('服务未实现')
    //     break
    //   case 502:
    //     Vue.prototype.$Message.error('网关错误')
    //     // alert('网关错误')
    //     break
    //   case 503:
    //     Vue.prototype.$Message.error('服务不可用')
    //     // alert('服务不可用')
    //     break
    //   case 504:
    //     Vue.prototype.$Message.error('网关超时')
    //     // alert('网关超时')
    //     break
    //   case 505:
    //     Vue.prototype.$Message.error('HTTP版本不受支持')
    //     // alert('HTTP版本不受支持')
    //     break
    //   default:
    // }
  }
  return Promise.reject(error)
})
export function fetch (method, url, params, timeout) {
  return new Promise(function (resolve, reject) {
    axios[method](url, params, timeout)
      .then(response => {
        // // 获取访问系统信息的status,若为1,则未登录
        // if (response.data.status) {
        //     if (response.data.info.functionEntity) {
        //         const restUrl = response.data.info.functionEntity.data.restUrl
        //         const serviceUrl = response.data.info.functionEntity.data.service
        //         // const restUrlApi = restUrl.slice(restUrl.indexOf('security-cas') - 1)
        //         const restUrlApi = restUrl
        //         console.log('restUrlApi:' + restUrlApi)
        //         const serviceUrlApi = serviceUrl.slice(serviceUrl.indexOf('video-analysis-platform') - 1)
        //         console.log('restUrlApi:' + restUrlApi)
        //         console.log('serviceUrl:' + serviceUrlApi)
        //         store.commit('restUrlMutations', restUrl)
        //         store.commit('serviceUrlMutations', serviceUrl)
        //         // 获取访问系统信息的validate,若为false则请求restUrl
        //         if (response.data.validate === false) {
        //             axios.post(restUrlApi)
        //                 .then(response => {
        //                     if (!response.data.status) {
        //                         let TGTdata = {
        //                             TGT: response.data.data
        //                         }
        //                         axios.post(serviceUrlApi, TGTdata)
        //                             .then(response => {
        //                                 router.go(0)
        //                                 resolve(response.data)
        //                             })
        //                             .catch((response) => {
        //                                 reject(response)
        //                             })
        //                     } else {
        //                         router.push({path: '/Login'})
        //                     }
        //                     resolve(response.data)
        //                 })
        //                 .catch((response) => {
        //                     reject(response)
        //                 })
        //         }
        //     }
        // }
        resolve(response.data)
      })
      .catch((error) => {
        reject(error)
      })
  })
}
export default {
  // method 请求方法 get post put delete
  // url 请求地址
  // params 请求参数
  // timeout 超时时间
  api (method, url, params, timeout) {
    return fetch(method, url, params, timeout)
  }
}
