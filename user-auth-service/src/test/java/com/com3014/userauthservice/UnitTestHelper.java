package com.com3014.userauthservice;

import com.com3014.userauthservice.model.Role;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.user.JsonUpdateUser;
import com.com3014.userauthservice.model.json.user.JsonCreateUser;

public class UnitTestHelper {

    public static final String EMAIL = "john.doe@gmail.com";
    public static final String EMAIL_2 = "jane.smith@gmail.com";
    public static final String PASSWORD = "password";

    public static final String FIRST_NAME = "John";
    public static final String FIRST_NAME_2 = "Jane";


    public static final String LAST_NAME = "Doe";
    public static final String LAST_NAME_2 = "Smith";

    public static final String ADDRESS = "42 Dancing Ave";

    public static final Role ROLE_1 = Role.ADMIN;
    public static final Role ROLE_2 = Role.PATIENT;

    public static final String HOSPITAL = "HOSPITAL";

    public static final JsonCreateUser JSON_CREATE_USER = new JsonCreateUser(EMAIL, PASSWORD, ROLE_1, FIRST_NAME, LAST_NAME, ADDRESS);
    public static final JsonUpdateUser JSON_UPDATE_USER = new JsonUpdateUser(EMAIL, FIRST_NAME, LAST_NAME, ADDRESS, HOSPITAL);
    public static final User testUser1 = new User(EMAIL, PASSWORD, ROLE_1, FIRST_NAME, LAST_NAME, ADDRESS);
    public static final User testUser2 = new User(EMAIL_2, PASSWORD, ROLE_2, FIRST_NAME_2, LAST_NAME_2, ADDRESS);
}
