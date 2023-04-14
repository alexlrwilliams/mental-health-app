<template>
  <div id="app">
    <NavBar v-if="navBarVisible"/>
    <router-view></router-view>
  </div>
</template>

<script>

import NavBar from "@/components/NavBar.vue";

export default {
  name: 'App',
  components: {
    NavBar
  },
  computed: {
    navBarVisible() {
      return this.$route.path!=='/login' && this.$route.path!=='/register';
    }
  }
}
</script>