import Vue from 'vue'
import Router from 'vue-router'
import Page1 from '../components/part1/Page1'
import Page2 from '../components/part1/Page2'
import Page3 from '../components/part1/Page3'
import Page4 from '../components/part1/Page4'
import Index from '../components/part1'
import login from '../views/login'
import home from '../views/home'
import add from '../views/add'
Vue.use(Router)

const router = new Router(
  {
    routes: [
      {
        path: '/',
        redirect: '/login'
      },
      {
        path: '/',
        name: '导航1',
        component: Index,
        children: [
          {
            path: '/Page1',
            name: '页面1',
            component: Page1
          },
          {
            path: '/Page2',
            name: '页面2',
            component: Page2
          }
        ]
      },
      {
        path: '/navigate',
        name: '导航2',
        component: Index,
        children: [
          {
            path: '/Page3',
            name: '页面3',
            component: Page3
          },
          {
            path: '/Page4',
            name: '页面4',
            component: Page4
          }
        ]
      },
      {
        path: '/login',
        component: login
      },
      {
        path: '/home',
        component: home
      },
      {
        path: '/add',
        component: add
      },
    ]
  }
)

//to：代表将要访问的路径 from：代表从哪个路径跳转而来 next：是一个函数，代表放行
// next函数有两种写法：next()，代表放行；next(‘路径’)，代表强制跳转
router.beforeEach((to, from, next) => {
  // 1.如果访问的是登录页面（无需权限），直接放行
  if (to.path === '/login') return next()
  // 2.如果访问的是有登录权限的页面，先要获取token
  const tokenStr = window.sessionStorage.getItem('token')
  // 2.1如果token为空，强制跳转到登录页面；否则，直接放行
  if (!tokenStr) return next('/login')
  next()
})

export default router
