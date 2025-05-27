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
import lk.ijse.finalproject.dto.PreOrderDto;
import lk.ijse.finalproject.dto.PurchasePaymentDto;
import lk.ijse.finalproject.dto.tm.PreOrderTm;
import lk.ijse.finalproject.dto.tm.PurchasePaymentTm;
import lk.ijse.finalproject.model.PurchasePaymentModel;
import lk.ijse.finalproject.util.CrudUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchasePaymentController implements Initializable {
    public TextField searchField;
    public Label lblPaymentId;
    public ComboBox comboOrderId;
    public ComboBox comboType;
    public TextField txtDate;
    public TextField txtTime;
    public TextField txtAmount;

    public TableView<PurchasePaymentTm> tblPayment;
    public TableColumn<PurchasePaymentTm , String> colPaymentId;
    public TableColumn<PurchasePaymentTm , String> colOrderId;
    public TableColumn<PurchasePaymentTm , String> colPaymentType;
    public TableColumn<PurchasePaymentTm , String> colDate;
    public TableColumn<PurchasePaymentTm , String> colTime;
    public TableColumn<PurchasePaymentTm , Double> colAmount;
    public AnchorPane ancPurchasePaymentsPage;

    PurchasePaymentModel purchasePaymentModel = new PurchasePaymentModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("paymentTime"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void loaddTableData() throws SQLException {
        tblPayment.setItems(FXCollections.observableArrayList(
                purchasePaymentModel.getAllPurchasePayments().stream()
                        .map(purchasePaymentDto -> new PurchasePaymentTm(
                                purchasePaymentDto.getPaymentId(),
                                purchasePaymentDto.getOrderId(),
                                purchasePaymentDto.getPaymentType(),
                                purchasePaymentDto.getPaymentDate(),
                                purchasePaymentDto.getPaymentTime(),
                                purchasePaymentDto.getAmount()
                        )).toList()
        ));

    }

    private void resetPage() throws SQLException {
        try {

            loaddTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            comboOrderId.getItems().clear();
            comboType.getItems().clear();

            comboOrderId.getItems().addAll(purchasePaymentModel.getAllOrderDetails());
            comboType.getItems().addAll("Cash", "Card", "Online");

            txtDate.clear();
            txtTime.clear();
            txtAmount.clear();
        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String orderId = (String) comboOrderId.getValue();
        String paymentType = (String) comboType.getValue();
        String paymentDate = (String) txtDate.getText();
        String paymentTime = (String) txtTime.getText();
        String amount = (String) txtAmount.getText();

        PurchasePaymentDto purchasePaymentDto = new PurchasePaymentDto(
                paymentId,
                orderId,
                paymentType,
                paymentDate,
                paymentTime,
                Double.parseDouble(amount)
        );

        try {
            boolean isSaved = purchasePaymentModel.savePayment(purchasePaymentDto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment saved successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String orderId = (String) comboOrderId.getValue();
        String paymentType = (String) comboType.getValue();
        String paymentDate = (String) txtDate.getText();
        String paymentTime = (String) txtTime.getText();
        String amount = (String) txtAmount.getText();

        PurchasePaymentDto purchasePaymentDto = new PurchasePaymentDto(
                paymentId,
                orderId,
                paymentType,
                paymentDate,
                paymentTime,
                Double.parseDouble(amount)
        );

        try {
            boolean isUpdated = purchasePaymentModel.updatePayment(purchasePaymentDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Payment updated successfully").show();
                resetPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update payment").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this payment?",
                ButtonType.YES,
                ButtonType.NO
                );
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String paymentId = lblPaymentId.getText();
            try {
                boolean isDeleted = purchasePaymentModel.deletePayment(paymentId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Payment deleted successfully").show();
                    resetPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete payment").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException {
        resetPage();
    }

    public void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = purchasePaymentModel.getNextPurchasePaymentId();
        lblPaymentId.setText(nextId);
    }
    private void navigateTo(String path) {
        try {
            ancPurchasePaymentsPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancPurchasePaymentsPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPurchasePaymentsPage.heightProperty());

            ancPurchasePaymentsPage.getChildren().add(anchorPane);

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
                loaddTableData();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Faild to load Payments").show();
            }
        }else {
            try {
                ArrayList<PurchasePaymentDto> paymentList = purchasePaymentModel.searchPurchasePayment(searchText);
                tblPayment.setItems(FXCollections.observableArrayList(
                        paymentList.stream()
                                .map(purchasePaymentDto -> new PurchasePaymentTm(
                                        purchasePaymentDto.getPaymentId(),
                                        purchasePaymentDto.getOrderId(),
                                        purchasePaymentDto.getPaymentType(),
                                        purchasePaymentDto.getPaymentDate(),
                                        purchasePaymentDto.getPaymentTime(),
                                        purchasePaymentDto.getAmount()
                                )).toList()
                ));
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search payments").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PurchasePaymentTm selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            lblPaymentId.setText(selectedPayment.getPaymentId());
            comboOrderId.setValue(selectedPayment.getOrderId());
            comboType.setValue(selectedPayment.getPaymentType());
            txtDate.setText(selectedPayment.getPaymentDate());
            txtTime.setText(selectedPayment.getPaymentTime());
            txtAmount.setText(String.valueOf(selectedPayment.getAmount()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }

    }
    public void goToPurchaseOrder(MouseEvent mouseEvent) {
        navigateTo("/view/PurchaseOrderPage.fxml");
    }

    private void loadOrderDetails() throws SQLException, ClassNotFoundException {
        comboOrderId.setItems(FXCollections.observableArrayList(purchasePaymentModel.getAllOrderDetails()));
    }
}
