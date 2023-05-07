<template>
  <div :class="messageClass">
    <p ref="content">{{ message.content }}</p>
    <span class="timestamp">{{ message.timestamp | formatDate }}</span>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "ChatMessage",
  props: {
    message: {type: Object, required: true}
  },
  computed: {
    messageClass() {
      let isUser = this.message.user === this.$store.getters.user.id;
      return {
        'join': this.message.type === 'JOIN',
        'chat-message': this.message.type === 'MESSAGE',
        'sent': isUser,
        'received': !isUser
      }
    }
  },
  filters: {
    formatDate: function (value) {
      const options = { hour: 'numeric', minute: 'numeric', hour12: true };
      return new Intl.DateTimeFormat('en-US', options).format(moment(value));
    },
  },
}
</script>

<style scoped>

.chat-message {
  display: flex;
  align-items: center;
}

.chat-message.sent p {
  background-color: #c3ffc3;
  margin-left: 5px;
}

.chat-message.received p {
  background-color: #f2f2f2;
  margin-right: 5px;
}

.chat-message p {
  padding: 10px;
  border-radius: 10px;
  display: inline-block;
  max-width: 50vw;
  flex-wrap: wrap;
  overflow-wrap: break-word;
}

.chat-message.sent {
  align-self: flex-end;
  flex-flow: row-reverse;
}

.join {
  align-self: center;
  display: flex;
  flex-flow: row;
  gap: 5px;
}

.timestamp {
  color: gray;
}
</style>