package edu.northeastern.lms.books;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class EditBook {
    private String bookSelected;

    public String getResult() {
        return this.bookSelected;
    }

    public EditBook() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Book");
        dialog.setHeaderText("Edit book");

        ButtonType confirm = new ButtonType("Edit");
        dialog.getDialogPane().getButtonTypes().add(confirm);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField author = new TextField();
        author.setPromptText("Author");

        TextField title = new TextField();
        title.setPromptText("Title");

        TextField isbn = new TextField();
        isbn.setPromptText("ISBN");

        TextField category = new TextField();
        category.setPromptText("Category");

        TextArea description = new TextArea();
        description.setPromptText("Description");

        TextField year = new TextField();
        year.setPromptText("Year");

        TextField price = new TextField();
        price.setPromptText("Price");


        grid.add(new Label("Author:"), 0, 0);
        grid.add(author, 1, 0);

        grid.add(new Label("Title:"), 0, 1);
        grid.add(title, 1, 1);

        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(isbn, 1, 2);

//        grid.add(new Label("Category:"), 0, 3);
//        grid.add(category, 1, 3);
//
//        grid.add(new Label("Description:"), 0, 4);
//        grid.add(description, 1, 4);
//
//        grid.add(new Label("Year:"), 0, 5);
//        grid.add(year, 1, 5);
//
//        grid.add(new Label("Price:"), 0, 6);
//        grid.add(price, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirm) {
                return author.getText() + ";" + title.getText() + ";" + isbn.getText() + ";" + category.getText() +
                        ";" + description.getText() + year.getText() + ";" + price.getText();
            }
            return null;
        });

        Optional<String> rslt = dialog.showAndWait();
        if (rslt.isPresent() ) {
            this.bookSelected = rslt.get();
        }
        else this.bookSelected = null;
    }
}
