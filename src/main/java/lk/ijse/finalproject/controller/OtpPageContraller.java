package lk.ijse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class OtpPageContraller {
    public AnchorPane ancOtpPage;

    public void goToDashBoardPage(ActionEvent actionEvent) {
        navigateTo("/view/Dashboard.fxml");
    }
    private void navigateTo(String path) {
        try {
            ancOtpPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancOtpPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancOtpPage.heightProperty());

            ancOtpPage.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }
}
