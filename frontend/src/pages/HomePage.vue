<template>
  <div>
    <div class='col-md-8 mx-auto center'>
      <b-button class="book-appointment-btn" variant='success' to='/book-appointment' ><h4>Book an appointment</h4></b-button>
      <b-card class="appointment-box">
              <b-card-header>
                <h4>Upcoming appointments <span class="upcoming-dot"></span></h4>
              </b-card-header>
              <b-card-body>
                  <AppointmentTicket v-for="(appointment, index) in upcomingAppointments" :key="index" :appointment="appointment" />
              </b-card-body>
      </b-card>

      <b-card class="appointment-box">
              <b-card-header>
                <h4>Previous appointments <span class="previous-dot"></span></h4>
              </b-card-header>
              <b-card-body>
                <AppointmentTicket v-for="(appointment, index) in previousAppointments" :key="index" :appointment="appointment" />
              </b-card-body>
      </b-card>

      <b-card class="appointment-box">
              <b-card-header>
                <h4 class="cancelled-title">Cancelled appointments:</h4>
              </b-card-header>
              <b-card-body>
                <AppointmentTicket v-for="(appointment, index) in cancelledAppointments" :key="index" :appointment="appointment"/>
              </b-card-body>
      </b-card>

    </div>
  </div>
</template>

<script>
import AppointmentTicket from '../components/AppointmentTicket.vue';
export default {
  name: 'HomePage', 
  components: {
    AppointmentTicket,
  },
  data() {
    return {
      upcomingAppointments: [
        {
          date: "Wednesday, April the 11th, 2023 - 03:15pm",
          doctor: "Ahmed Henine",
          status: "upcoming",
        },
        {
          date: "Friday, April the 7th, 2023 - 09:00am",
          doctor: "Alex Williams",
          status: "upcoming",
        }
      ], 
      previousAppointments: [
        {
          date: "Tuesday, March the 21st, 2023 - 04:15pm",
          doctor: "Jenny Maria",
          status: "previous",
        },
        {
          date: "Monday, March the 13th, 2023 - 012:30pm",
          doctor: "Karthi",
          status: "previous",
        }
      ], 
      cancelledAppointments: [
        {
          date: "Friday, April the 14th, 2023 - 10:45am",
          doctor: "Al-Ayhem",
          status: "cancelled",
        } 
      ]
  }},
}
</script>

<style>
  .center {
    display: flex;
    align-items: center;
    flex-flow: column;
    margin-top: 5%;
    gap: 40px;
  }
  .book-appointment-btn {
    width: 80%;
  }
  .appointment-box {
    width: calc(100% - 10px);
    margin: 0 auto;
  }
  .upcoming-dot {
    height: 13px;
    width: 13px;
    background-color: green;
    border-radius: 50%;
    display: inline-block;
  }
  .previous-dot {
    height: 13px;
    width: 13px;
    background-color: rgb(192, 15, 15);
    border-radius: 50%;
    display: inline-block;
  }
  .cancelled-title {
    color: brown;
  }
</style>