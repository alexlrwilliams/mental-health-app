package com.com3014.userauthservice.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Document("user")
public class User implements UserDetails {
    @Id
    @JsonView(Views.Public.class)
    private UUID id;

    @JsonView(Views.Public.class)
    private String username;

    private String password;

    @JsonView(Views.Public.class)
    private Role role;

    @JsonView(Views.Public.class)
    private String firstName;

    @JsonView(Views.Public.class)
    private String lastName;

    @JsonView(Views.Public.class)
    private String address;

    @JsonView(Views.Public.class)
    private String hospital;

    @JsonView(Views.Public.class)
    private String profilePictureUrl;

    public User() {
    }

    public User(String username, String password, Role role,
                String firstName, String lastName, String address) {
        super();
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonView(Views.Public.class)
    public UUID getId() {
        return id;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getHospital() {
        return hospital;
    }

    public User setHospital(String hospital) {
        this.hospital = hospital;
        return this;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public User setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
        return this;
    }

    public class Views {
        public static class Public {
        }
    }
}