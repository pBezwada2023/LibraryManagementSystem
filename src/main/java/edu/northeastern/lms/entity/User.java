package edu.northeastern.lms.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 75782997496803L;
    private String id;
    private String firstName;
    private String lastName;
    private long phoneNumber;
    private String emailId;
    private UserType userType;

    public User(String id, String fName, String lName, long phoneNumber, String emailId, UserType userType) {
        this.id = id;
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.userType = userType;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setUserType(UserType type) {
        this.userType = type;
    }

    public UserType getUserType() {
        return userType;
    }
}
