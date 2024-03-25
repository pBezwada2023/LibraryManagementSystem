package edu.northeastern.lms.books;

import edu.northeastern.lms.MainApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LMSController implements Initializable {

    final private String delimiter = "||::||";
    @FXML
    private ListView<String> booksList;

    @FXML
    private TextField author;

    @FXML
    private TextField book;

    @FXML
    private TextField isbn;

    @FXML
    private TextField category;

    @FXML
    private TextArea description;

    @FXML
    private TextField year;

    @FXML
    private TextField price;

    @FXML
    private TextField search;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadBooks();
        } catch (IOException e) {
            System.out.println("Error while loading the LMS" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void searchBook() throws IOException {

        String search_text = search.getText().strip().toLowerCase();
        loadBooks();
        int size = booksList.getItems().size();
        if (search_text.length() >= 3) {
            List<String> results = new ArrayList<>(size);

            for(String book: booksList.getItems()) {
                if (book.toLowerCase().contains(search_text)) results.add(book);
            }

            booksList.getItems().clear();
            if (results.size() > 0) {
                for(String found_book: results) booksList.getItems().add(found_book);
                booksList.refresh();
            }
        }
    }

    @FXML
    protected void editBook() throws IOException {
        ObservableList<Integer> selectedIndices = booksList.getSelectionModel().getSelectedIndices();

        if (selectedIndices.size() == 1) {
            String bookToEdit = booksList.getItems().get(selectedIndices.get(0));
            String oldIsbn = bookToEdit.split(delimiter)[2];


            EditBook eb = new EditBook();
            String str = eb.getResult();

            if (str != null) {
                Path p = Paths.get("src/main/data/" + oldIsbn + ".txt");

                File fileToDelete = new File(p.toString());
                fileToDelete.delete();



                Path p2 = Paths.get("src/main/data/" + str.split(delimiter)[2] + ".txt");
                File newFile = new File(p2.toString());

                if(newFile.createNewFile()) {
                    FileWriter myWriter = new FileWriter(String.valueOf(p2));
                    myWriter.write(str);
                    myWriter.close();
                }

                loadBooks();
                search.setText("");
            }
        }
    }

    @FXML
    protected void deleteBook() throws IOException {
        ObservableList<Integer> selectedIndices = booksList.getSelectionModel().getSelectedIndices();

        if (selectedIndices.size() == 1) {
            String bookToEdit = booksList.getItems().get(selectedIndices.get(0));
            String oldIsbn = bookToEdit.split(";")[2];
            Path p = Paths.get("src/main/data/" + oldIsbn + ".txt");
            File fileToDelete = new File(p.toString());
            fileToDelete.delete();

            loadBooks();
            search.setText("");
        }
    }

    @FXML
    protected void addItem() throws IOException {

        String author_text = author.getText();
        String book_text = book.getText();
        String isbn_text = isbn.getText();
        String category_text = category.getText();
        String description_text = description.getText();
        String yearPublished_text = year.getText();
        String price_text = price.getText();

        StringBuilder sb = new StringBuilder();
        sb.append(author_text);
        sb.append(delimiter);
        sb.append(book_text);
        sb.append(delimiter);
        sb.append(isbn_text);
        sb.append(delimiter);
        sb.append(category_text);
        sb.append(delimiter);
        sb.append(description_text);
        sb.append(delimiter);
        sb.append(yearPublished_text);
        sb.append(delimiter);
        sb.append(price_text);

        String data = new String(sb);
        Path p = Paths.get("src/main/data/" + isbn_text + ".txt");
        File myObj = new File(String.valueOf(p));
        if(myObj.createNewFile()) {
            FileWriter myWriter = new FileWriter(String.valueOf(p));
            myWriter.write(data);
            myWriter.close();
        }

        author.setText("");
        book.setText("");
        isbn.setText("");
        category.setText("");
        description.setText("");
        year.setText("");
        price.setText("");
        this.loadBooks();
    }

    public static List<String> listFilesForFolder(final File folder) throws IOException {
        List<String> al = new LinkedList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            String read = Files.readAllLines(Paths.get(fileEntry.getPath())).get(0);
            al.add(read.strip());
        }
        return al;
    }

    public void loadBooks() throws IOException {
        Path p = Paths.get("src/main/data");
        final File folder = new File(String.valueOf(p));
        List<String> al = listFilesForFolder(folder);
        booksList.getItems().clear();
        for(String book: al) {
            booksList.getItems().add(book);
        }
        booksList.refresh();
    }


    public static void changeScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("library.fxml"));
        Scene scene = new Scene(fxmlLoader.load()); // scene

        Stage stage = MainApplication.getPrimaryStage();
        stage.hide();
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }
}
