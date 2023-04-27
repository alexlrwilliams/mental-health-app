package com.com3014.userauthservice.model.json.user;


public class JsonUpdateUser extends JsonUser {

        private String hospital;

        public JsonUpdateUser(String email, String firstName, String lastName, String address, String hospital) {
                this.email = email;
                this.firstName = firstName;
                this.lastName = lastName;
                this.address = address;
                this.hospital = hospital;
        }

        public String getHospital() {
                return hospital;
        }

        public void setHospital(String hospital) {
                this.hospital = hospital;
        }
}