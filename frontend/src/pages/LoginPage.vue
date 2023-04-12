<template>
  <div class="login-container">
    <b-card class="login-form">
      <b-card-header>
        <h4><b>Log into your account:</b></h4>
        <p>Welcome to <b>EvenBetterHealth</b>. Please Login to your account or create an account if you don't have one.</p>
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

          <div class="login-form__buttons">
            <b-button type="submit" variant="success" class='login-form__login'>Log in</b-button> 
            <b-button to='/register' variant="primary" class='login-form__signup'>Register an account</b-button>
          </div>
          
        </b-form>
      </b-card-body>
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
  .login-form__buttons b-button{
    display: inline-block;
    flex-direction: column;
    align-items: center;
  }
  .login-form__login {
    width: 100%;
    border-radius: 13px;
    margin-bottom: 2px
  }
  .login-form__signup {
    width: 100%;
    border-radius: 10px;
    margin-top: 2px
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
</style>