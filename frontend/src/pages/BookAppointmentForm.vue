<template>
  <div>
    <NavBar />
  <div class="booking-section1">
    <b-card class="booking-section-form1">
      <b-card-header>
        <h4><b>Book your appointment:</b></h4>
        <p>Welcome to <b>EvenBetterHealth</b>. Please fill in the forms below to book your appointment:</p>
        <p><b>Section 01</b>: On the textbox below, could you please tell us more about the issue ?</p>
      </b-card-header>
      <b-card-body>
        <b-form @submit.prevent="" >
          <b-form-group id="address-group"
                        label="Description of symptoms:*"
                        label-for="description-input">
            <b-form-textarea id="description-input"
                          class="description-textarea"
                          v-model="description"
                          type="text"
                          required></b-form-textarea>
          </b-form-group>

          <div class="booking-section1-btn">
            <b-button variant="primary" class='booking-section1-btn-proceed'>Proceed to calendar</b-button>
          </div>
        </b-form>
      </b-card-body>
    </b-card>
  </div>
  </div>
</template>

<script>
import NavBar from '../components/NavBar' 
export default {
  data() {
    return {
      description: '',
    }
  },
  name: 'BookAppointmentForm',
  components: {
    NavBar 
  }
}
</script>
<style scoped>
  .booking-section1 {
    display: flex;
    justify-content: center;
    align-items: center;

  }
  .booking-section-form1 {
    width: 95%;
    margin: 0 auto;
  }

  .description-textarea {
    width: 100%;
    height: 200px;
    font-size: 16px;
  }
  .booking-section1-btn{
    display: flex;
    flex-direction: column;
    align-items: center;
  }
    .booking-section1-btn-proceed {
    width:70%;
    border-radius: 10px;
    margin-top: 20px
  }
</style>