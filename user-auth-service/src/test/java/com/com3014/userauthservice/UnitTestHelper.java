package com.com3014.userauthservice;

import com.com3014.userauthservice.model.Authority;
import com.com3014.userauthservice.model.User;
import com.com3014.userauthservice.model.json.JsonUser;

import java.util.List;

public class UnitTestHelper {

    public static final String EMAIL = "john.doe@gmail.com";
    public static final String EMAIL_2 = "jane.smith@gmail.com";
    public static final String PASSWORD = "password";

    public static final String FIRST_NAME = "John";
    public static final String FIRST_NAME_2 = "Jane";


    public static final String LAST_NAME = "Doe";
    public static final String LAST_NAME_2 = "Smith";

    public static final String ADDRESS = "42 Dancing Ave";

    public static final List<Authority> AUTHORITIES = List.of(Authority.BOOK_APPOINTMENT, Authority.ACCESS_ALL_PATIENTS);

    public static final JsonUser jsonUser = new JsonUser(EMAIL, PASSWORD, AUTHORITIES, FIRST_NAME, LAST_NAME, ADDRESS);
    public static final User testUser1 = new User(EMAIL, PASSWORD, AUTHORITIES, FIRST_NAME, LAST_NAME, ADDRESS);
    public static final User testUser2 = new User(EMAIL_2, PASSWORD, AUTHORITIES, FIRST_NAME_2, LAST_NAME_2, ADDRESS);;


}
