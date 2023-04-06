<template v-if="availableDoctors.status === 'free'">
    <b-form-radio-group>
        <div class="row">
            <b-form-radio class="btn appointment-type-radios__radio-item" ><h4>Dr. {{ available.doctor }}</h4> <p>{{ available.hospital }}</p></b-form-radio>
        </div>
    </b-form-radio-group>
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
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid black;
  padding: 5px;
  margin-right: 20px;
  margin-bottom: 20px;
}
.appointment-type-radios__radio-item:hover {
  border-color: blue;
}
</style>