module edu.northeastern.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;

    opens edu.northeastern.lms to javafx.fxml;
    exports edu.northeastern.lms;
    exports edu.northeastern.lms.books;
    opens edu.northeastern.lms.books to javafx.fxml;
}