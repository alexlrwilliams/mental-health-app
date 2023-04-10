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
    appointments: [
{
            id: "68cdefc7-669c-48be-a148-6506dc2f3637",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-04-21T15:30:00.552842800Z",
            endTime: "2023-04-21T15:45:00.552842800Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "my mental health be rough lol",
            type: "VIDEO",
            cancelled: false
        },
        {
            id: "347c3001-5112-42cf-8576-1d075c44be52",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-05-06T20:30:00.552842800Z",
            endTime: "2023-05-06T20:45:00.552842800Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "probs like anxiety or depression lol",
            type: "VIDEO",
            cancelled: false
        },
        {
            id: "0228e247-b83f-4d9c-86cf-308990f0c463",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-04-19T14:15:00.552842800Z",
            endTime: "2023-04-19T14:30:00.552842800Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "anxiety maybe? who knows lol",
            type: "VIDEO",
            cancelled: false
        },
        {
            id: "428bbd7c-e7fc-4050-bf52-6fd3bfe04d44",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-05-07T19:30:00.552842800Z",
            endTime: "2023-05-07T19:45:00.552842800Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "recently had depression or something",
            type: "VIDEO",
            cancelled: false
        },
        {
            id: "decfaf62-dfac-45fd-9638-b9f2723c930b",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-08-07T16:30:00.609940900Z",
            endTime: "2023-08-07T17:45:00.609940900Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "test123",
            type: "CHAT",
            cancelled: false
        },
        {
            id: "36c5c9b8-fea4-4f1c-ae92-2e06b638aa86",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-04-06T20:00:00.552842800Z",
            endTime: "2023-04-06T20:15:00.552842800Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "depression",
            type: "VIDEO",
            cancelled: false
        },
        {
            id: "a848bd57-3b42-4ec4-884f-29b55a2c7128",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-04-06T12:15:00.739546500Z",
            endTime: "2023-04-06T12:30:00.739546500Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "anxiety",
            type: "CHAT",
            cancelled: false
        },
        {
            id: "b484c8b5-eb29-4013-ac66-eec7b77f65b4",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-05-08T19:30:00.300796800Z",
            endTime: "2023-05-08T19:45:00.300796800Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "summary",
            type: "CHAT",
            cancelled: true
        },
        {
            id: "7889a5a0-5586-4aba-bde0-abb11d660de9",
            docId: "0e5c9e0e-65a1-48c1-a8b3-84fe12204055",
            startTime: "2023-03-01T09:00:00.552842800Z",
            endTime: "2023-03-01T09:15:00.552842800Z",
            patientId: "552c9a7a-202a-46bf-865a-6147d32bf165",
            summary: "depression",
            type: "VIDEO",
            cancelled: true
        }
    ]
  }
},
computed: {
    upcomingAppointments() {
      const now = new Date();
      const filteredAppointments = this.appointments.filter((appointment) => {
        const endTime = new Date(appointment.endTime);
        return endTime > now && !appointment.cancelled;
      });
      filteredAppointments.sort((a, b) => {
        const aEndTime = new Date(a.endTime);
        const bEndTime = new Date(b.endTime);
        return aEndTime - bEndTime;
      });
      return filteredAppointments;
    },
    previousAppointments() {
      const now = new Date();
      return this.appointments.filter((appointment) => {
        const endTime = new Date(appointment.endTime);
        return endTime < now || appointment.cancelled;
      });
    },
    cancelledAppointments() {
    return this.appointments.filter(appointment => appointment.cancelled);
    }
  }
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