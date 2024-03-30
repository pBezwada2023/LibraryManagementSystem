package edu.northeastern.lms.utility;



import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

import java.util.Optional;


public interface TextFieldUtil {

    /**
     * Adds a limit to the specified text field.
     * @param textField the text field to add the limit.
     * @param maxLength the maximum length of the text field.
     */
    static void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((textField.getText().length() > maxLength)) {
                String limitedInput = textField.getText().substring(0, maxLength);
                if (!limitedInput.equals(DataUtil.$admin.substring(0, maxLength))) textField.setText(limitedInput);
            }
        });
    }

    static boolean validateUserFields(
            Button button,
            MouseEvent event,
            String id,
            String firstName,
            String lastName,
            String phoneNumber,
            String emailId
    ) {
        Optional<Tooltip> tooltip1 = Optional.ofNullable(button.getTooltip());
        tooltip1.ifPresent(t -> button.setTooltip(null));
        if (id.isBlank() || firstName.isBlank() || lastName.isBlank()
                        || phoneNumber.isBlank()  || emailId.isBlank()) {
            String message = "Cannot Add User, mandatory fields are empty" ;
            ToolTipUtil.checkInputsToolTip(message, event, Styling.errorToolTipStyle(), button);
            return false;
        }
        return true;
    }

    static boolean validateBookFields(
            Button button,
            MouseEvent event,
            String id,
            String name,
            String price,
            String author,
            String year
    ) {
        Optional<Tooltip> tooltip1 = Optional.ofNullable(button.getTooltip());
        tooltip1.ifPresent(t -> button.setTooltip(null));
        if (id.isBlank() || name.isBlank() || price.isBlank()
                || author.isBlank() || year.isBlank()) {
            String message = "Cannot Add Books, some of the mandatory fields are empty";
            ToolTipUtil.checkInputsToolTip(message, event, Styling.errorToolTipStyle(), button);
            return false;
        }
        return true;
    }

    static void resetBookInputs(
            TextField id,
            TextField name,
            TextField isbn,
            TextField price,
            TextField author,
            TextField year,
            ChoiceBox<?> category
    ) {
        id.clear();
        name.clear();
        isbn.clear();
        price.clear();
        author.clear();
        year.clear();
        category.getSelectionModel().selectFirst();
    }
    static void resetUserInputs(
            TextField id,
            TextField firstName,
            TextField lastName,
            TextField phone,
            TextField email,
            ChoiceBox<?> userType
    ) {
        id.clear();
        firstName.clear();
        lastName.clear();
        phone.clear();
        email.clear();
        userType.getSelectionModel().selectFirst();
    }

}
