package lk.ijse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.PurchaseReportDto;
import lk.ijse.finalproject.dto.tm.CustomerTm;
import lk.ijse.finalproject.dto.tm.PurchaseReportTm;
import lk.ijse.finalproject.model.PurchaseReportModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchaseReportPageController implements Initializable {

    public Label lblReportId;
    public ComboBox comboOrderId;
    public TextField txtDescription;

    public TableView<PurchaseReportTm> tblReportOrder;
    public TableColumn<PurchaseReportTm , String> colReportId;
    public TableColumn<PurchaseReportTm , String> colOrderId;
    public TableColumn<PurchaseReportTm , String> colDescriptionId;

    private final PurchaseReportModel purchaseReportModel = new PurchaseReportModel();
    public TextField searchField;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public AnchorPane ancReportPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colReportId.setCellValueFactory(new PropertyValueFactory<>("reportId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDescriptionId.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }
    public void loadTableData() throws Exception {
        tblReportOrder.setItems(FXCollections.observableArrayList(
                purchaseReportModel.getAllReports().stream()
                        .map(purchaseReportDto -> new PurchaseReportTm(
                                purchaseReportDto.getReportId(),
                                purchaseReportDto.getOrderId(),
                                purchaseReportDto.getDescription()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTableData();
            loadOrderDetails();
            loadNextId();

            btnSave.setDisable(true);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            comboOrderId.getSelectionModel().clearSelection();
            txtDescription.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wronng").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String reportId = lblReportId.getText();
        String orderId = (String) comboOrderId.getValue();
        String description = txtDescription.getText();

        PurchaseReportDto purchaseReportDto = new PurchaseReportDto(
                reportId,
                orderId,
                description
        );

        try {
            boolean isSaved = purchaseReportModel.saveReport(purchaseReportDto);

            if (isSaved){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Save Failed");
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String reportId = lblReportId.getText();
        String orderId = (String) comboOrderId.getValue();
        String description = txtDescription.getText();

        PurchaseReportDto purchaseReportDto = new PurchaseReportDto(
                reportId,
                orderId,
                description
        );

        try {
            boolean isUpdated = purchaseReportModel.updateReport(purchaseReportDto);

            if (isUpdated){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Update Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Update Failed");
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.YES) {
            String reportId = lblReportId.getText();

            try {
                boolean isDeleted = purchaseReportModel.deleteReport(reportId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Delete Successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }
    }



    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PurchaseReportTm selectedItem = tblReportOrder.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            lblReportId.setText(selectedItem.getReportId());
            comboOrderId.getSelectionModel().select(selectedItem.getOrderId());
            txtDescription.setText(selectedItem.getDescription());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void loadNextId() throws Exception {
        String nextId = purchaseReportModel.getNextReportId();
        lblReportId.setText(nextId);
    }

    private void navigateTo(String path) {
        try {
            ancReportPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancReportPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancReportPage.heightProperty());

            ancReportPage.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) {
        navigateTo("/view/Dashboard.fxml");
    }

    public void search(KeyEvent keyEvent) {
        String searchText = searchField.getText();
        if (searchText.isEmpty()){
            try {
                loadTableData();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Faild to load Reports").show();
            }
        }else {
            try {
                ArrayList<PurchaseReportDto> reportList = purchaseReportModel.searchReport(searchText);
                tblReportOrder.setItems(FXCollections.observableArrayList(
                        reportList.stream()
                                .map(purchaseReportDto -> new PurchaseReportTm(
                                        purchaseReportDto.getReportId(),
                                        purchaseReportDto.getOrderId(),
                                        purchaseReportDto.getDescription()
                                )).toList()
                ));
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search customers").show();
            }
        }
    }


    public void goToSaveSupplierPage(MouseEvent mouseEvent) {
        navigateTo("/view/SupplierOrderPage.fxml");
    }
    private void loadOrderDetails() throws SQLException, ClassNotFoundException {
        comboOrderId.setItems(FXCollections.observableArrayList(purchaseReportModel.getAllOrderDetails()));
    }

}
