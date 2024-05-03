module com.example.assignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment to javafx.fxml;
    exports com.example.assignment;
}