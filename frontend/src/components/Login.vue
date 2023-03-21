<template>
  <div class="login-container">
    <b-card class="login-form">
      <b-card-header>
        <h4><b>Login Form:</b></h4>
        <p>Welcome to <b>EvenBetterHealth</b>. Please Login to your account or create an account if you don't have one.</p>
      </b-card-header>
      <b-card-body>
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
          <div class="button-container">
          <b-button type="submit" variant="primary" class='login-button'>Login</b-button> <br>

          <b-button href='/register' variant="success" class='signup-button'>Create an account</b-button>
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
      password: ''
    }
  },
  name: 'LoginPage',
  methods: {
    login() {
      
    }
  }
}
</script>

<style scoped>
  .card-header h4 {
    margin-bottom: 0;
  }
  .btn-primary {
    background-color: light-blue;
    border-color: blue;
  }
  .button-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .login-button {
    width: 100%;
    border-radius: 13px;
  }
  .signup-button {
    width: 100%;
    border-radius: 10px;
  }
  .login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 120vh;
    margin-top: -100px; /* adjust as needed */
    background-image: url('https://thumbs.dreamstime.com/b/portrait-happy-doctors-team-showing-thumbs-up-80704124.jpg');
    background-size: cover;
    background-position: center;
  }
  .login-form {
    width: 40%;
    margin: 0 auto;
  }
</style>