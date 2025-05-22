package lk.ijse.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardPageController implements Initializable {

    public AnchorPane ancDashboard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    private void navigateTo(String path) {
        try {
            ancDashboard.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancDashboard.widthProperty());
            anchorPane.prefHeightProperty().bind(ancDashboard.heightProperty());

            ancDashboard.getChildren().add(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    public void btnGoToSupplierPageOnAction(ActionEvent actionEvent) { navigateTo("/view/SupplierPage.fxml"); }

    public void btnCustomerOnAction(ActionEvent actionEvent) { navigateTo("/view/CustomerPage.fxml"); }

    public void btnGoToItemPageOnAction(ActionEvent actionEvent) { navigateTo("/view/ItemPage.fxml"); }

    public void btnGoToEmployeePageOnAction(ActionEvent actionEvent) { navigateTo("/view/EmployeePage.fxml");
    }

    public void btnGoToPurchaseOrderPageOnAction(ActionEvent actionEvent) { navigateTo("/view/PurchaseOrderPage.fxml");}

    public void btnGoToPurchaseReportPageOnAction(ActionEvent actionEvent) {navigateTo("/view/PurchaseReportPage.fxml");}
}
