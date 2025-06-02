/*
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
import javafx.util.StringConverter;
import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.PurchaseOrderDto;
import lk.ijse.finalproject.dto.tm.PurchaseOrderTm;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchaseOrderController implements Initializable {
    public Label lblOrderId;
    public TextField txtDate;
    public TextField txtAmount;
    public ComboBox comboCustomerContact;


    public TableView<PurchaseOrderTm> tblPurchaseOrder;
    public TableColumn<PurchaseOrderTm , String> colOrderId;
    public TableColumn<PurchaseOrderTm , String> colCustomerContact;
    public TableColumn<PurchaseOrderTm , String> colDate;
    public TableColumn<PurchaseOrderTm , String> colTotalAmount;

    private final PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TextField searchField;
    public AnchorPane ancPurchseOrderPage;
    public TextField txtCustomerContact;


    public PurchaseOrderController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("customerContact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        //colTotAmount.setCellValueFactory(new PropertyValueFactory<>("totAmount"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblPurchaseOrder.setItems(FXCollections.observableArrayList(
                purchaseOrderModel.getAllPurchaseOrder().stream()
                        .map(purchaseOrderDto -> new PurchaseOrderTm(
                                purchaseOrderDto.getOrderId(),
                                purchaseOrderDto.getCustomerContact(),
                                purchaseOrderDto.getOrderDate(),
                                purchaseOrderDto.getTotAmount()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            comboCustomerContact.getSelectionModel().clearSelection();
            txtDate.setText(null);
            txtAmount.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String customerContact = (String) comboCustomerContact.getValue();
        String date = txtDate.getText();
        double amount = Double.parseDouble(txtAmount.getText());

        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(
                orderId,
                customerContact,
                date,
                Double.parseDouble(String.valueOf(amount))
        );

        try {
            boolean isSaved = purchaseOrderModel.savePurchaseOrder(purchaseOrderDto);

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
        String orderId = lblOrderId.getText();
        String customerContact = (String) comboCustomerContact.getValue();
        String date = txtDate.getText();
        double amount = Double.parseDouble(txtAmount.getText());

        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(
                orderId,
                customerContact,
                date,
                Double.parseDouble(String.valueOf(amount))
        );

        try {
            boolean isUpdate = purchaseOrderModel.updatePurchaseOrder(purchaseOrderDto);

            if (isUpdate){
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

        if (response.isPresent() && response.get() == ButtonType.YES){
            String poId = lblOrderId.getText();

            try {
                boolean isDelete = purchaseOrderModel.deletePurchaseOrder(poId);

                if (isDelete){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Delete Successfully").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Delete Failed");
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = purchaseOrderModel.getNextPurchaseOrder();
        lblOrderId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PurchaseOrderTm selectedItem = tblPurchaseOrder.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            lblOrderId.setText(selectedItem.getOrderId());
            comboCustomerContact.getSelectionModel().select(selectedItem.getCustomerContact());
            txtDate.setText(selectedItem.getOrderDate());
            txtAmount.setText(String.valueOf(selectedItem.getTotAmount()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void navigateTo(String path) {
        try {
            ancPurchseOrderPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancPurchseOrderPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancPurchseOrderPage.heightProperty());

            ancPurchseOrderPage.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    public void goToCustomerPage(MouseEvent mouseEvent) {
        navigateTo("/view/CustomerPage.fxml");
    }

    public void search(KeyEvent keyEvent) {
        String searchText = searchField.getText();
        if (searchText.isEmpty()){
            try {
                loadTableData();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Faild to load Customers").show();
            }
        }else {
            try {
                ArrayList<PurchaseOrderDto> orderList = purchaseOrderModel.searchPurchaseOrder(searchText);
                tblPurchaseOrder.setItems(FXCollections.observableArrayList(
                        orderList.stream()
                                .map(purchaseOrderDto -> new PurchaseOrderTm(
                                        purchaseOrderDto.getOrderId(),
                                        purchaseOrderDto.getCustomerContact(),
                                        purchaseOrderDto.getOrderDate(),
                                        purchaseOrderDto.getTotAmount()
                                )).toList()
                ));
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search orders").show();
            }
        }
    }
    public void goToDashboard(MouseEvent mouseEvent) {
        navigateTo("/view/Dashboard.fxml");
    }
   // public void loadItemDetails(){
    */
/*public void secondSearch(KeyEvent keyEvent) {
        String searchText = txtCustomerContact.getText();
        if (searchText.isEmpty()){
            try {
                loadTableData();
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Faild to load Customers").show();
            }
        }else {
            try {
                ArrayList<CustomerDto> customerList = purchaseOrderModel.searchCustomer(searchText);
                comboCustomerContact.setItems(FXCollections.observableArrayList(
                        customerList.stream().
                                map(customerDto -> new CustomerDto(
                                        customerDto.getCustomerId(),
                                        customerDto.getCustomerName(),
                                        customerDto.getCustomerContact(),
                                        customerDto.getCustomerAddresss()
                                )).toList()
                ));
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search customers").show();
            }
        }
    }*//*

   */
/*public void secondSearch(KeyEvent keyEvent) {
       String searchText = txtCustomerContact.getText().trim();

       try {
           if (searchText.isEmpty()) {
               loadTableData(); // Load all customers
           } else {
               ArrayList<CustomerDto> customerList = purchaseOrderModel.searchCustomer(searchText);
               comboCustomerContact.setItems(FXCollections.observableArrayList(customerList));
           }
       } catch (Exception e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "Failed to load/search customers").show();
       }
   }*//*


    public void secondSearch(KeyEvent keyEvent) {
        String keyword = txtCustomerContact.getText().trim();

        try {
            ArrayList<CustomerDto> customers;

            if (keyword.isEmpty()) {
                customers = purchaseOrderModel.getAllCustomers(); // You must have this method
            } else {
                customers = purchaseOrderModel.searchCustomer(keyword);
            }

            comboCustomerContact.setItems(FXCollections.observableArrayList(customers));

            // Optional: define how items are shown in ComboBox
            comboCustomerContact.setConverter(new StringConverter<CustomerDto>() {
                @Override
                public String toString(CustomerDto customer) {
                    return customer == null ? "" : customer.getCustomerName() + " (" + customer.getCustomerContact() + ")";
                }

                @Override
                public CustomerDto fromString(String string) {
                    return null; // Not used
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load customer data").show();
        }
    }


}
*/
