<template>
  <!-- Chat form header -->
  <div class="chat-form">
    <div class="chat-header">
      <h3 class="mx-auto"> {{ chatName }} </h3>
      <template v-if="isDoctor">
        <b-button variant="primary" v-b-toggle.sidebar-1>Patient's history <b-icon-card-text/></b-button>
        <chat-sidebar v-if="otherUser" :patient="otherUser"/>
      </template>
    </div>

    <!-- Chat messages content -->
    <div class="chat-messages" ref="messageContainer">
      <chat-message ref="message" :message="msg" v-for="(msg, index) in messages" :key="index"/>
    </div>

    <!-- Chat input section -->
    <form class="chat-input" @submit.prevent="sendMessage">
      <input :disabled="!isCurrent" type="text" v-model="message" placeholder="Type your message...">
      <b-button :disabled="!isCurrent" variant="success" type="Submit">Send<b-icon-chevron-double-right/></b-button>
    </form>
  </div>
</template>

<script>

import Pusher from "pusher-js";
import ChatMessage from "@/components/ChatMessage.vue";
import ChatSidebar from "@/components/ChatSidebar.vue";
import {getChatHistory, joinChat, messageChat} from "@/js/chat";
import {getAppointmentById} from "@/js/appointments";
import {getUserById} from "@/js/user-auth";

export default {
  name: 'ChatPage',
  components: {ChatSidebar, ChatMessage},
  props: {
    id: {type: String, required: true}
  },
  data() {
    return {
      appointment: undefined,
      otherUser: undefined,
      message: '',
      messages: [], // store messages here
    }
  },
  async created() {
    Pusher.logToConsole = true;

    await getAppointmentById(this.id)
        .then(response => this.appointment = response);

    if (this.isCurrent) {
      await joinChat(this.id)
          .then(() => {
            const pusher = new Pusher('07602526ba53e0d9ccb6', {
              cluster: 'eu'
            });

            const channel = pusher.subscribe(this.id);
            channel.bind('JOIN', (data) => {
              this.messages.push({...data, type: "JOIN"});
            });

            channel.bind('MESSAGE', (data) => {
              this.messages.push({...data, type: "MESSAGE"});
              this.$nextTick(() => {
                const lastMessage = this.$refs.message[this.$refs.message.length - 1].$refs.content;
                lastMessage.scrollIntoView({behavior: 'smooth', block: 'end'});
              });
            });
          })

      this.messages = await getChatHistory(this.id);
    }

    if (this.isDoctor) {
      this.otherUser = await getUserById(this.appointment.patientId);
    } else {
      this.otherUser = await getUserById(this.appointment.docId);
    }
  },
  methods: {
    async sendMessage() {
      if (this.message) {
        // add message to messages array
        await messageChat(this.id, this.$store.getters.user.id, this.message)
            .then(() => {
              this.message = '';
            });
      }
    },
  },
  computed: {
    isDoctor() {
      return this.$store.getters.user.role === "DOCTOR";
    },
    chatName() {
      return this.otherUser ? `${this.otherUser.firstName} ${this.otherUser.lastName}` : "";
    },
    isCurrent() {
      return new Date(this.appointment.endTime) > new Date();
    }
  }
}
</script>

<style scoped>
/* Header */
.chat-header {
  background-color: #f2f2f2;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* Messages section */
.chat-messages {
  overflow-y:scroll;
  height: 73vh;
  display: flex;
  flex-flow: column;
  padding: 10px;
  gap: 5px;
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

</style>