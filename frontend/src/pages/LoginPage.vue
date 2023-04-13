<template>
  <div class="login-container">
    <b-card no-body class="login-form">
      <b-card-header>
        <h4><b>Log into your account:</b></h4>
        <p>Welcome to <b>EvenBetterHealth</b>. Log into your account or create an account if you don't have one.</p>
      </b-card-header>
      <b-card-body>
        <b-alert v-model="showDismissibleAlert" variant="danger" dismissible>
          Email or Password incorrect.
        </b-alert>
        <b-form @submit.prevent="login" >
          <b-form-group id="email-group"
                        label="Email address:"
                        label-for="email-input"
                        description="Enter your email address ">
            <b-form-input id="email-input"
                          v-model="email"
                          type="email"
                          required></b-form-input>
          </b-form-group>
          <b-form-group id="password-group"
                        label="Password:"
                        label-for="password-input"
                        description="Enter your password">
            <b-form-input id="password-input"
                          type="password"
                          v-model="password"
                          required></b-form-input>
          </b-form-group>
          <b-button type="submit" variant="success" class='login-form__login'>Log in</b-button>
        </b-form>
      </b-card-body>
      <template #footer>
        New User? <b-link to='/register'>Create an account.</b-link>
      </template>
    </b-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      showDismissibleAlert: false,
    }
  },
  name: 'LoginPage',
  methods: {
    async login() {
      await this.$store.dispatch('login', this.$data)
          .catch(exception => {
            console.log(exception.status, exception.statusText);
            this.showDismissibleAlert = true;
          });
    }
  }
}
</script>

<style scoped>
  .card-header h4 {
    margin-bottom: 0;
  }
  .login-form__login {
    width: 100%;
    border-radius: 13px;
  }
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: url('https://thumbs.dreamstime.com/b/portrait-happy-doctors-team-showing-thumbs-up-80704124.jpg');
    background-size: cover;
    background-position: center;
  }
  .login-form {
    width: 40%;
    margin: 0 auto;
  }
  .card-footer {
    font-size: smaller;
  }
</style>