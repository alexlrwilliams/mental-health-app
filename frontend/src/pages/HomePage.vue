<template>
  <div>
    <div class='col-md-8 mx-auto center'>
      <b-button v-if="isPatient" class="book-appointment-btn" variant='success' to='/book-appointment' ><h4>Book an appointment</h4></b-button>
      <b-tabs content-class="mt-3">
        <AppointmentTab @cancelEvent="cancel" :fetching="fetching" class="appointment-tab-title" title="Upcoming" :appointments="upcomingAppointments" dot-class="upcoming-dot" :active="true"/>
        <AppointmentTab :fetching="fetching" title="Previous" :appointments="previousAppointments" dot-class="previous-dot"/>
        <AppointmentTab :fetching="fetching" title="Cancelled" :appointments="cancelledAppointments" header-class="cancelled-title"/>
      </b-tabs>
    </div>
  </div>
</template>

<script>
import AppointmentTab from "@/components/AppointmentTab.vue";
import {getUserAppointments} from "@/js/appointments";
export default {
  name: 'HomePage', 
  components: {
    AppointmentTab,
  },
  data() {
    return {
      fetching: false,
      appointments: []
    }
  },
  async created() {
    this.fetching = true;
    this.appointments = await getUserAppointments(this.$store.getters.user.id);
    this.fetching = false;
  },
  computed: {
    upcomingAppointments() {
      return this.appointments
          .filter((appointment) => new Date() < new Date(appointment.endTime))
          .filter(appointment => !appointment.cancelled)
          .sort((a, b) => new Date(a.endTime) - new Date(b.endTime));
      },
    previousAppointments() {
      return this.appointments
          .filter((appointment) => new Date() > new Date(appointment.endTime))
          .filter(appointment => !appointment.cancelled)
          .sort((a, b) => new Date(b.endTime) - new Date(a.endTime));
    },
    cancelledAppointments() {
      return this.appointments.filter(appointment => appointment.cancelled);
    },
    isPatient() {
      return this.$store.getters.user.role === "PATIENT";
    }
  },
  methods: {
    cancel(id) {
      const appointment = this.appointments.find(appointment => appointment.id === id);
      this.$set(appointment, 'cancelled', true);
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
  .tab-title {
    font-size: 1.3rem;
  }
  .tabs {
    min-width: 65vw;
  }
</style>