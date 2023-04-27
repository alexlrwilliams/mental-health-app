<template>
  <b-tab title-item-class="tab-title" :title="title" :active="active">
    <div class="d-flex justify-content-center mb-3" v-if="fetching">
      <b-spinner label="Loading..."></b-spinner>
    </div>
    <template v-else>
      <b-alert variant="warning" :show="appointments.length === 0">No appointments found</b-alert>
      <b-card v-if="appointments.length > 0" class="appointment-box">
        <b-card-header>
          <h4 :class="headerClass"> {{ title }} Appointments <span :class="dotClass"></span> </h4>
        </b-card-header>
        <b-card-body>
          <AppointmentTicket @cancelEvent="emitEvent" v-for="appointment in appointments" :key="appointment.id" :appointment="appointment" />
        </b-card-body>
      </b-card>
    </template>
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
    fetching: {type: Boolean, required: true},
    appointments: {type: Array, required: true},
    title: {type: String, required: true},
    dotClass: {type: String, default: ""},
    active: {type: Boolean, default: false},
    headerClass: {type: String, default: ""}
  },
  methods: {
    emitEvent(value) {
      this.$emit("cancelEvent", value)
    }
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