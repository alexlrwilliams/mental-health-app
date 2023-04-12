<template>
  <b-form-radio class="doctor-radios__radio-item"  :value="doctor.id">
      <h4>Dr. {{ doctor.firstName + " " + doctor.lastName }}</h4>
      <p>{{ doctor.hospital || "Hospital not specified" }}</p>
  </b-form-radio>
</template>

<script>
export default {
  props: {
    // Define props for appointment data
    doctor: {
      type: Object,
      required: true
    }
  }
}
</script>

<style >
.doctor-radios__radio-item {
  flex: 0 0 50% !important;
  border-radius: 0;
  margin-left: 0 !important;
  padding: 5px;
  justify-content: center;
  flex-flow: column;
}

</style>