<template>
  <div>
    <NavBar />
  <div class="booking-section">
    <b-card class="booking-section-form">
      <b-card-header>
        <h4><b>Book your appointment:</b></h4>
        <p>Welcome to <b>EvenBetterHealth</b>. Please fill in the forms below to book your appointment:</p>
        <h5><b>Section 01:</b></h5> 
        <p>On the textbox below, could you please tell us more about the issue ?</p>
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

          <div class="booking-section-btn">
            <b-button variant="primary" class='booking-section-btn-proceed' v-model="type">Proceed to calendar</b-button>
          </div>
        </b-form>
      </b-card-body>
    </b-card>
  </div>

    <div class="booking-section">
    <b-card class="booking-section-form">
      <b-card-header>
        <h5><b>Section 02:</b></h5> 
        <p>Please chose your availability using the calendar below: </p>
      </b-card-header>
      <b-card-body>

        <b-form @submit.prevent="" >
          <b-calendar block locale="en-US" v-model="selectedDate" @input="saveSelectedDate"></b-calendar>
        </b-form>

        <div class="booking-section-btn">
            <b-button variant="primary" class='booking-section-btn-proceed'>Proceed to meeting types</b-button>
        </div>
      </b-card-body>
    </b-card>
  </div>

    <div class="booking-section">
    <b-card class="booking-section-form">
      <b-card-header>
        <h5><b>Section 03:</b></h5> 
        <p>How do you wish to contact your doctor ? Please pick one of the following options:</p>
      </b-card-header>
      <b-card-body>

        <b-form @submit.prevent="" >
            <b-button type="button" class="btn btn-square-md" id='call' variant="light"><h3>Chat <b-icon-chat-dots-fill></b-icon-chat-dots-fill></h3></b-button>
            <b-button type="button" class="btn btn-square-md" id='video' variant="light"><h3>Video call <b-icon-camera-video-fill></b-icon-camera-video-fill></h3></b-button>
        </b-form>
        
        <div class="booking-section-btn">
            <b-button variant="primary" class='booking-section-btn-proceed'>Proceed to doctors list</b-button>
        </div>
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
      selectedDate: '',
      savedDate: '',
      type: ''
    }
  },
  name: 'BookAppointmentForm',
  components: {
    NavBar
  },
  methods: {
    saveSelectedDate() {
      this.savedDate = this.selectedDate;
    }
  }
}

</script>
<style scoped>
  .booking-section {
    display: flex;
    justify-content: center;
    align-items: center;

  }
  .booking-section-form {
    width: 90%;
    margin: 0 auto;
  }

  .description-textarea {
    width: 100%;
    height: 200px;
    font-size: 16px;
  }
  .booking-section-btn{
    display: flex;
    flex-direction: column;
    align-items: center;
  }
    .booking-section-btn-proceed {
    width:70%;
    border-radius: 10px;
    margin-top: 20px
  }
    .call {
        margin-right: 2px;
    }
    .video {
        margin-left: 2px;
    }

  .btn-square-md {
    width: 150px !important;
    max-width: 100% !important;
    max-height: 100% !important;
    height: 150px !important;
    text-align: center;
    padding: 0px;
    font-size:12px;
    border: 1px solid black;
    border-radius: 15px;
    margin-left: 30%;
}
#video {
    margin-left: 60px;
}
</style>