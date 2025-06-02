package lk.ijse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.ijse.finalproject.db.DBConnection;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ForgetGetUsernameEmailController {
    public TextField txtUsername;
    public TextField txtEmail;
    public Label lblForgetPassword;

    public static int OTP;
    public static String Username;
    public ObservableList<String> obList;
    public Label lblCheckUsername;

   /* public void initialize() {
       // animateLabelTyping();
        getUsernames();
    }

    public void btnForEmailOnAction(ActionEvent event) {
        String username = txtUsername.getText();

        try {
            String isCheckedEmail = ForgetGetUsernameEmailController.checkEmail(username);
            if (isCheckedEmail != null){
                txtEmail.setText(isCheckedEmail);
                btnSendOtpOnAction(event);
            }else {
                txtEmail.clear();
                new Alert(Alert.AlertType.ERROR, "Can't find Email..." , ButtonType.OK).show();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static String  checkEmail(String username) throws SQLException {

        ResultSet resultSet = CrudUtil.execute("SELECT email FROM user WHERE user_name = ?", username);
        if (resultSet.next()) {
            return resultSet.getString("email");
        }
        return null;
    }

    void  txtUsernameOnKeyReleased(KeyEvent event) {
        boolean isValid = false;

        for (String s : obList) {
            if (s.equals(txtUsername.getText())) {
                isValid = true;
                break;
            }
        }
        if (isValid) {
            lblCheckUsername.setText("Username is valid");
            lblCheckUsername.setStyle("-fx-text-fill: green");
        }else {
            lblCheckUsername.setText("Username is invalid");
            lblCheckUsername.setStyle("-fx-text-fill: red");
        }
    }

    private void getUsernames() {
        obList = FXCollections.observableArrayList();

        try {
            List<String> NoList = ForgetGetUsernameEmailController.getUser();
            for (String No : NoList) {
                obList.add(No);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static List<String> getUser() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT user_name FROM user");
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }
    public void btnSendOtpOnAction(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String username = txtUsername.getText();

        if (username != null){
            Random random = new Random();
            int otp = 1000 + random.nextInt(9000);

            boolean sendingOTP = JavaMailUtil.sendMail(email, otp);
        }
    }

    public void hyperOnActionLogin(ActionEvent actionEvent) {
    }*/
}
