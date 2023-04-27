import Vue from 'vue';
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue'
import App from './App.vue';

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap/dist/js/bootstrap.min.js'
import router from "@/js/router";
import store from "@/js/store";

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')