import Vue from 'vue'
import Router from 'vue-router'
import MainPage from '../components/userPage/MainPage.vue'
import ContactUs from '../components/userPage/ContactUs.vue'
import Chat from '../components/userPage/Chat.vue'
import Reservation from '../components/userPage/Reservation.vue'
import Manager from '../components/manager/Manage.vue'
import Review from '../components/userPage/Review.vue'
import RoomShow from '../components/userPage/RoomShow.vue'
import BigMap from '../components/userPage/BigMap.vue'
import Register from '../components/userPage/Register.vue'
import Login from '../components/userPage/Login.vue'
import Floor from '../components/userPage/Floor.vue'
import Book from '../components/userPage/Book.vue'
import HotelReview from '../components/userPage/HotelReview.vue'

Vue.use(Router)

const router = new Router(
  {
    mode: 'history',
    routes: [
      {
        path: '/',
        name: 'MainPage',
        component: MainPage,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/reservation',
        name: 'Reservation',
        component: Reservation,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/roomShow',
        name: 'roomShow',
        component: RoomShow,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/bigMap',
        name: 'BigMap',
        component: BigMap,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/review',
        name: 'review',
        component: Review,
        meta: {
          keepAlive: false
        }
      },
      {
        path: '/contract',
        name: 'ContactUs',
        component: ContactUs,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/chat',
        name: 'Chat',
        component: Chat,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/login',
        name: 'Login',
        component: Login,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/register',
        name: 'Register',
        component: Register,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/floor',
        name: 'Floor',
        component: Floor,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/book',
        name: 'Book',
        component: Book,
        meta: {
          keepAlive: true
        }
      },
      {
        path: '/hotelReview',
        name: 'HotelReview',
        component: HotelReview,
        meta: {
          keepAlive: false
        }
      },
      {
        path: '/manager',
        name: 'Manager',
        redirect: '/manager/room',
        component: Manager,
        children: [
          {path: '/manager/room', name: 'ManagerRoom', component: () => import('../components/manager/room')},
          {path: '/manager/orders', name: 'Orders', component: () => import('../components/manager/orders')},
          {path: '/manager/analyse', name: 'analyse', component: () => import('../components/analysis/Analysis.vue')},
          {path: '/manager/roomType', name: 'roomType', component: () => import('../components/manager/roomType')}
        ]
      },
      {
        path: '/personal',
        name: 'Personal',
        // redirect: '/info',
        component: () => import('../components/userPage/userDetail/Personal'),
        children: [
          {path: 'info', name: 'info', component: () => import('../components/userPage/userDetail/info')},
          {path: 'individualOrders', name: 'individualOrders', component: () => import('../components/userPage/userDetail/individualOrders')},
          {path: 'changePwd', name: 'changePwd', component: () => import('../components/userPage/userDetail/changePwd')},
          {path: 'mall', name: 'mall', component: () => import('../components/userPage/userDetail/mall')},
          {path: 'favor', name: 'favor', component: () => import('../components/userPage/userDetail/favor')}
        ]
      }
    ]
  }
)

export default router
