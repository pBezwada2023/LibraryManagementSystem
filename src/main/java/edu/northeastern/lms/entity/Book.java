package edu.northeastern.lms.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 56543212345678L;
    private final String id;
    private String title;
    private String isbn;
    private double price;
    private String author;
    private int publishedYear;
    private Category category;
    private boolean checkedOut;
    private Customer checkedOutByUser;
    private Date checkedOutDate;

    public Book(String id, String title, String isbn, double price, String author, int year, Category category) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
        this.publishedYear = year;
        this.category = category;
    }

    public Book(String id, String title, String isbn, double price, String author, int year, Category category, boolean checkedOut, Customer checkedOutUser, Date checkedOutDate) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
        this.publishedYear = year;
        this.category = category;
        this.checkedOut = checkedOut;
        this.checkedOutByUser = checkedOutUser;
        this.checkedOutDate = checkedOutDate;
    }

    /*
        No setter for Id, has to be final.
     */
    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public Customer getCheckedOutByUser() {
        return checkedOutByUser;
    }

    public void setCheckedOutByUser(Customer customer) {
        this.checkedOutByUser = customer;
    }

    public Date getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(Date checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Book or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Book)) {
            return false;
        }

        // typecast o to Book so that we can compare data members
        Book b = (Book) o;
        // Only checking for Id, as other book attributes could be updated
        return this.id.equals(b.getId());
    }

    public int hashCode() {
        // Only using  Id for generating hashCode, as other book attributes could be updated
        final int PRIME = 17;
        int result = 1;
        final Object $bookId = id;
        result = result * PRIME + ($bookId == null ? 43 : $bookId.hashCode());
        return result;
    }



}
