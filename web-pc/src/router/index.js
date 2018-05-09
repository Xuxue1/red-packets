import Vue from 'vue'
import Router from 'vue-router'

const homePage = r => require.ensure([], () => r(require('@/views/homePage')), 'homePage')
const leaveMsg = r => require.ensure([], () => r(require('@/views/leaveMsg')), 'leaveMsg')
const recorder = r => require.ensure([], () => r(require('@/views/recorder')), 'recorder')

Vue.use(Router)

const router =  new Router({
    routes: [{
        path: '/',
        name: 'homePage',
        component: homePage,
        meta:{
            title:'NAS手气红包大放送'
        }
    }, {
        path: '/leaveMsg',
        name: 'leaveMsg',
        component: leaveMsg,
        meta:{
            title:'联系我们'
        }
    }, {
        path: '/recorder',
        name: 'recorder',
        component: recorder,
        meta:{
            title:'红包发送记录'
        }
    }]
})


router.beforeEach(function(to, from, next) {
    if (to.meta.title) {
        document.title = to.meta.title;
    }
    next();
})

export default router 
