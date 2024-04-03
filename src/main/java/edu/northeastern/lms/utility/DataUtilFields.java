package edu.northeastern.lms.utility;

import edu.northeastern.lms.entity.Book;
import edu.northeastern.lms.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class DataUtilFields {
    static List<User> userList = new ArrayList<>();
    static List<Book> booksList = new ArrayList<>();
    static List<Book> borowedBooksList = new ArrayList<>();
    static Queue<Button> activeButtons = new LinkedList<>();
    static ObservableList<User> userDataSource = FXCollections.observableArrayList();
    static ObservableList<Book> booksDataSource = FXCollections.observableArrayList();
    static ObservableList<Book> availableBooksDataSource = FXCollections.observableArrayList();
    static ObservableList<Book> borrowBooksDataSource = FXCollections.observableArrayList();
}