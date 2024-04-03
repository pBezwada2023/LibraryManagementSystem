package edu.northeastern.lms;

import edu.northeastern.lms.entity.Category;
import edu.northeastern.lms.entity.UserType;
import edu.northeastern.lms.utility.ComponentUtil;
import edu.northeastern.lms.utility.ScreenUtil;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class MainApplication extends Application {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        initParents();
        Parent parent = ScreenUtil.getParent("main_window");
        Scene scene = new Scene(parent);

        MainApplication.primaryStage = stage;

        ScreenUtil.loadParent(parent, "Library Management System", true);

        getPrimaryStage().initStyle(StageStyle.DECORATED);

        getPrimaryStage().getIcons().add(new Image(requireNonNull(getClass().getResourceAsStream("images/library.png"), "logo not found")));
        getPrimaryStage().setScene(scene);

        ComponentUtil.getChoiceBox(ScreenUtil.getParent("add_users_window"), "userType")
                .map(e -> (ChoiceBox<UserType>) e)
                .ifPresentOrElse(e -> {
                            e.getItems().addAll(FXCollections.observableArrayList(Arrays.stream(UserType.values()).collect(Collectors.toList())));
                            e.getSelectionModel().selectFirst();
                        }, () -> System.out.println("User Type choice box not found")
                );

        ComponentUtil.getChoiceBox(ScreenUtil.getParent("add_books_window"), "category")
                .map(e -> (ChoiceBox<Category>) e)
                .ifPresent(MainApplication::initCategory);

//        ComponentUtil.getChoiceBox(ScreenUtil.getParent("borrow_books_window"), "choiceBox")
//                .map(e -> (ChoiceBox<Category>) e)
//                .ifPresent(MainApplication::initCategory);

        getPrimaryStage().show();
        System.out.println("Application started");
    }

    private static void initCategory(ChoiceBox<Category> borrowBookChoiceBox) {
        borrowBookChoiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.stream(Category.values()).collect(Collectors.toList())));
        borrowBookChoiceBox.getSelectionModel().selectFirst();
    }

    private void initParents() throws IOException {
        Parent mainPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/mainPage.fxml"), "Cannot find mainPage.fxml"));
        Parent adminPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/admin/adminPage.fxml"), "Cannot find adminPage.fxml"));
        Parent addUsersPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/admin/addUsers.fxml"), "Cannot find addUsers.fxml"));
        Parent addBooksPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/admin/addBooks.fxml"), "Cannot find addBooks.fxml"));
       // Parent userPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/user/userPage.fxml"), "Cannot find UserPage.fxml"));
        Parent checkOutBookPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/user/borrowBook/checkoutBooks.fxml"), "Cannot find borrowBook.fxml"));
        //Parent listOfBorrowedBooksPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/user/viewList/listOfBorrowedBooks.fxml"), "Cannot find listOfBorrowedBooks.fxml"));
        //Parent promptPage = FXMLLoader.load(requireNonNull(getClass().getResource("fxml/prompt.fxml"), "Cannot find prompt.fxml"));

        adminPage.setId("admin_window");
        mainPage.setId("main_window");
        addUsersPage.setId("add_users_window");
        addBooksPage.setId("add_books_window");
//        userPage.setId("user_window");
//        borrowBookPage.setId("borrow_books_window");
//        listOfBorrowedBooksPage.setId("list_of_borrowed_books_window");
//        promptPage.setId("promptPage_page");

        ScreenUtil.addParents.accept(List.of
                (
                        mainPage,
                        adminPage,
                        addUsersPage,
                        addBooksPage
//                        userPage,
//                        borrowBookPage,
//                        listOfBorrowedBooksPage,
//                        promptPage
                )
        );
    }

    public static void main(String[] args) {
        launch();
    }

}