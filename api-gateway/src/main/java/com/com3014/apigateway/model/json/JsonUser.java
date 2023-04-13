package com.com3014.apigateway.model.json;

import com.com3014.apigateway.model.Role;

import java.util.List;

public class JsonUser {
    private String username;
    private Role role;
    private String firstName;
    private String lastName;
    private String address;

    public JsonUser() {
    }

    public JsonUser(String username, String password, Role role,
                    String firstName, String lastName, String address) {
        this.username = username;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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