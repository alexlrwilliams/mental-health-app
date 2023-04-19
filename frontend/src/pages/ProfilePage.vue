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
        <b-button variant="primary" class="edit" v-if="isDisabled" @click="EditProfile">Edit profile</b-button>
        <b-button variant="success" class="save" v-else @click="saveChanges">Save</b-button>
      </div>
  </b-card>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  data() {
    return {
      profilePicture: '',
      firstName: '',
      lastName: '',
      homeAddress: '',
      role: '',
      hospital: 'Royal hospital Guildford',
      emailAddress: '',

      isDisabled: true,
    }
  },
  computed: {
    ...mapGetters(['user']),
  },
  created() {
    this.profilePicture = 'path/to/default-profile-picture.jpg'; //fetch s3 picture from url?
    this.firstName = this.user.firstName;
    this.lastName = this.user.lastName;
    this.emailAddress = this.user.email;
    this.homeAddress = this.user.address;
    this.role = this.user.role;
    this.firstName = this.user.firstName;
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
    EditProfile() {
      this.isDisabled = false;
    },
    async saveChanges() {
      await this.$store.dispatch('updateUser', {
        id: this.$store.getters.user.id,
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.emailAddress,
        role: this.role,
        address: this.homeAddress
      })
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


