module lk.ijse.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires   lombok;
    requires mysql.connector.j;
    requires com.google.protobuf;


    opens lk.ijse.finalproject.controller to javafx.fxml;
    opens lk.ijse.finalproject.dto.tm to javafx.base;
    opens lk.ijse.finalproject.dto to javafx.base;

    exports lk.ijse.finalproject;
}