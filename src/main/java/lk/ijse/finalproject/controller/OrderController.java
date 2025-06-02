/*
package lk.ijse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.finalproject.dto.CartDto;
import lk.ijse.finalproject.dto.PurchaseOrderDto;
import lk.ijse.finalproject.dto.tm.CartTm;
import lk.ijse.finalproject.model.CustomerModel;
import lk.ijse.finalproject.model.ItemModel;
import lk.ijse.finalproject.model.PurchaseOrderModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {


    public TableView<CartTm> tblOrder;
    public TableColumn<CartTm, String> colCid;
    public TableColumn<CartTm, String> colItemId;
    public TableColumn<CartTm, String> colItemName;
    public TableColumn<CartTm, Integer> comboQty;
    public TableColumn<CartTm, Double> qtyPrice;
    public TableColumn<CartTm, Double> colTotal;

    private final PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();

    private final ObservableList<CartTm> cartData = FXCollections.observableArrayList();

    public Label lblOrderId;
    public Label orderDate;
    public TextField txtCustomerContact;
    public Label lblCustomerName;
    public ComboBox cmbItemId;
    public Label lblItemName;
    public TextField lblItemQty;
    public Label txtAddToCartQty;
    public Label lblItemPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        try {
            refreshPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load data").show();
        }
    }

    private void setCellValues(){
        colCid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        comboQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qtyPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        lblOrderId.setText(purchaseOrderModel.getNextPurchaseOrder());
        orderDate.setText(LocalDate.now().toString());

        loadCustomerContacts();
        loadItemIds();
    }
    private void loadCustomerContacts() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerContacts = customerModel.getAllCustomerContact();
        ObservableList<String> contacts = FXCollections.observableArrayList();
        contacts.addAll(customerContacts);
        txtCustomerContact.setText(contacts.get(0));
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        String itemIds = itemModel.getNextItemId();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(itemIds);
        cmbItemId.setItems(items);
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String selectedItemId = (String) cmbItemId.getSelectionModel().getSelectedItem();
        String cartQtyString = txtAddToCartQty.getText();

        if (selectedItemId == null){
            new Alert(Alert.AlertType.ERROR,"Please select an item").show();
            return;
        }

        if (!cartQtyString.matches("^[0-9]+$")){
            new Alert(Alert.AlertType.ERROR,"Please enter a valid quantity").show();
            return;
        }
        String itemQtyOnStockString = lblItemQty.getText();

        int cartQty = Integer.parseInt(cartQtyString);
        int itemQtyStock = Integer.parseInt(itemQtyOnStockString);

        if (itemQtyStock < cartQty){
            new Alert(Alert.AlertType.ERROR,"No enough item quantity").show();
            return;
        }
        String itemName = lblItemName.getText();
        String itemUnitPriceString = lblItemPrice.getText();

        double itemUnitPrice = Double.parseDouble(itemUnitPriceString);
        double total = itemUnitPrice * cartQty;

        for (CartTm cartTm : cartData){
            if (cartTm.getItemId().equals(selectedItemId)){
                int newQty = cartTm.getCartQty() + cartQty;

                if (itemQtyStock < newQty){
                    new Alert(Alert.AlertType.ERROR,"Not enough item quantity").show();
                    return;
                }
                txtAddToCartQty.setText("");
                cartTm.setCartQty(newQty);
                cartTm.setTotal(newQty * itemUnitPrice);

                tblOrder.refresh();
                return;
            }

            Button removeBtn = new Button("Remove");
            CartTm cartTm1 = new CartTm(
                    selectedItemId,
                    itemName,
                    cartQty,
                    itemUnitPrice,
                    total,
                    removeBtn
            );
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAAction(ActionEvent actionEvent) {
        if (tblOrder.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please select an item").show();
            return;
        }
        String selectedCustomerContact = txtCustomerContact.getText();

        if (txtCustomerContact.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Please enter a customer contact").show();
            return;
        }
        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());

        ArrayList<CartDto> cartList = new ArrayList<>();

        for (CartTm cartTm : cartData){
            CartDto cartDto = new CartDto(
                    orderId,
                    cartTm.getItemId(),
                    cartTm.getCartQty(),
                    cartTm.getUnitPrice()
            );
            cartList.add(cartDto);
        }
        PurchaseOrderDto orderDto = new PurchaseOrderDto(
                orderId,
                selectedCustomerContact,
                dateOfOrder,
                cartList
        );

        */
/*try {
            boolean isSaved = purchaseOrderModel.
        }*//*


    }


}
    */
/*public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Data").showAndWait();
        }
    }

    private void setCellValues() {
        colCid.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        comboQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        qtyPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblOrder.setItems(cartData);
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        lblOrderId.setText(purchaseOrderModel.getNextPurchaseOrder());
        lblDate.setText(LocalDate.now().toString());

        loadCustomerContacts();
        loadItemIds();
    }

    private void loadCustomerContacts() throws SQLException, ClassNotFoundException {
        ArrayList<String> customerContacts = customerModel.getAllCustomerContact();
        ObservableList<String> contacts = FXCollections.observableArrayList();
        contacts.addAll(customerContacts);
        txtContact.setText(contacts.get(0));
    }

    private void loadItemIds() throws SQLException, ClassNotFoundException {
        String itemIds = itemModel.getNextItemId();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(itemIds);
        cmbId.setItems(items);
    }


    public OrderController() throws SQLException {
    }


    public void cmbItemIdOnAction(ActionEvent actionEvent) {
    }

    public void btmAddToCartOnAction(ActionEvent actionEvent) {
        String selectedItemId = (String) cmbId.getSelectionModel().getSelectedItem();
        String cartQtyString = txtQty.getText();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please Select Item").showAndWait();
            return;
        }
        if (!cartQtyString.matches("^[0-9]+$")) {
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Quantity").showAndWait();
            return;
        }
        String itemQtyOnStockString = lblOnStockQty.getText();

        int cartQty = Integer.parseInt(cartQtyString);
        int itemQtyOnStock = Integer.parseInt(itemQtyOnStockString);

        if (itemQtyOnStock < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough Stock").showAndWait();
            return;
        }

        String itemName = lblItemName.getText();
        String itemUnitPriceString = lblPrice.getText();

        double itemUnitPrice = Double.parseDouble(itemUnitPriceString);
        double total = itemUnitPrice * cartQty;

        for (CartTm cartTm : cartData) {
            if (cartTm.getItemId().equals(selectedItemId)) {
                int newQty = cartTm.getQty() + cartQty;

                if (itemQtyOnStock < newQty) {
                    new Alert(Alert.AlertType.ERROR, "Not enough Stock").showAndWait();
                    return;
                }
                txtQty.setText("");
                cartTm.setQty(newQty);
                cartTm.setTotal(newQty * itemUnitPrice);

                tblOrder.refresh();
                return;

                txtQty.setText("");
                cartData.add(cartTm);
            }



        public void btnResetOnAction (ActionEvent actionEvent){
        }

        public void btnPlaceOrderOnAAction (ActionEvent actionEvent){
        }

    }}*/

