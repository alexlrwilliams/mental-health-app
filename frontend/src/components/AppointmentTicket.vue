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
          <b-card-text v-if="isPatient">
            Your appointment with Dr. <b>{{ this.doctorName }}</b> has terminated.
            {{ appointment.type === 'CHAT' ? ' However, you are still able to access your previous chat with the doctor, by clicking the button below.' : '' }}
          </b-card-text>
          <b-card-text v-else>
            Your appointment with <b>{{ this.patientName }}</b> has terminated.
            {{ appointment.type === 'CHAT' ? ' However, you are still able to access your previous chat with the patient, by clicking the button below.' : '' }}
          </b-card-text>
          <b-button :to="`/chat/${appointment.id}`" v-if="appointment.type === 'CHAT'" class="chat-access" variant="primary">Access chat</b-button>
        </template>

        <template v-else>
            <b-card-text v-if="isPatient">
                Your appointment with Dr. <b>{{ this.doctorName }}</b> is now confirmed. You will be able to join the appointment 10min before the booked timing.
            </b-card-text>
            <b-card-text v-else>
              You have an appointment with <b>{{ this.patientName }}</b>. Here is their summary:
              <br>
              {{ appointment.summary }}
            </b-card-text>
            <b-button :to="`/chat/${appointment.id}`" :disabled="!isAppointmentCurrent" class="join-btn" variant="success">Join</b-button>
            <b-button @click="show" v-if="isPatient" class="cancel-btn" variant="danger">Cancel</b-button>
            <b-button class="summary-btn" variant="primary" @click="showModal=true">View summary</b-button>
            <b-modal @ok="cancel" ok-variant="danger" :id="`modal-${appointment.id}`" centered title="Confirm" ok-title="Confirm" cancel-title="Cancel">
              Please confirm you want to delete your appointment with <b>{{ this.doctorName }}</b>. Once you cancel an appointment, you cannot undo it. However, you can still access it in the "cancelled" tab.
            </b-modal>
        </template>

          <b-modal v-model="showModal" title="Appointment summary:" hide-footer>
            <template v-if="!isPatient">
              <h5>Patient name:</h5><p>{{ appointment.patientId }}</p>
            </template>
            <h5>Appointment type:</h5><p>{{ appointment.type }}</p>
            <h5>Description:</h5><p>{{ appointment.summary }}</p>
          </b-modal>

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
      doctorName: undefined,
      patientName: undefined,
      showModal: false
    }
  },
  methods: {
    async cancel() {
      await updateAppointment(this.appointment.id, {...this.appointment, cancelled: true});
      this.$emit("cancelEvent", this.appointment.id);
    },
    show() {
      this.$bvModal.show(`modal-${this.appointment.id}`);
    }
  },
  async created() {
    this.fetching = true;
    if (this.isPatient) {
      const doctor = await getUserById(this.appointment.docId);
      this.doctorName = `${doctor.firstName} ${doctor.lastName}`;
    } else {
      const patient = await getUserById(this.appointment.patientId);
      this.patientName = `${patient.firstName} ${patient.lastName}`;
    }
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
      return startTime < now && endTime > now;
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
.summary-btn {
    margin-left: 3px;
}
</style>