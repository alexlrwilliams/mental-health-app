<template>
  <div>
    <b-card class="ticket" bg-variant="light" :title="formattedDate">
        <template v-if="appointment.cancelled">
            <b-card-text>
                Your appointment with Dr. <b>{{ appointment.docId }}</b> is cancelled. If you wish to book another appointment, please refer to the green button at the top of the page. 
            </b-card-text>
        </template>

        <template v-else-if="isAppointmentPrevious">
          <b-card-text>
            Your appointment with Dr. <b>{{ appointment.docId }}</b> has terminated.
            {{ appointment.type === 'CHAT' ? ' However, you are still able to access your previous chat with the doctor, by clicking the button below.' : '' }}
          </b-card-text>
          <b-button v-if="appointment.type === 'CHAT'" class="chat-access" variant="primary">Access chat</b-button>
        </template>

        <template v-else>
            <b-card-text>
                Your appointment with Dr. <b>{{ appointment.docId }}</b> is now confirmed. You will be able to join the appointment 10min before the booked timing. 
            </b-card-text>
            <b-button class="join-btn" variant="success">Join</b-button>                
            <b-button class="cancel-btn" variant="danger">Cancel</b-button>
        </template>
      </b-card>
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
.ticket {
  margin-bottom: 20px;
}
.join-btn {
    margin-right: 3px;
}
.cancel-btn {
    margin-left: 3px;
}
</style>