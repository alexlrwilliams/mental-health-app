<template>
  <b-sidebar class='slidebar' id="sidebar-1" title="Patient's history:" shadow>
    <div class="px-3 py-2">
      <p><b>Name:</b> Ahmed </p>
      <div v-for="(history, index) in historyAppointmentDetails" :key="index" style="border: 1px solid green; padding: 10px; margin-bottom: 10px">
        <p><b>Appointment:</b>{{ history.appointmentDate }}</p>
        <p><b>Description:</b>{{ history.description }}</p>
      </div>
    </div>
  </b-sidebar>
</template>

<script>
export default {
  name: "ChatSidebar",
  props: {
    patient: {type: Object, required: false}
  },
  data() {
    return {
      historyAppointmentDetails: [
        {
          'appointmentDate': '20 April 2023',
          'description': ' I have been feeling depressed lately ...'
        },
        {
          'appointmentDate': '29 April 2023',
          'description': 'I am feeling much better now. But I have a new problem ...'
        }
      ]
    }
  }
}
</script>

<style scoped>
  /* Slide Bar */
  #sidebar-1 {
    text-align: center; /* Center the title text */
  }


</style>