package edu.northeastern.lms.controllers;

import edu.northeastern.lms.entity.Book;
import edu.northeastern.lms.entity.Category;
import edu.northeastern.lms.entity.MenuItems;
import edu.northeastern.lms.utility.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class AddBookController implements Initializable {
    private final String bookDataRepositoryPath = "src/main/data/books/";

    private static boolean isDataLoadedFromRepository = false;
    @FXML
    public TextField id;

    @FXML
    public TextField name;
    @FXML
    public TextField isbn;

    @FXML
    public TextField author;

    @FXML
    public TextField year;

    @FXML
    public TextField price;

    @FXML
    public ChoiceBox<?> category;

    @FXML
    private Button add;

    @FXML
    private Button remove;

    @FXML
    private Button removeAll;
    
    @FXML
    public TableView<Book> table;

    private boolean passed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(!isDataLoadedFromRepository) {
                loadBooks();
                isDataLoadedFromRepository = true;
            }
        } catch (Exception e) {
            System.out.println("Error while loading the books from repository" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadBooks() throws IOException, ClassNotFoundException {
        Path p = Paths.get(bookDataRepositoryPath);
        final File folder = new File(String.valueOf(p));
        List<Book> bookList = loadAllBooksFromDataFolder(folder);
        if(bookList == null || bookList.isEmpty())
            return;
        for(Book bookObj : bookList) {
            DataUtil.getBooksDataSource().add(new Book(
                    bookObj.getId(),
                    bookObj.getTitle(),
                    bookObj.getIsbn(),
                    bookObj.getPrice(),
                    bookObj.getAuthor(),
                    bookObj.getPublishedYear(),
                    bookObj.getCategory()
            ));
            ComponentUtil.initTableColumns(table, new String[]{"id", "title", "author", "category"});
            table.setItems(DataUtil.getBooksDataSource());
        }
    }

    @FXML
    public void onAdd(MouseEvent event) {
        event.consume();
        System.out.printf("Passed: %s%n", passed);
        if (passed) {

            if (Validator.isExistingBook(id.getText().trim())) {
                int publishedYear = (year.getText().isBlank() ? 0 : Integer.parseInt(year.getText().trim()));
                double bookPrice = (price.getText().isBlank() ? 0.00 : Double.parseDouble(price.getText().trim()));
                Book book = new Book(id.getText().trim(),
                                     name.getText().trim(),
                                     isbn.getText().trim(),
                                     bookPrice,
                                     author.getText().trim(),
                                     publishedYear,
                                     (Category) category.getSelectionModel().getSelectedItem()
                                    );
                    try {
                            EntityDataResolver.writeToFile(book, book.getId(), bookDataRepositoryPath);
                            DataUtil.getBooksDataSource().add(book);
                            ComponentUtil.initTableColumns(table, new String[]{"id", "title", "author", "category"});
                            table.setItems(DataUtil.getBooksDataSource());
                            TextFieldUtil.resetBookInputs(
                                    id,
                                    name,
                                    isbn,
                                    price,
                                    author,
                                    year,
                                    category
                            );
                    }catch(IOException ie) {
                        System.out.println("Error while saving books into repository" + ie.getMessage());
                        ie.printStackTrace();
                    }
            }
            else {
                String message = "Cannot add book, book id is already added";
                Tooltip tooltip  = ToolTipUtil.initToolTip(message, null, Styling.errorToolTipStyle(), id);
                id.setTooltip(tooltip);
                String window =  "add_books_window";
                id.getTooltip().show(ScreenUtil.getParent(window).getScene().getWindow());
            }
        }
    }

    @FXML
    public void onHoverAdd(MouseEvent event) {
        passed = TextFieldUtil.validateBookFields(
                add,
                event,
                id.getText(),
                name.getText(),
                price.getText(),
                author.getText(),
                year.getText()
        );
        if (passed) {
            String message =  "Add Book" ;
            ToolTipUtil.onHoverButtons(message, event, add);
        }
    }

    /**
     * Removes a book record from the table
     * @param mouseEvent the mouse event
     */
    @FXML
    public void onRemove(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Optional<Object> optional = Optional.ofNullable(table.getSelectionModel().getSelectedItem());
        try {
                optional.ifPresent(item -> {
                    Book book = (Book) item;
                    EntityDataResolver.deleteFile(bookDataRepositoryPath, book.getId());
                    if (ScreenUtil.getPage().equals(MenuItems.ADD_BOOKS)) {
                        DataUtil.getBooksDataSource().remove(item);
                        table.setItems(DataUtil.getBooksDataSource());
                    }
                });
        }catch (Exception e) {
            System.out.println("Error while deleting book" );
            e.printStackTrace();
        }
    }

    @FXML
    public void onHoverRemove(MouseEvent event) {
        String message = "Remove Book";
        ToolTipUtil.onHoverButtons(message, event, remove);
    }

    @FXML
    public void onRemoveAll(MouseEvent mouseEvent) {
        mouseEvent.consume();
        try {
            EntityDataResolver.deleteAllDataRecordsFromRepository(bookDataRepositoryPath);
            DataUtil.getAllBooks().clear();
            DataUtil.getBooksDataSource().clear();
            table.setItems(DataUtil.getBooksDataSource());
            TextFieldUtil.resetBookInputs(
                    id,
                    name,
                    isbn,
                    price,
                    author,
                    year,
                    category
            );
        } catch (Exception ex) {
            System.out.println("Error while deleting all books from repository");
            ex.printStackTrace();
        }
    }

    @FXML
    public void onHoverRemoveAll(MouseEvent event) {
        String message = "Delete all Books from the library inventory";
        ToolTipUtil.onHoverButtons(message, event, removeAll);
    }

    public List<Book> loadAllBooksFromDataFolder(final File folder) throws IOException, ClassNotFoundException {
        // LinkedList to maintain constant time of insertion
        List<Book> booksList = new LinkedList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            Book book = (Book) EntityDataResolver.readObjectFromFile(fileEntry);
            booksList.add(book);
        }
        booksList.sort(Comparator.comparing(Book::getId));
        return booksList;
    }

}
