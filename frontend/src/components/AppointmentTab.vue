<template>
  <b-tab :title="title" :active="active">
    <b-card class="appointment-box">
      <b-card-header>
        <h4 :class="headerClass"> {{ title }} Appointments <span :class="dotClass"></span> </h4>
      </b-card-header>
      <b-card-body>
        <AppointmentTicket v-for="appointment in appointments" :key="appointment.id" :appointment="appointment" />
      </b-card-body>
    </b-card>
  </b-tab>
</template>

<script>
import AppointmentTicket from "@/components/AppointmentTicket.vue";

export default {
  name: "AppointmentTab",
  components: {
    AppointmentTicket
  },
  props: {
    appointments: {type: Array, required: true},
    title: {type: String, required: true},
    dotClass: {type: String, default: ""},
    active: {type: Boolean, default: false},
    headerClass: {type: String, default: ""}
  }
}
</script>

<style scoped>
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