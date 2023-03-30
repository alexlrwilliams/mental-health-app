<template>
  <div>
    <NavBar />
  <div @submit.prevent="login">
  <div class="booking-section">
        <b-card class="booking-section-form">
            <b-card-header>
                <h4><b>Book your appointment:</b></h4>
                <p>Welcome to <b>EvenBetterHealth</b>. Please fill in the forms below to book your appointment:</p>

                <h5><b>Section 01:</b></h5> 
                <p>On the textbox below, could you please tell us more about the issue ?</p>
            </b-card-header>
            <b-card-body >
                <b-form @submit.prevent="" >
                  <b-form-group id="address-group"
                                    label="Description of symptoms:*"
                                    label-for="description-input">
                                  <b-form-textarea 
                                    id="description-input"
                                    class="description-textarea"
                                    v-model="description"
                                    type="text"
                                    required>
                                  </b-form-textarea>
                  </b-form-group>

                <div class="booking-section-btn">
                    <b-button variant="primary" class='booking-section-btn-proceed' v-model="type" @click="proceedToSection2">Proceed to calendar</b-button>
                </div>
                </b-form>
            </b-card-body>
        </b-card>
    </div>

    <div class="booking-section">
        <b-card class="booking-section-form">
        <b-card-header ref="section2">
            <h5><b>Section 02:</b></h5> 
            <p>Please chose your availability using the calendar below: </p>
        </b-card-header>
        <b-card-body>

        <b-form @submit.prevent="" >
            <h4>Date:</h4>
              <b-calendar block locale="en-US" v-model="selectedDate" @input="saveSelectedDate" :disabled="!section1Complete"></b-calendar>
            <h4>Time:</h4>
              <b-row class='time'>
                <b-col md="auto">
                  <b-time v-model="selectedTime" minutes-step="15" locale="en" @context="saveSelectedTime" :disabled="!section1Complete"></b-time>
                </b-col>
              </b-row>

         </b-form>

            <div class="booking-section-btn">
                <b-button variant="primary" class='booking-section-btn-proceed' :disabled="!section1Complete" @click="proceedToSection3">Proceed to meeting types</b-button>
            </div>
        </b-card-body>
        </b-card>
    </div>

    <div class="booking-section">
        <b-card class="booking-section-form">
            <b-card-header ref="section3">
                <h5><b>Section 03:</b></h5> 
                <p>How do you wish to contact your doctor ? Please pick one of the following options:</p>
            </b-card-header>
            <b-card-body>

                <b-form @submit.prevent="" >
                    <b-button v-model="type" class="btn btn-square-md" id='call' :variant="buttonVariantCall" @click="toggleButtonCall" :disabled="!section2Complete"><h3>Chat <b-icon-chat-dots-fill></b-icon-chat-dots-fill></h3></b-button>
                    <b-button v-model="type" class="btn btn-square-md" id='video' :variant="buttonVariantVideo" @click="toggleButtonVideo" :disabled="!section2Complete"><h3>Video call <b-icon-camera-video-fill></b-icon-camera-video-fill></h3></b-button>
                </b-form>
                
                <div class="booking-section-btn">
                    <b-button variant="primary" class='booking-section-btn-proceed' :disabled="!section2Complete" @click="proceedToSection4">Proceed to doctors list</b-button>
                </div>
            </b-card-body>
        </b-card>
    </div>

    <div class="booking-section">
        <b-card class="booking-section-form">
            <b-card-header ref="section4">
                <h5><b>Section 04:</b></h5> 
                <p>Please select your doctor based on the following availabilities:</p>
            </b-card-header>
            <b-card-body>

            </b-card-body>
        </b-card>
    </div>

    <div class="booking-section-btn">
        <b-button type="submit" variant="success" class='booking-section-btn-proceed' :disabled="!section3Complete">Book appointment</b-button><br>
    </div>
 </div>
</div>
</template>

<script>
import NavBar from '../components/NavBar' 

export default {
  data() {
    return {
      section1Complete: false,
      section2Complete: false,
      section3Complete: false,

      description: '',

      selectedDate: '',
      savedDate: '',
      selectedTime: '', 

      type: '',
      buttonVariantCall: 'light',
      buttonVariantVideo: 'light',
      isClickedCall: false,
      isClickedVideo: false
    }
  },
  name: 'BookAppointmentForm',
  components: {
    NavBar
  },
  methods: {
    proceedToSection2() {
      // Your code to validate section 1 goes here
      this.section1Complete = true;
      this.scrollToSection2();
    },
    proceedToSection3() {
      // Your code to validate section 2 goes here
      this.section2Complete = true;
      this.scrollToSection3();
    },
    proceedToSection4() {
      // Your code to validate section 3 goes here
      this.section3Complete = true;
      this.scrollToSection4();
    },
    scrollToSection2() {
      this.$refs.section2.scrollIntoView({ behavior: 'smooth' });
    },
    scrollToSection3() {
      this.$refs.section3.scrollIntoView({ behavior: 'smooth' });
    },
    scrollToSection4() {
      this.$refs.section4.scrollIntoView({ behavior: 'smooth' });
    },
    saveSelectedDate() {
      this.savedDate = this.selectedDate;
    },
    saveSelectedTime() {

    },
    toggleButtonCall() {
      if (!this.isClickedCall) {
        this.isClickedCall = true
        this.isClickedVideo = false
        this.buttonVariantCall = 'primary'
        this.buttonVariantVideo = 'light'
      }
    },
    toggleButtonVideo() {
      if (!this.isClickedVideo) {
        this.isClickedVideo = true
        this.isClickedCall = false
        this.buttonVariantVideo = 'primary'
        this.buttonVariantCall = 'light'
      }
    },
    book() {

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
    width:50%;
    border-radius: 10px;
    margin-top: 20px
  }
  .time {
    display: flex;
    flex-direction: column;
    align-items: center;
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