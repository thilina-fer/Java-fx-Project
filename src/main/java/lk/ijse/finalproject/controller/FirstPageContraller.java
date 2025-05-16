package lk.ijse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class FirstPageContraller {
    public void btnOnActionGoToSignInPage(ActionEvent actionEvent) {
        try {
            Parent dashBoardRoot = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            Stage dashBoardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            dashBoardStage.setScene(new Scene(dashBoardRoot));
            dashBoardStage.setTitle("Alpha Modifications");
            dashBoardStage.setResizable(true);
            dashBoardStage.show();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SignUp Failed", ButtonType.OK).show();
        }
    }

    public void btnOnActionGoToSignUpPage(ActionEvent actionEvent) {
        try {
            Parent dashBoardRoot = FXMLLoader.load(getClass().getResource("/view/SignUpPage.fxml"));
            Stage dashBoardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            dashBoardStage.setScene(new Scene(dashBoardRoot));
            dashBoardStage.setTitle("Alpha Modifications");
            dashBoardStage.setResizable(true);
            dashBoardStage.show();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SignUp Failed", ButtonType.OK).show();
        }
    }
}
