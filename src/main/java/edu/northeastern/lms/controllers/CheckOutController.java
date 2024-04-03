package edu.northeastern.lms.controllers;

import edu.northeastern.lms.entity.Book;
import edu.northeastern.lms.utility.DataUtil;
import edu.northeastern.lms.utility.ToolTipUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

public class CheckOutController {

    @FXML
    public TextField userId;
    @FXML
    public Button checkOut;

    @FXML
    public TableView<Book> availableBooks;

    @FXML
    public TableView<Book> selectedBooksForCheckout;

    @FXML
    public Button separateWindowButton;

    @FXML
    public void onAdd(MouseEvent event) {
        event.consume();
        ObservableList<Book> selectedItems = availableBooks
                .getSelectionModel()
                .getSelectedItems()
                .stream()
                .peek(book -> {
                    book.setCheckedOut(true);
                    book.setCheckedOutByUserId(userId.getText().trim());
                    book.setCheckedOutDate(now());
                };

        //TODO
        /* Add book to the borrowedBook collection in User and update the status of the book
           Delete the book repository file and create a new one. Same with the User.
         */
    }


    private void addBook(LocalDate date) {
        ObservableList<Book> selectedItems = availableBooks
                .getSelectionModel()
                .getSelectedItems()
                .stream()
                .peek(book -> {
                        book.setCheckedOutDate(now());
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        boolean error = selectedItems.stream()
                .noneMatch(books -> table.getItems().stream().noneMatch(book -> book.equals(books)));

        System.out.println("error = " + error);

        if (error) {
            Tooltip tooltip  = ToolTipUtil.initToolTip("Cannot add book, Book is already added", null, Style.errorToolTipStyle(), availableBooks);
            availableBooks.setTooltip(tooltip);
            availableBooks.getTooltip().show(WindowUtil.getParent("borrow_books_window").getScene().getWindow());
        } else {
            DataUtil.getBorrowedBooksDataSource().addAll(selectedItems);

            ComponentUtil.initTableColumns(table, new String[]{"bookId", "title", "author", "category", "dateBorrowed", "dateReturned"});

            table.setItems(DataUtil.getBorrowedBooksDataSource());
            availableBooks.getSelectionModel().clearSelection();
        }
    }

    @FXML
    public void onHoverCheckOut(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Check out a book", mouseEvent, checkOut);
    }

    @FXML
    public void onRemove(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Optional<Object> optional = Optional.ofNullable(table.getSelectionModel().getSelectedItem());
        optional.ifPresent(item -> {
            DataUtil.getBorrowedBooksDataSource().remove(item);
            table.setItems(DataUtil.getBorrowedBooksDataSource());
            table.getSelectionModel().clearSelection();
        });
    }

    @FXML
    public void onHoverRemove(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Remove a book to borrow listed on the right", mouseEvent, remove);
    }

    @FXML
    public void onRemoveAll(MouseEvent mouseEvent) {
        mouseEvent.consume();
        DataUtil.getAllBorrowedBooks().clear();
        DataUtil.getBorrowedBooksDataSource().clear();
        table.setItems(DataUtil.getBorrowedBooksDataSource());
    }

    @FXML
    public void onHoverRemoveAll(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Remove all books to borrow listed on the right", mouseEvent, removeAll);
    }

    @FXML
    public void onBorrowAll(MouseEvent mouseEvent) {
        mouseEvent.consume();
        DataUtil.saveAllBorrowedBooks();
        DataUtil.getBorrowedBooksDataSource().clear();
        table.getItems().clear();
    }

    @FXML
    public void onHoverCheckout(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Checkout all the books listed on the right", mouseEvent, borrowAll);
    }



}
