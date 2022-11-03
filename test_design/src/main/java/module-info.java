module com.example.test_design {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.test_design to javafx.fxml;
    exports com.example.test_design;
}