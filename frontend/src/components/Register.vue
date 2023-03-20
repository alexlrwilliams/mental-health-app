<template>
  <div class="register-container">
    <b-card class="register-form">
      <b-card-header>
        <h4><b>Register Form:</b></h4>
        <p>Welcome to <b>EvenBetterHealth</b> application ! Please register your account, if you are a new user</p>
      </b-card-header>
      <b-card-body>
        <b-form @submit.prevent="register" >
          <b-form-group id="fname-group"
                        label="First name:*"
                        label-for="fname-input">
            <b-form-input id="name-input"
                          v-model="fname"
                          type="text"
                          required></b-form-input>
          </b-form-group>

          <b-form-group id="lname-group"
                        label="Last name:*"
                        label-for="lname-input">
            <b-form-input id="name-input"
                          v-model="lname"
                          type="text"
                          required></b-form-input>
          </b-form-group>

          <b-form-group id="address-group"
                        label="Home address:*"
                        label-for="address-input">
            <b-form-textarea id="address-input"
                          v-model="address"
                          type="text"
                          required></b-form-textarea>
          </b-form-group>

          <b-form-group id="profession-group" label="Current profession:*">
            <b-form-radio-group v-model='profession' 
                                :options='options' 
                                value-field='item' 
                                text-field='name' 
                                plain></b-form-radio-group>
          </b-form-group><br>

          <b-form-group id="email-group"
                        label="Email address:*"
                        label-for="email-input">
            <b-form-input id="email-input"
                          v-model="email"
                          type="email"
                          required></b-form-input>
          </b-form-group>
          <b-form-group id="password-group"
                        label="Password:*"
                        label-for="password-input">
            <b-form-input id="password-input"
                          type="password"
                          v-model="password"
                          required></b-form-input>
          </b-form-group><br>
          <div class="button-container">
          <b-button type='submit' variant="success" class='signup-button'>Create an account</b-button>
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
      fname: '',
      lname: '',
      address: '',
      selected:'', 
      options: [
        {item: 'A', name: 'Patient'},
        {item: 'B', name: 'Doctor'},
        {item: 'C', name: 'Administrator'}
      ],
      email: '',
      password: ''
    }
  },
  methods: {
    register() {
      
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
  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 120vh;
    margin-top: -80px; /* adjust as needed */
    background-image: url('https://thumbs.dreamstime.com/b/portrait-happy-doctors-team-showing-thumbs-up-80704124.jpg');
    background-size: cover;
    background-position: center;
  }
  .register-form {
    width: 40%;
    margin: 0 auto;
  }
</style>
