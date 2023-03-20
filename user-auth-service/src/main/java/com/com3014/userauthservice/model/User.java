package com.com3014.userauthservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("user")
public class User {
    @Id
    private String email;
    private String password;
    private List<Authority> authorities;
    private String firstName;
    private String lastName;
    private String address;

    public User(String email, String password, List<Authority> authorities,
                String firstName, String lastName, String address) {
        super();
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
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