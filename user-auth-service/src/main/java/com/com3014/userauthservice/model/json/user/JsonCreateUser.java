package com.com3014.userauthservice.model.json.user;
import com.com3014.userauthservice.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class JsonCreateUser extends JsonUser {
    @NotBlank
    private String password;
    @NotNull
    private Role role;

    public JsonCreateUser(String email, String password, Role role,
                          String firstName, String lastName, String address) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}