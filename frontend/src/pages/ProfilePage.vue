<template>
  <b-card class="user-profile-card">
    <div class="user-profile" >
      <b-avatar v-if="profilePicture" :src="profilePicture" alt="Profile Picture" size="150" class="profile-picture"></b-avatar>
      <b-avatar v-else size="150" class="profile-picture"></b-avatar>
        <b-button variant="outline-primary" size="sm" class="mt-2" @click="openFileSelector">Upload profile picture</b-button>
        <input type="file" ref="fileInput" class="d-none" @change="onFileSelected">
      </div>
      <b-form-group label="First Name" label-class="font-weight-bold">
        <b-form-input v-model="firstName" class="input" :disabled="isDisabled"></b-form-input>
      </b-form-group>
      <b-form-group label="Last Name" label-class="font-weight-bold">
        <b-form-input v-model="lastName" :disabled="isDisabled"></b-form-input>
      </b-form-group>
      <b-form-group label="Home Address" label-class="font-weight-bold">
        <b-form-textarea v-model="homeAddress" :disabled="isDisabled"></b-form-textarea>
      </b-form-group>
      <b-form-group label="Role" label-class="font-weight-bold" disabled >
        <b-form-input v-model="role"></b-form-input>
      </b-form-group>
      <b-form-group label="Hospital" v-if="role === 'DOCTOR'" label-class="font-weight-bold">
        <b-form-input v-model="hospital" :disabled="isDisabled"></b-form-input>
      </b-form-group>
      <b-form-group label="Email Address" label-class="font-weight-bold">
        <b-form-input type="email" v-model="emailAddress" :disabled="isDisabled"></b-form-input>
      </b-form-group>
      <div class="btns">
        <b-button variant="primary" class="edit" v-if="isDisabled" @click="enableInput">Edit profile</b-button>
        <b-button variant="success" class="save" v-else @click="saveChanges">Save</b-button>
      </div>
  </b-card>
</template>

<script>
export default {
  data() {
    return {
      profilePicture: 'path/to/default-profile-picture.jpg',
      firstName: 'Ahmed',
      lastName: 'Henine',
      homeAddress: 'Battersea Course, Spiers House, Guildford, GU2 7JQ',
      role: 'DOCTOR',
      hospital: 'Royal hospital Guildford',
      emailAddress: 'ahmedhenine@gmail.com',

      isDisabled: true,
    }
  },
    methods: {
    openFileSelector() {
      this.$refs.fileInput.click();
    },
    onFileSelected(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = () => {
          this.profilePicture = reader.result;
        };
        reader.readAsDataURL(file);
      }
    },
    enableInput() {
      this.isDisabled = false;
    },
    saveChanges() {
      this.isDisabled = true;
    }
  },
}
</script>

<style>
.user-profile-card {
  padding: 20px;
  width: 70%; /* adjust the width of the card */
  margin-left: 15%; /* move the card 10% from the left side */
  margin-right: 15%; /* move the card 10% from the right side */
  margin-top: 20px;
}
.user-profile {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.btns {
  display: flex;
  flex-direction: row;
  justify-content: center;
}
.edit {
  width: 40%;
}
.save {
  width: 40%;
}
</style>


