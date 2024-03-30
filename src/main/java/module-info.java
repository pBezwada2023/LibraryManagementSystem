module edu.northeastern.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens edu.northeastern.lms.controllers to javafx.fxml;
    opens edu.northeastern.lms.entity to javafx.base;
    exports edu.northeastern.lms;
}