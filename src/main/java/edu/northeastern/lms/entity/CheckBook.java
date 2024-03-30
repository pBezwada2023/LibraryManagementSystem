package edu.northeastern.lms.entity;

import edu.northeastern.lms.entity.Book;

public interface CheckBook {
    public boolean checkOutBook(Book book);
    public boolean checkInBook(Book book);

}
