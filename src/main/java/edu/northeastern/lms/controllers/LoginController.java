package edu.northeastern.lms.controllers;


import edu.northeastern.lms.utility.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;



public class LoginController {




    @FXML
    private PasswordField credentialField;

    @FXML
    private Label message;

    private Stage stage;

    /**
     * Checks if the Enter key is pressed and invokes the check() method.
     * @param keyEvent the key event.
     */
    @FXML
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            credentialField.setVisible(false);
            checkCredential();
        }
    }

    /**
     * Checks if the account number entered exists in the database as an account.
     */
    @FXML
    private void checkCredential() {
        final String fieldText = credentialField.getText();
        System.out.printf("Credential: %s%n", DataUtil.$admin);
        checker(fieldText);
    }

    private void checker(String fieldText) {
        if (fieldText.equals(DataUtil.$admin)) {
            ScreenUtil.loadParent(ScreenUtil.getParent("admin_window"), "Administrator", false);
            credentialField.clear();
            credentialField.setVisible(true);
        }
        else {
            try {
                final boolean doesAccountExist = Validator.isExistingUser(fieldText);
                if (doesAccountExist) {
                    ScreenUtil.loadParent(ScreenUtil.getParent("user_window"), "User", false);
                    credentialField.clear();
                } else {
                    message.setText("Account does not exist");
                }
            } catch (RuntimeException runtimeException) {
                message.setText(runtimeException.getMessage());
                System.out.println(runtimeException.getMessage());
                credentialField.setVisible(true);
            }
        }
        credentialField.setVisible(true);
    }

    /**
     * Checks if the credential field is empty, then clears the message label.
     * @param keyEvent the key event
     */
    @FXML
    public void onKeyTyped(KeyEvent keyEvent) {
        TextFieldUtil.addTextLimiter(credentialField, DataUtil.MAX_LENGTH);
        if (credentialField.getText().isEmpty() && keyEvent.getCode() != KeyCode.ENTER) {
            message.setText("");
        }
    }

    /**
     * Shows the tooltip when the mouse is hovered over the credential field. and if th field is empty.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEntered(MouseEvent mouseEvent) {
        if (stage != null) stage = (Stage) credentialField.getScene().getWindow();
        final String $an = credentialField.getText().trim();
        final Optional<Tooltip> optionalTooltip = Optional.ofNullable(credentialField.getTooltip());
        if ($an.isEmpty()) {
            if (optionalTooltip.isEmpty()) {
                Tooltip toolTip = getFieldToolTip(mouseEvent);
                credentialField.setTooltip(toolTip);
            }
        } else if (optionalTooltip.isPresent()) {
            credentialField.setTooltip(null);
        }
    }

    /**
     * Creates a simple tooltip when mouse is hovered over the credentials field.
     * @param mouseEvent the mouse event.
     * @return a tooltip.
     */
    @FXML
    private static Tooltip getFieldToolTip(MouseEvent mouseEvent) {
        return ToolTipUtil.initToolTip(
                "Enter your credentials",
                mouseEvent,
                Styling.errorToolTipStyle(),
                null
        );
    }
}

