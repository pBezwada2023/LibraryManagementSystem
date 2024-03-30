package edu.northeastern.lms.controllers;

import edu.northeastern.lms.entity.User;
import edu.northeastern.lms.entity.UserType;
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
import java.util.concurrent.atomic.AtomicReference;

public class AddUserController implements Initializable {

    private final String userDataRepositoryPath = "src/main/data/users/";
    private static boolean isUserDataLoadedFromRepository = false;

    @FXML
    public TextField id;

    @FXML
    public TextField firstName;

    @FXML
    public TextField lastName;

    @FXML
    public TextField phoneNumber;

    @FXML
    public TextField emailId;

    @FXML
    public ChoiceBox<?> userType;

    @FXML
    private Button add;

    @FXML
    private Button remove;

    @FXML
    private Button removeAll;

    @FXML
    @SuppressWarnings("rawtypes")
    public TableView table;

    private boolean passed;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            if(!isUserDataLoadedFromRepository) {
                loadUsers();
                isUserDataLoadedFromRepository = true;
            }
        } catch (Exception e) {
            System.out.println("Error while loading the users from repository" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadUsers() throws IOException, ClassNotFoundException {
        Path p = Paths.get(userDataRepositoryPath);
        final File folder = new File(String.valueOf(p));
        List<User> userList = loadAllUsersFromDataFolder(folder);
        if(userList == null || userList.isEmpty())
            return;
        for(User userObj : userList) {
            DataUtil.getUserDataSource().add(new User(
                    userObj.getId(),
                    userObj.getFirstName(),
                    userObj.getLastName(),
                    userObj.getPhoneNumber(),
                    userObj.getEmailId(),
                    userObj.getUserType()
            ));
            ComponentUtil.initUserTableColumns(table, new String[]{"id", "firstName", "lastName", "phoneNumber", "emailId", "userType"});
            table.setItems(DataUtil.getUserDataSource());
        }
    }

    @SuppressWarnings("unchecked")
    public void onAdd(MouseEvent event) {
        event.consume();
        System.out.printf("Passed: %s%n", passed);
        if (passed) {
            if (Validator.isExistingUser(id.getText().trim())) {
                long phoneNo = (phoneNumber.getText().isBlank() ? 0L : Long.parseLong(phoneNumber.getText().trim()));
                User user = new User( id.getText().trim(),
                                      firstName.getText().trim(),
                                      lastName.getText().trim(),
                                      phoneNo,
                                      emailId.getText().trim(),
                                      (UserType) userType.getSelectionModel().getSelectedItem());
                try {
                    EntityDataResolver.writeToFile(user, user.getId(), userDataRepositoryPath);
                    DataUtil.getUserDataSource().add(user);
                    ComponentUtil.initUserTableColumns(table, new String[]{"id", "firstName", "lastName", "phoneNumber", "emailId", "userType"});
                    table.setItems(DataUtil.getUserDataSource());
                    TextFieldUtil.resetUserInputs(
                            id,
                            firstName,
                            lastName,
                            phoneNumber,
                            emailId,
                            userType
                    );
                }catch(IOException ie) {
                    System.out.println("Error while saving user into repository" + ie.getMessage());
                    ie.printStackTrace();
                }
            }
            else {
                String message = "Cannot add User, user with same id already added";
                Tooltip tooltip  = ToolTipUtil.initToolTip(message, null, Styling.errorToolTipStyle(), id);
                id.setTooltip(tooltip);
                String window = "add_users_window";
                id.getTooltip().show(ScreenUtil.getParent(window).getScene().getWindow());
            }
        }
    }

    @FXML
    public void onHoverAdd(MouseEvent event) {
        passed = TextFieldUtil.validateUserFields(
                add,
                event,
                id.getText(),
                firstName.getText(),
                lastName.getText(),
                phoneNumber.getText(),
                emailId.getText()
        );
        if (passed) {
            String message =  "Add Users";
            ToolTipUtil.onHoverButtons(message, event, add);
        }
    }

    /**
     * Removes a user record from the table
     * @param mouseEvent the mouse event
     */
    @FXML
    public void onRemove(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Optional<Object> optional = Optional.ofNullable(table.getSelectionModel().getSelectedItem());
        try {
                optional.ifPresent(item -> {
                        User user = (User) item;
                        EntityDataResolver.deleteFile(userDataRepositoryPath, user.getId());
                        DataUtil.getUserDataSource().remove(item);
                        table.setItems(DataUtil.getUserDataSource());
                });
        }catch (Exception e) {
            System.out.println("Error while deleting user");
            e.printStackTrace();
        }
    }

    @FXML
    public void onHoverRemove(MouseEvent event) {
        String message = "Remove User";
        ToolTipUtil.onHoverButtons(message, event, remove);

    }

    @SuppressWarnings("unchecked")
    @FXML
    public void onRemoveAll(MouseEvent mouseEvent) {
        mouseEvent.consume();
        try {
            EntityDataResolver.deleteAllDataRecordsFromRepository(userDataRepositoryPath);
            DataUtil.getAllUsers().clear();
            DataUtil.getUserDataSource().clear();
            table.setItems(DataUtil.getUserDataSource());

            TextFieldUtil.resetUserInputs(
                    id,
                    firstName,
                    lastName,
                    phoneNumber,
                    emailId,
                    userType
            );
        }catch (Exception ex) {
            System.out.println("Error while deleting all users from repository");
            ex.printStackTrace();
        }
    }

    @FXML
    public void onHoverRemoveAll(MouseEvent event) {
        String message =  "Remove All users records from the table";
        ToolTipUtil.onHoverButtons(message, event, removeAll);
    }

    public List<User> loadAllUsersFromDataFolder(final File folder) throws IOException, ClassNotFoundException {
        // LinkedList to maintain constant time of insertion
        List<User> userList = new LinkedList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            User user = (User) EntityDataResolver.readObjectFromFile(fileEntry);
            userList.add(user);
        }
        userList.sort(Comparator.comparing(User::getId));
        return userList;
    }

}
