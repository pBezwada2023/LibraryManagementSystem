package edu.northeastern.lms.entity;

import java.util.*;

public class Customer extends User implements CheckBook {
    private final Set<Book> borrowedBooks;
    private boolean validityStatus ;

    public boolean isValidityStatus() {
        return validityStatus;
    }

    public void setValidityStatus(boolean validityStatus) {
        this.validityStatus = validityStatus;
    }

    public Set<Book> getBorrowedBooks() {
        return this.borrowedBooks;
    }

    public Customer(String id, String fName, String lName, long phoneNumber, String email,boolean status) {
        super(id, fName, lName, phoneNumber, email, UserType.Customer);
        this.borrowedBooks = new LinkedHashSet<>();
        this.validityStatus = status;
        this.setUserType(UserType.Customer);
    }

    @Override
    public boolean checkOutBook(Book book) {
        if(book == null)
            return false;
        borrowedBooks.add(book);
        return true;
//        book.setCheckedOutDate(new Date());
//        book.setCheckedOutByUser(this);
//        book.setCheckedOut(true);
//        save(book);
    }

    @Override
    public boolean checkInBook(Book book) {
        if(book == null || !borrowedBooks.contains(book))
            return false;
        borrowedBooks.remove(book);
        return true;
//                book.setCheckedOut(false);
//                book.setCheckedOutByUser(null);
//                book.setCheckedOutDate(null);
//                save(book);
    }

}
