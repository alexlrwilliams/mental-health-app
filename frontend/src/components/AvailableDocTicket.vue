<template>
  <b-form-radio class="appointment-type-radios__radio-item"  :value="doctor.docId">
    <div class="row">
      <h4>Dr. {{ doctor.doctor }}</h4>
      <p>{{ doctor.hospital }}</p>
    </div>
  </b-form-radio>
</template>

<script>
export default {
  props: {
    // Define props for appointment data
    available: {
      type: Object,
      required: true
    }
  }
}
</script>

<style >
.appointment-type-radios__radio-item {
  flex: 0 0 50%;
  border-radius: 0;
  margin-left: 0 !important;
  padding: 5px;
}

</style>