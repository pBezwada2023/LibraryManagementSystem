package edu.northeastern.lms.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 75782997496803L;
    private String id;
    private String firstName;
    private String lastName;
    private long phoneNumber;
    private String emailId;
    private UserType userType;
    private Set<Book> borrowedBooks;

    public User(String id, String fName, String lName, long phoneNumber, String emailId, UserType userType) {
        this.id = id;
        this.firstName = fName;
        this.lastName = lName;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.userType = userType;
        borrowedBooks = new LinkedHashSet<>();
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

    public Set<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Set<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public boolean checkOutBook(Book book) {
        if(book == null || borrowedBooks.contains(book))
            return false;
        borrowedBooks.add(book);
        return true;
    }

    public boolean checkInBook(Book book) {
        if(book == null || !borrowedBooks.contains(book))
            return false;
        borrowedBooks.remove(book);
        return true;
    }


}
