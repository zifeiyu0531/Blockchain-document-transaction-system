import Vue from 'vue';
import VueRouter from 'vue-router'
import ElementUI from 'element-ui'
import store from '../components/store.js'
import index from '@/views/index'
import docMarket from '@/views/docMarket/docMarket'
import docSearch from '@/views/docMarket/docSearch'
import docDetail from '@/views/docMarket/docDetail'
import pointMarket from '@/views/pointMarket/pointMarket'
import userCenter from '@/views/userCenter/userCenter'
import login from '@/views/login/login'


Vue.use(VueRouter);

const routes = [{
		path: '',
		redirect: '/index'
	},
	{
		path: '/index',
		name: 'index',
		component: index
	},
	{
		path: '/login',
		name: 'login',
		component: login
	},
	{
		path: '/doc',
		name: 'doc',
		component: docMarket
	},
	{
		path: '/search',
		name: 'search',
		component: docSearch
	},
	{
		path: '/docDetail',
		name: 'docDetail',
		component: docDetail
	},
	{
		path: '/point',
		name: 'point',
		component: pointMarket
	},
	{
		path: '/user',
		name: 'user',
		component: userCenter,
		meta: {
			requireAuth: true
		}
	},
]

const router = new VueRouter({
	base: '/mannikin/',
	routes,
	mode: 'history'
});


router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) { 
	  // 判断是否存在登录
      if (store.state.isLogin) {
        next()
      } else {
        // 未登录,跳转到登陆页面，并且带上 将要去的地址，方便登陆后跳转。
        next({
          path: '/login',
        })
		ElementUI.Message({
			message: '请先登录',
			showClose: true,
			type: 'warning'
		})
      }
    } else {
      next()
    }
})

router.afterEach(route => {
  window.scroll(0, 0)
})

export default router
