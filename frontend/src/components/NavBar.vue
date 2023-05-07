<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <div class="container">
        <b-navbar-brand to="/">EvenBetterHealth</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav class="ml-auto">
            <b-nav-item to="/"><b-icon-house-door-fill/>Home</b-nav-item>
            <b-nav-item to="about-us"><b-icon-info-circle-fill/> About us</b-nav-item>
            <b-nav-item to="contact-us"><b-icon-telephone-fill/> Contact us</b-nav-item>
            <b-nav-item-dropdown right no-caret>
              <template #button-content>
                <b-avatar size="sm" v-if="$store.getters.user.profilePicUrl" :src="$store.getters.user.profilePicUrl"/>
                <b-icon-person-circle v-else/>
              </template>
              <b-dropdown-text style="width: 240px;">
                {{ userName }}
              </b-dropdown-text>
              <b-dropdown-item to="profile">Your Profile</b-dropdown-item>
              <b-dropdown-divider></b-dropdown-divider>
              <b-dropdown-item @click="logout">Log out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </div>
    </b-navbar>
  </div>
</template>

<script>
import {mapActions} from "vuex";

export default {
  name: 'NavBar',
  computed: {
    userName() {
      const user = this.$store.getters.user;
      return user ? `${user.firstName} ${user.lastName}` : "";
    }
  },
  methods: {
    ...mapActions(['logout']),
  }
}
</script>
