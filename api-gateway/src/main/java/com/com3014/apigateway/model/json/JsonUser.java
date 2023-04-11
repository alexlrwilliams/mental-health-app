package com.com3014.apigateway.model.json;

import com.com3014.apigateway.model.Role;

import java.util.List;

public class JsonUser {
    private String email;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private String firstName;
    private String lastName;
    private String address;

    public JsonUser() {
    }

    public JsonUser(String email, String password, Role role,
                    String firstName, String lastName, String address) {
        this.email = email;
        this.password = password;
        this.authorities = List.of(new SimpleGrantedAuthority(role));
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

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
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


    public record SimpleGrantedAuthority (Role authority) {}
}