package org.example.constant;

import com.github.javafaker.Faker;

public class UserConstant {
    Faker faker = new Faker();

    String name = faker.name().fullName(); // Miss Samanta Schmidt
    String firstName = faker.name().firstName(); // Emory
    String lastName = faker.name().lastName(); // Barton
    public static final String LOGIN = name;
    public static final String PASSWORD = "kankretniy";
    public static final String FIRSTNAME = "maladec";
}
