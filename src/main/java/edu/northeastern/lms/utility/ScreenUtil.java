package edu.northeastern.lms.utility;

import edu.northeastern.lms.entity.MenuItems;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static edu.northeastern.lms.MainApplication.getPrimaryStage;
import static java.lang.String.format;

public interface ScreenUtil {

    /**
     * Add a list parent to the parents array.
     * takes an array of parents
     */
    Consumer<List<Parent>> addParents = ScreenFields.parents::addAll;

    EventHandler<KeyEvent> eventHandler = event -> {
        if (KeyCode.F11.equals(event.getCode())) getPrimaryStage().setFullScreen(!getPrimaryStage().isFullScreen());
    };

    /**
     * Gets the parent with the specified id.
     * @param id the id of the parent.
     * @return the parent with the specified id.
     */
    static Parent getParent(String id) {
        return ScreenFields.parents.stream()
                .filter(parent -> parent.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(format("Cannot find parent with [%s] id", id)));
    }

    /**
     * Sets the center border pane to the specific window.
     * @param parentId the parent id.
     * @param windowToCenter the page id to be put in the center pane.
     */
    static void loadPage(String parentId, String windowToCenter) {
        ((BorderPane)(getParent(parentId)))
                .setCenter(getParent(windowToCenter));
    }

    static void loadParent(Parent parent, String stageTitle, boolean isMainWindow) {
        if (parent.getScene() != null)
            getPrimaryStage().setScene(parent.getScene()); // if scene is present, get it
        else getPrimaryStage().setScene(new Scene(parent)); // create new scene if new login

        getPrimaryStage().setTitle(Optional.ofNullable(getPrimaryStage().getTitle()).orElse(stageTitle));

        getPrimaryStage().centerOnScreen();

        getPrimaryStage().addEventHandler(KeyEvent.KEY_PRESSED, eventHandler);
        getPrimaryStage().toFront();
       // if (!isMainWindow) loadPage(parent.getId(), "promptPage_page");
    }

    static void setPage(MenuItems menuItem) {
        ScreenFields.page = menuItem;
    }

    static MenuItems getPage() {
        return ScreenFields.page;
    }

    static void logoutSession() {
        Parent mainWindow = getParent("main_window");
        loadParent(mainWindow, "Library Management System", true);
        ComponentUtil.getLabel(mainWindow, "message").ifPresent(label -> label.setText(""));
    }
}

class ScreenFields {
    static MenuItems page;

    /**
     * The parents array.
     */
    static List<Parent> parents = new ArrayList<>();
}
