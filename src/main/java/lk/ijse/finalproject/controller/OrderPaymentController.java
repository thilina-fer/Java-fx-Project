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
import lk.ijse.finalproject.dto.OrderPaymentDto;
import lk.ijse.finalproject.dto.PurchaseReportDto;
import lk.ijse.finalproject.dto.tm.OrderPaymentTm;
import lk.ijse.finalproject.dto.tm.PurchaseReportTm;
import lk.ijse.finalproject.model.OrderPaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.ResourceBundle;

public class OrderPaymentController implements Initializable {

    public Label lblPaymentId;
    public ComboBox comboSupplierId;
    public ComboBox comboSupplyOrderI;
    public TextField txtAmount;
    public ComboBox comboType;

    public TableView<OrderPaymentDto> tblPayment;
    public TableColumn<OrderPaymentDto , String> colPaymentId;
    public TableColumn<OrderPaymentDto , String> colSupplierId;
    public TableColumn<OrderPaymentDto , String> colSupplierOrderId;
    public TableColumn<OrderPaymentDto , Double> colAmount;
    public TableColumn<OrderPaymentDto , String> colPaymentType;

    private final OrderPaymentModel orderPaymentModel = new OrderPaymentModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TextField searchField;
    public AnchorPane ancOrdePaymentrPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("opId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSupplierOrderId.setCellValueFactory(new PropertyValueFactory<>("supplyOrderId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentType.setCellValueFactory(new PropertyValueFactory<>("payType"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void loadTbleData() throws SQLException {
        tblPayment.setItems(FXCollections.observableArrayList(
                orderPaymentModel.getAllOrderPayment().stream()
                        .map(orderPaymentDto -> new OrderPaymentDto(
                                orderPaymentDto.getOpId(),
                                orderPaymentDto.getSupplierId(),
                                orderPaymentDto.getSupplyOrderId(),
                                orderPaymentDto.getAmount(),
                                orderPaymentDto.getPayType()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTbleData();
            loadNextId();

            btnSave.setDisable(true);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            comboSupplierId.getSelectionModel().clearSelection();
            comboSupplyOrderI.getSelectionModel().clearSelection();
            txtAmount.setText(null);
            comboType.getSelectionModel().clearSelection();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String supplierId = (String) comboSupplierId.getValue();
        String supplyOrderId = (String) comboSupplyOrderI.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        String paymentType = (String) comboType.getValue();

        OrderPaymentDto orderPaymentDto = new OrderPaymentDto(
                paymentId,
                supplierId,
                supplyOrderId,
                amount,
                paymentType
        );

      try {
          boolean isSaved = orderPaymentModel.saveOrderPayment(orderPaymentDto);

          if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed").show();
          }
      }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
      }
}

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String supplierId = (String) comboSupplierId.getValue();
        String supplyOrderId = (String) comboSupplyOrderI.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        String paymentType = (String) comboType.getValue();

        OrderPaymentDto orderPaymentDto = new OrderPaymentDto(
                paymentId,
                supplierId,
                supplyOrderId,
                amount,
                paymentType
        );

        try {
            boolean isUpdated = orderPaymentModel.updateOrderPayment(orderPaymentDto);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Update Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update Failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText(null);

        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            String paymentId = lblPaymentId.getText();

            try {
                boolean isDeleted = orderPaymentModel.deleteOrderPayment(paymentId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted Successfully").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
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
        OrderPaymentDto selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            lblPaymentId.setText(selectedPayment.getOpId());
            comboSupplierId.getSelectionModel().select(selectedPayment.getSupplierId());
            comboSupplyOrderI.getSelectionModel().select(selectedPayment.getSupplyOrderId());
            txtAmount.setText(String.valueOf(selectedPayment.getAmount()));
            comboType.getSelectionModel().select(selectedPayment.getPayType());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = orderPaymentModel.getNextOrderPaymentId();
        lblPaymentId.setText(nextId);
    }

    public void search(KeyEvent keyEvent) {
        String searchText = searchField.getText();
        if (searchText.isEmpty()){
            try {
                loadTbleData();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Faild to load Payments").show();
            }
        }else {
            try {
                ArrayList<OrderPaymentDto> paymentList = orderPaymentModel.searchOrderPayment(searchText);
                tblPayment.setItems(FXCollections.observableArrayList(
                        paymentList.stream()
                                .map(orderPaymentDto -> new OrderPaymentDto(
                                        orderPaymentDto.getOpId(),
                                        orderPaymentDto.getSupplierId(),
                                        orderPaymentDto.getSupplyOrderId(),
                                        orderPaymentDto.getAmount(),
                                        orderPaymentDto.getPayType()
                                )).toList()
                ));
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search customers").show();
            }
        }
    }



    private void navigateTo(String path) {
        try {
            ancOrdePaymentrPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancOrdePaymentrPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancOrdePaymentrPage.heightProperty());

            ancOrdePaymentrPage.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    public void goToDashboard(MouseEvent mouseEvent) {
        navigateTo("/view/Dashboard.fxml");
    }


}
