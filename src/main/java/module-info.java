module lk.ijse.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;


    opens lk.ijse.finalproject.controller to javafx.fxml;
    opens lk.ijse.finalproject.dto.tm to javafx.base;
    exports lk.ijse.finalproject;
}