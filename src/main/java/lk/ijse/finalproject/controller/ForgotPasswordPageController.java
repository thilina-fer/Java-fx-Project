package lk.ijse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class ForgotPasswordPageController {
    public AnchorPane ancForgotPasswordPage;

    private void navigateTo(String path) {
        try {
            ancForgotPasswordPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancForgotPasswordPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancForgotPasswordPage.heightProperty());

            ancForgotPasswordPage.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    public void goToOtpPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/OtpPage.fxml");
    }
}
