<template>
  <div>
  <div @submit.prevent="login">
    <AppointmentBookingSection title="Book your appointment" >
      <p>Please fill in the forms below to book your appointment.</p>
    </AppointmentBookingSection>

    <AppointmentBookingSection ref="section1" title="Why would you like an appointment?" label="In the textbox below, tell us about your symptoms and reason for booking.">
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
    </AppointmentBookingSection>

    <AppointmentBookingSection ref="section2" title="When would you like the appointment?" label="Please chose your availability below.">
      <b-form @submit.prevent="" >
        <label for="example-datepicker">Choose a date:</label>
        <b-form-datepicker v-model="selectedDate" :disabled="!section1Complete" class="mb-2"></b-form-datepicker>
        <label for="example-datepicker">Choose a time:</label>
        <b-form-timepicker v-model="selectedTime" :disabled="!section1Complete" minutes-step="15"></b-form-timepicker>
      </b-form>

      <div class="booking-section-btn">
        <b-button variant="primary" class='booking-section-btn-proceed' :disabled="!section1Complete" @click="proceedToSection3">Proceed to meeting types</b-button>
      </div>
    </AppointmentBookingSection>

    <AppointmentBookingSection ref="section3" title="How do you wish to contact your doctor?" label="Please chose which type of appointment you would like.">
      <b-form @submit.prevent="" class="appointment-type-radios">
        <b-form-group>
          <b-form-radio-group
              v-model="appointmentType"
              buttons
              button-variant="outline-secondary"
          >
            <b-form-radio class="btn appointment-type-radios__radio-item" value="CHAT" :disabled="!section2Complete"><h3>Chat <b-icon-chat-dots-fill/></h3></b-form-radio>
            <b-form-radio class="btn appointment-type-radios__radio-item" value="VIDEO" :disabled="!section2Complete"><h3>Video call <b-icon-camera-video-fill/></h3></b-form-radio>
          </b-form-radio-group>
        </b-form-group>
      </b-form>

      <div class="booking-section-btn">
        <b-button variant="primary" class='booking-section-btn-proceed' :disabled="!section2Complete" @click="proceedToSection4">Proceed to doctors list</b-button>
      </div>
    </AppointmentBookingSection>

    <AppointmentBookingSection ref="section4" title="Which doctor you like an appointment with?" label="Please chose one of the following available doctors.">
        <AvailableDocTicket v-for="(available, index) in availableDoctors" :key="index" :available="available"/> 
    </AppointmentBookingSection>
    <div class="booking-section-btn">
        <b-button type="submit" variant="success" class='booking-section-btn-proceed' :disabled="!section3Complete">Book appointment</b-button><br>
    </div>
 </div>
</div>
</template>

<script>
import AppointmentBookingSection from "@/components/AppointmentBookingSection.vue";
import AvailableDocTicket from "@/components/AvailableDocTicket.vue";

export default {
  components: {
    AppointmentBookingSection, 
    AvailableDocTicket
  },
  data() {
    return {
      section1Complete: false,
      section2Complete: false,
      section3Complete: false,

      description: '',

      selectedDate: '',
      selectedTime: '',

      appointmentType: '',

      availableDoctors: [
        {
          doctor: 'Ahmed Henine',
          hospital: 'Royal Hospital Guildford',
          status: 'free',
        },
        {
          doctor: 'Alex Williams',
          hospital: 'London Hospital',
          status: 'free',
        },
                {
          doctor: 'Jenny Maria',
          hospital: 'Manchester Hospital',
          status: 'free',
        } 
      ]
    }
  },
  name: 'BookAppointmentForm',
  methods: {
    proceedToSection(sectionFlag, sectionToScroll) {
      this[sectionFlag] = true;
      sectionToScroll.$refs.header.scrollIntoView({ behavior: 'smooth' });
    },
    proceedToSection2() {
      // Your code to validate section 1 goes here
      this.proceedToSection("section1Complete", this.$refs.section2);
    },
    proceedToSection3() {
      // Your code to validate section 2 goes here
      this.proceedToSection("section2Complete", this.$refs.section3);
    },
    proceedToSection4() {
      // Your code to validate section 3 goes here
      this.proceedToSection("section3Complete", this.$refs.section4);
    },
    book() {

    }
  }
}

</script>
<style scoped>
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
  .appointment-type-radios {
    display: flex;
    justify-content: center;
  }
  .appointment-type-radios__radio-item {
    width: 20vw;
    height: 10vh;
    display: flex !important;
    justify-content: center;
    align-items: center;
  }
</style>
<style>
  .appointment-type-radios__radio-item > input[type=radio] {
    display: none;
  }
</style>