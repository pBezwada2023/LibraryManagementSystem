package edu.northeastern.lms.controllers;

import edu.northeastern.lms.entity.MenuItems;
import edu.northeastern.lms.utility.ScreenUtil;
import edu.northeastern.lms.utility.ToolTipUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import static edu.northeastern.lms.MainApplication.getPrimaryStage;

public class AdminController {
    @FXML
    public Button addUsers;

    @FXML
    private Button addBooks;

    @FXML
    private Button manageUsers;

    @FXML
    private Button manageBooks;

    @FXML
    private Button logout;

    /**
     * Shows a tooltip when the mouse is hovered over the add Users button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredAddUsersButton(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Add Users", mouseEvent, addUsers);
    }

    /**
     * Shows a tooltip when the mouse is hovered over the remove clients button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredAddBooksButton(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Add Books", mouseEvent, addBooks);
    }

    /**
     * Shows a tooltip when the mouse is hovered over the view clients button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredManageUserssButton(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Manage Users Records", mouseEvent, manageUsers);
    }

    /**
     * Shows a tooltip when the mouse is hovered over the manage account loans button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredManageBooksButton(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Manage Borrowed Books", mouseEvent, manageBooks);
    }

    /**
     * Logs out the current session.
     * Returns to the main page.
     * @param actionEvent the action event.
     */
    @FXML
    public void onLogout(ActionEvent actionEvent) {
        actionEvent.consume();
        ScreenUtil.logoutSession();
        getPrimaryStage().show();
    }

    /**
     * Shows a tooltip when mouse is hovered over the logout button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredLogout(MouseEvent mouseEvent) {
        ToolTipUtil.showToolTipOnHover("Logout Session", mouseEvent, logout);
    }

    @FXML
    public void onAddUsers(ActionEvent actionEvent) {
        actionEvent.consume();
        ScreenUtil.setPage(MenuItems.ADD_USERS);
        ScreenUtil.loadPage("admin_window", "add_users_window");
    }

    @FXML
    public void onAddBooks(ActionEvent actionEvent) {
        actionEvent.consume();
        ScreenUtil.setPage(MenuItems.ADD_BOOKS);
        ScreenUtil.loadPage("admin_window", "add_books_window");
    }
}
