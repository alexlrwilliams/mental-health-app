package com.com3014.userauthservice.model.json.user;


public class JsonUpdateUser extends JsonUser {

        public JsonUpdateUser(String email, String firstName, String lastName, String address) {
                this.email = email;
                this.firstName = firstName;
                this.lastName = lastName;
                this.address = address;
        }
}