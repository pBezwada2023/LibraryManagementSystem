package edu.northeastern.lms.utility;

import edu.northeastern.lms.entity.Book;
import edu.northeastern.lms.entity.User;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.IntStream;

public interface DataUtil {
    /**
     * admin credentials.
     */
    String $admin = decrypt("QWRtaW5ORVUhMTIz");
    int MAX_LENGTH = 10;


    static ObservableList<User> getUserDataSource() {
        return DataUtilFields.userDataSource;
    }

    static ObservableList<Book> getBooksDataSource() {
        return DataUtilFields.booksDataSource;
    }

    static List<User> getAllUsers() {
        return DataUtilFields.userList;
    }

    static List<Book> getAllBooks() {
        return DataUtilFields.booksList;
    }

    static String decrypt(String data) throws IllegalArgumentException {
        if (data.trim().isEmpty()) throw new IllegalArgumentException("Text to be decrypted cannot be empty");
        byte[] b = Base64.getDecoder().decode(data);
        return IntStream.range(0, b.length).map(i -> b[i]).mapToObj(Character::toString).reduce("", String::concat);
    }

    static ObservableList<? super Book> getAvailableBooksDataSource() {
        return DataUtilFields.availableBooksDataSource;
    }

    static ObservableList<Book> getBorrowedBooksDataSource() {
        return DataUtilFields.borrowBooksDataSource;
    }

    static void saveAllBorrowedBooks() {
        DataUtilFields.borowedBooksList = new ArrayList<>(getBorrowedBooksDataSource());
    }

    static List<Book> getAllBorrowedBooks() {
        return DataUtilFields.borowedBooksList;
    }

//    static void setAvailableBooksData(TableView tableView, ChoiceBox<Category> objectChoiceBox) {
//        getAvailableBooksDataSource().clear();
//        Category selectedItem = objectChoiceBox.getSelectionModel().getSelectedItem();
//        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        getAvailableBooksDataSource().addAll(ComponentUtil.getBooksByCategory(selectedItem));
//        ComponentUtil.initTableColumns(tableView, new String[]{"id", "title", "author", "category"});
//        tableView.setItems(getAvailableBooksDataSource());
//    }
}
