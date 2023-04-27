package com.com3014.userauthservice.model.json.user;
import com.com3014.userauthservice.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public abstract class JsonUser {
        @Email
        @NotBlank
        protected String email;

        @NotBlank
        protected String firstName;
        @NotBlank
        protected String lastName;
        @NotBlank
        protected String address;

        public void setEmail(String email) {
                this.email = email;
        }

        public String getEmail() {
                return email;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }


}