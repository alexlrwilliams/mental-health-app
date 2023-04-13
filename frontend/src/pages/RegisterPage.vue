<template>
  <div class="register-container">
    <b-card no-body class="register-form mb-2">
      <b-card-header>
        <h4><b>Register your account:</b></h4>
        <p>Welcome to <b>EvenBetterHealth</b>. Register a new account by filling in your details below.</p>
      </b-card-header>
      <b-card-body>
        <b-alert v-model="conflictAlert" variant="danger" dismissible>
          Email already exists.
        </b-alert>
        <b-form @submit.prevent="register" >
          <b-form-group id="fname-group"
                        label="First name:*"
                        label-for="fname-input">
            <b-form-input id="first-name-input"
                          v-model="form.firstName"
                          type="text"
                          required></b-form-input>
          </b-form-group>

          <b-form-group id="lname-group"
                        label="Last name:*"
                        label-for="lname-input">
            <b-form-input id="last-name-input"
                          v-model="form.lastName"
                          type="text"
                          required></b-form-input>
          </b-form-group>

          <b-form-group id="address-group"
                        label="Home address:*"
                        label-for="address-input">
            <b-form-textarea id="address-input"
                          v-model="form.address"
                          type="text"
                          required></b-form-textarea>
          </b-form-group>

          <b-form-group id="profession-group" label="Current profession:*">
            <b-form-radio-group v-model='form.role'
                                :options='options' 
                                value-field='item' 
                                text-field='name' 
                                plain></b-form-radio-group>
          </b-form-group><br>

          <b-form-group id="email-group"
                        label="Email address:*"
                        label-for="email-input">
            <b-form-input id="email-input"
                          v-model="form.email"
                          type="email"
                          required></b-form-input>
          </b-form-group>
          <b-form-group id="password-group"
                        label="Password:*"
                        label-for="password-input">
            <b-form-input id="password-input"
                          type="password"
                          v-model="form.password"
                          required></b-form-input>
          </b-form-group><br>

          <b-button type='submit' variant="success" class='register-form__register'>Register</b-button>
        </b-form>
      </b-card-body>
      <template #footer>
        Already a member? <b-link to='/login'>Login.</b-link>
      </template>
    </b-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      conflictAlert: false,
      form: {
        email: '',
        password: '',
        role: '',
        firstName: '',
        lastName: '',
        address: '',
      },
      options: [
        {item: 'PATIENT', name: 'Patient'},
        {item: 'DOCTOR', name: 'Doctor'},
        {item: 'ADMINISTRATOR', name: 'Administrator'}
      ],
    }
  },
  name: 'RegisterPage',
  methods: {
    async register() {
      await this.$store.dispatch('register', this.form)
          .catch(exception => {
            console.log(exception.status, exception.statusText);
            if (exception.status === 409) {
              this.conflictAlert = true;
            }
          });
    }
  }
}
</script>

<style scoped>
  .card-header h4 {
    margin-bottom: 0;
  }
    .register-form__register {
    width: 100%;
    border-radius: 13px;
    margin-bottom: 2px
  }
  .register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: url('https://thumbs.dreamstime.com/b/portrait-happy-doctors-team-showing-thumbs-up-80704124.jpg');
    background-size: cover;
    background-position: center;
  }
  .register-form {
    width: 40%;
    font-size: smaller;
  }
</style>