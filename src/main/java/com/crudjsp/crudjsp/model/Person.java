package com.crudjsp.crudjsp.model;

import lombok.Data;

import java.sql.Date;
@Data
public class Person {
    private int personId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private String typeDocument;
    private String numberDocument;
    private String gender;
    private String email;
    private char active;

}
