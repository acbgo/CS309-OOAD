import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '../components/HelloWorld.vue'
import Manage from '../components/Manage.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Manage',
      redirect: '/room',
      component: Manage,
      children: [
        {path: 'room', name: 'Room', component: () => import('../components/room')},
        {path: 'orders', name: 'Orders', component: () => import('../components/orders')},
        {path: 'analyse', name: 'analyse', component: () => import('../components/Analyse')},
        {path: 'roomType', name: 'roomType', component: () => import('../components/roomType')}
      ]
    },
    {
      path: '/roomShow',
      name: 'roomShow',
      component: () => import('../components/roomShow')
    }
  ]
})
