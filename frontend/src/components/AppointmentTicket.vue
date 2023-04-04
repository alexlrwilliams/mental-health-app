<template>
  <div>

    <template v-if="appointment.status === 'upcoming'">
        <b-card bg-variant="light" :title="appointment.date">
            <b-card-text>
                Your appointment with Dr. <b>{{ appointment.doctor }}</b> is now confirmed. You will be able to join the appointment 10min before the booked timing. 
            </b-card-text>
            <b-button class="join-btn" variant="success">Join</b-button>                
            <b-button class="cancel-btn" variant="danger">Cancel</b-button>
        </b-card> 
    </template>

    <template v-if="appointment.status === 'previous'">
        <b-card bg-variant="light" :title="appointment.date">
            <b-card-text>
            Your appointment with Dr. <b>{{ appointment.doctor }}</b> has terminated. However, you are still able to access your previous chat with the docor, by clicking the button below. 
            </b-card-text>
            <b-button class="chat-access" variant="primary">Access chat</b-button>
        </b-card> 
    </template>

    <template v-if="appointment.status === 'cancelled'">
        <b-card bg-variant="light" :title="appointment.date">
            <b-card-text>
            Your appointment with Dr. <b>{{ appointment.doctor }}</b> is cancelled. If you wish to book another appointment, please refer to the green button at the top of the page. 
            </b-card-text>
        </b-card> 
    </template>

  </div>
</template>

<script>
export default {
  name: "AppointmentTicket",
  props: {
    // Define props for appointment data
    appointment: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
.join-btn {
    margin-right: 3px;
}
.cancel-btn {
    margin-left: 3px;
}
</style>