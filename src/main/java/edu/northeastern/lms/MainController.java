package edu.northeastern.lms;

import edu.northeastern.lms.books.LMSController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onStartButtonClick() throws IOException {
        LMSController.changeScene();
    }
}