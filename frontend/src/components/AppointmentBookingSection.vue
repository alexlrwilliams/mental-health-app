<template>
  <div class="booking-section">
    <b-card class="booking-section-form">
      <b-card-header ref="header">
        <h5><b>{{ title }}</b></h5>
        <p>{{ label }} </p>
      </b-card-header>
      <b-card-body>
        <slot></slot>
      </b-card-body>
    </b-card>
  </div>
</template>

<script>
export default {
  name: "AppointmentBookingSection",
  props: {
    title: {type: String, required: true},
    label: {type: String},
  }
}
</script>

<style scoped>
.booking-section {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 40px
}
.booking-section-form {
  width: 90%;
  margin: 0 auto;
}
</style>