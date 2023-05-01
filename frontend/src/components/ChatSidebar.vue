<template>
  <b-sidebar class='slidebar' id="sidebar-1" title="Patient's history:" shadow>
    <div class="px-3 py-2">
      <p><b>Name:</b> {{ `${patient.firstName} ${patient.lastName}` }} </p>
      <div v-for="(history, index) in historyAppointmentDetails" :key="index" style="border: 1px solid green; padding: 10px; margin-bottom: 10px">
        <p v-if="history.startTime"><b>Appointment:</b>{{ history.startTime | formatDate }}</p>
        <p><b>Description:</b>{{ history.summary }}</p>
      </div>
    </div>
  </b-sidebar>
</template>

<script>
import {getUserAppointments} from "@/js/appointments";
import moment from "moment/moment";

export default {
  name: "ChatSidebar",
  props: {
    patient: {type: Object, required: true}
  },
  data() {
    return {
      historyAppointmentDetails: []
    }
  },
  async created() {
    var appointments = await getUserAppointments(this.patient.id);
    this.historyAppointmentDetails = appointments
        .filter((appointment) => new Date() > new Date(appointment.endTime))
        .filter(appointment => !appointment.cancelled)
        .sort((a, b) => new Date(b.endTime) - new Date(a.endTime));
  },
  filters: {
    formatDate: function (value) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Intl.DateTimeFormat('en-US', options).format(moment(value));
    },
  }
}
</script>

<style scoped>
  /* Slide Bar */
  #sidebar-1 {
    text-align: center; /* Center the title text */
  }


</style>