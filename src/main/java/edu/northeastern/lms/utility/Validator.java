package edu.northeastern.lms.utility;

import edu.northeastern.lms.entity.Book;
import edu.northeastern.lms.entity.Customer;

public class Validator {

    public static boolean isExistingUser(String userId) {
        return DataUtil.getAllUsers()
                .stream()
                .map(e -> (Customer) e)
                .noneMatch(e -> e.getId().equals(userId)) && DataUtil.getUserDataSource().stream()
                .noneMatch(e -> e.getId().equals(userId));
    }

    public static boolean isExistingBook(String id) {
        return DataUtil.getAllBooks()
                .stream()
                .map(e -> (Book) e)
                .noneMatch(e -> e.getId().equals(id)) && DataUtil.getBooksDataSource().stream()
                .noneMatch(e -> e.getId().equals(id));
    }

}
