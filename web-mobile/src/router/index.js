import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store/index'
import { getQueryString } from '../libs/urlResolve'
import api from '../fetch/api'
import qs from 'qs'


const getWay = r => require.ensure([], () => r(require('@/views/DAPP/getWay')), 'getWay')
const index = r => require.ensure([], () => r(require('@/views/DAPP/index')), 'index')
const NASAddress = r => require.ensure([], () => r(require('@/views/DAPP/NASAddress')), 'NASAddress')
const password = r => require.ensure([], () => r(require('@/views/DAPP/password')), 'password')
const helpRemWord = r => require.ensure([], () => r(require('@/views/DAPP/helpRemWord')), 'helpRemWord')
const sendNAS = r => require.ensure([], () => r(require('@/views/DAPP/sendNAS')), 'sendNAS')
const openPacket = r => require.ensure([], () => r(require('@/views/DAPP/openPacket')), 'openPacket')

Vue.use(VueRouter)
    // 路由配置
const RouterConfig = {
    mode: 'history',
    routes: [{
        path: '/m/',
        name: 'index',
        component: openPacket,
        meta: {
            title: '领取NAS红包'
        }
    }, {
        path: '/m/getWay',
        name: 'getWay',
        component: getWay,
        meta: {
            title: '选择地址输入方式'
        }
    }, {
        path: '/m/password',
        name: 'password',
        component: password,
        meta: {
            title: '输入密码'
        }
    }, {
        path: '/m/NASAddress',
        name: 'NASAddress',
        component: NASAddress,
        meta: {
            title: '输入NAS地址'
        }
    }, {
        path: '/m/helpRemWord',
        name: 'helpRemWord',
        component: helpRemWord,
        meta: {
            title: '一键生成NAS地址'
        }
    }, {
        path: '/m/sendNAS',
        name: 'sendNAS',
        component: sendNAS,
        meta: {
            title: '我也要发红包'
        }
    }, {
        path: '/m/openPacket',
        name: 'openPacket',
        component: openPacket,
        meta: {
            title: '领取红包'
        }
    }]
};
export const router = new VueRouter(RouterConfig);

router.beforeEach(function(to, from, next) {
    if (process.env.NODE_ENV === 'development') {
        let userInfo = {
            country: "中国",
            province: "浙江",
            city: "杭州",
            openid: "ouXO6wm-EhgDMjRabY3A8qJUyVe8",
            sex: 1,
            nickname: "$doopy",
            headimgurl: "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIBIaVXAjmGbap8dsLxaiaJLODJDuFQoTfqdWZCicQuRGkd6WeYrxJRphbbWWiatibKeGWVf9LMkvlkcw/132",
            language: "zh_CN",
            privilege: [],
            level: 1,
            end_time: '2018-5-13 09:32:14'
        };
        console.log('development')
        store.commit('updateUserInfo', { info: userInfo })
    }
    let wxUserInfo = store.state.user.info;
    let code = getQueryString('code')
    if (!wxUserInfo) {
        if (code) {
            getWxUserInfo(code, next);
            store.commit('updateLoadingStatus', { isLoading: true })
        } else {
            // next()
            // 没有微信用户信息，没有授权-->> 需要授权，跳转授权页面
            let appId = store.state.wx.appId;
            let redirectUri = encodeURIComponent(window.location.href)
            let responseType = 'code';
            let scope = 'snsapi_userinfo';
            let state = 'qwertyuiop';
            window.location.href = `https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId}&redirect_uri=${redirectUri}&response_type=${responseType}&scope=${scope}&state=${state}#wechat_redirect`
        }
    } else {
        store.commit('updateLoadingStatus', { isLoading: true })
        if (to.meta.title) {
            document.title = to.meta.title
        }
        next()
    }
});

function getWxUserInfo(code, next) {
    api.api('get', '/wx/rest/open_id?code=' + code)
        .then(res => {
            if (res.success) {
                // console.log('res.data', res.data)
                try {
                    // 保证写入的wxUserInfo是正确的
                    if (res.data.openid) {
                        store.commit('updateUserInfo', { info: res.data })
                        console.log('getWxUserInfo', store.state.user.info)
                            // localStorage.setItem('wxUserInfo', JSON.stringify(res.data));// 写缓存--微信用户信息
                        next()
                    }
                } catch (e) {
                    // TODO: handle exception
                    console.log(e)
                }
            }
        }, (err) => {
            console.log(err)
        })
}
