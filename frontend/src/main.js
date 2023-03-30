import Vue from 'vue';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import App from './App.vue';
import VueRouter from 'vue-router'
import Home from './pages/HomePage.vue'
import Login from './pages/LoginPage.vue'
import Register from './pages/RegisterPage.vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap/dist/js/bootstrap.min.js'
import BookAppointmentPage from "@/pages/BookAppointmentPage.vue";

Vue.use(VueRouter)
// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/book-appointment', component: BookAppointmentPage }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')