<template>
  <!-- Chat form header -->
  <div class="chat-form">
    <div class="chat-header">
      <h3 class="mx-auto">{{ patientName }}</h3>
      <b-button variant="primary" v-b-toggle.sidebar-1>Patient's history <b-icon icon="card-text"></b-icon></b-button>

      <b-sidebar class='slidebar' id="sidebar-1" title="Patient's history:" shadow>
        <div class="px-3 py-2">
            <p><b>Name:</b> {{ patientName }}</p> 
            <p><b>Gender:</b>{{ gender }}</p>
            <p><b>Age:</b>{{ age }}</p>
            <div v-for="(history, index) in historyAppointmentDetails" :key="index" style="border: 1px solid green; padding: 10px; margin-bottom: 10px">
              <p><b>Appointment:</b>{{ history.appointmentDate }}</p>
              <p><b>Description:</b>{{ history.description }}</p>
            </div>
        </div>
      </b-sidebar>
    </div>

    <!-- Chat messages content -->
    <div class="chat-messages" ref="messageContainer">
      <div v-for="(msg, index) in messages" :key="index">
        <div v-if="msg.sent">
          <div class="chat-message sent">
            <p>{{ msg.content }}</p>
            <span class="timestamp">{{ msg.timestamp | formatDate }}</span>
          </div>
        </div>
        <div v-else>
          <div class="chat-message received">
            <p>{{ msg.content }}</p>
            <span class="timestamp">{{ msg.timestamp | formatDate }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- Chat input section -->
    <form class="chat-input" @submit.prevent="sendMessage">
      <input type="text" v-model="message" placeholder="Type your message...">
      <b-button variant="success" type="Submit">Send<b-icon icon="chevron-double-right"></b-icon></b-button>
    </form>
  </div>
</template>

<script>

export default {
  data() {
    return {
      patientName: 'Ahmed Henine',
      message: '',
      messages: [], // store messages here

      age: '22',
      gender: 'Male',
      historyAppointmentDetails: [
        {
          'appointmentDate': '20 April 2023',
          'description': ' I have been feeling depressed lately ...'
        },
        {
          'appointmentDate': '29 April 2023',
          'description': 'I am feeling much better now. But I have a new problem ...'
        }
        ]
    }
  },
  methods: {
    sendMessage() {
      // TODO: Push messages[] to Chat database. 
      if (this.message) {
        // add message to messages array
        this.messages.push({
          content: this.message,
          sent: true,
          timestamp: new Date(),
        });
        // clear input
        this.message = '';

        // scroll to last message
        this.$nextTick(() => {
        const lastMessage = this.$refs.lastMessage[this.$refs.lastMessage.length - 1];
        lastMessage.scrollIntoView({ behavior: 'smooth', block: 'end' });        
      });
      }
    },
  },
  filters: {
    formatDate: function (value) {
      const options = { hour: 'numeric', minute: 'numeric', hour12: true };
      return new Intl.DateTimeFormat('en-US', options).format(value);
    },
  },
}
</script>

<style scoped>
/* Form */
.chat-form {
  display: flex;
  flex-direction: column;
  height: 100%;
}

/* Header */
.chat-header {
  position: flex;
  width: 100%;
  background-color: #f2f2f2;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.history-button {
  background-color: #1027d2;
  border: none;
  color: white;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
}

/* Slide Bar */
#sidebar-1 {
  text-align: center; /* Center the title text */
}

/* Messages section */
.chat-messages {
  flex: 1;
  overflow-y: auto;
  margin-top: 10px;
  margin-bottom: 70px;
}

.chat-message.sent p {
  background-color: #f2f2f2;
  padding: 10px;
  border-radius: 10px;
  display: inline-block;
  margin-bottom: 5px;
  margin-left: 10px;
  margin-right: 5px;
}

.chat-message.received p {
  background-color: #c3ffc3;
  padding: 10px;
  border-radius: 10px;
  display: inline-block;
  margin-bottom: 5px;
  margin-right: 10px;
  margin-left: auto;
}

.timestamp {
  color: gray;
}

/* Bottom input section */
.chat-input {
  display: flex;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: #eee;
  padding: 10px;
}

.chat-input input[type="text"] {
  flex: 1;
  margin-right: 10px;
  border-radius: 5px;
  padding: 5px;
  border: none;
}

.chat-input button {
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px;
  cursor: pointer;
}

</style>