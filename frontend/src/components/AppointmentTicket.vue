<template>
  <div>
    <b-card class="ticket" bg-variant="light" :title="formattedDate">
        <div class="d-flex justify-content-center mb-3" v-if="fetching">
          <b-spinner label="Loading..."></b-spinner>
        </div>
        <template v-else-if="appointment.cancelled">
            <b-card-text>
                Your appointment with Dr. <b>{{ this.doctorName }}</b> is cancelled. If you wish to book another appointment, please refer to the green button at the top of the page.
            </b-card-text>
        </template>

        <template v-else-if="isAppointmentPrevious">
          <b-card-text>
            Your appointment with Dr. <b>{{ this.doctorName }}</b> has terminated.
            {{ appointment.type === 'CHAT' ? ' However, you are still able to access your previous chat with the doctor, by clicking the button below.' : '' }}
          </b-card-text>
          <b-button v-if="appointment.type === 'CHAT'" class="chat-access" variant="primary">Access chat</b-button>
        </template>

        <template v-else>
            <b-card-text>
                Your appointment with Dr. <b>{{ this.doctorName }}</b> is now confirmed. You will be able to join the appointment 10min before the booked timing.
            </b-card-text>
            <b-button :disabled="!isAppointmentCurrent" class="join-btn" variant="success">Join</b-button>
            <b-button @click="cancel" v-if="isPatient" class="cancel-btn" variant="danger">Cancel</b-button>
        </template>
      </b-card>
  </div>
</template>

<script>
import {getUserById} from "@/js/user-auth";
import {updateAppointment} from "@/js/appointments";

export default {
  name: "AppointmentTicket",
  props: {
    // Define props for appointment data
    appointment: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      fetching: false,
      doctorName: undefined
    }
  },
  methods: {
    async cancel() {
      await updateAppointment(this.appointment.id, {...this.appointment, cancelled: true});
      this.$emit("cancelEvent", this.appointment.id);
    }
  },
  async created() {
    this.fetching = true;
    const doctor = await getUserById(this.appointment.docId);
    this.doctorName = `${doctor.firstName} ${doctor.lastName}`;
    this.fetching = false;
  },
  computed: {
    formattedDate() {
      // Format date and time to display on the ticket
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
      const start = new Date(this.appointment.startTime).toLocaleDateString(undefined, options);
      return `${start}`;
    },
    isAppointmentPrevious() {
      // Check if the appointment has ended
      const now = new Date();
      const endTime = new Date(this.appointment.endTime);
      return endTime < now;
    },
    isAppointmentCurrent() {
      // Check if the appointment has ended
      const now = new Date();
      const endTime = new Date(this.appointment.endTime);
      const startTime = new Date(this.appointment.startTime);
      return startTime > now && endTime < now;
    },
    isPatient() {
      return this.$store.getters.user.role === "PATIENT";
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