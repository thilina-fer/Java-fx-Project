package lk.ijse.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.finalproject.dto.CartDto;
import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.ItemDto;
import lk.ijse.finalproject.dto.PurchaseOrderDto;
import lk.ijse.finalproject.dto.tm.CartTm;
import lk.ijse.finalproject.model.CustomerModel;
import lk.ijse.finalproject.model.ItemModel;
import lk.ijse.finalproject.model.OrderModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderPageController implements Initializable {

    public Label lblOrderId;
    public Label orderDate;
    public TextField txtCustomerContact;
    public Label lblCustomerName;
    public ComboBox cmbItemId;
    public ComboBox cmbContacts;
    public Label lblItemName;
    public TextField lblItemQty;
    public Label txtAddToCartQty;
    public Label lblItemPrice;

    public TableView<CartTm> tblOrder;
    public TableColumn<CartTm , String > colCid;
    public TableColumn<CartTm , String > colItemId;
    public TableColumn<CartTm , String > colItemName;
    public TableColumn<CartTm , Integer > comboQty;
    public TableColumn<CartTm , Double > colPrice;
    public TableColumn<CartTm , Double> colTotal;
    public TableColumn<? , ?> colAction;

    private final ObservableList<CartTm> cartData = FXCollections.observableArrayList();

    private final OrderModel orderModel = new OrderModel();
    private final CustomerModel customerModel = new CustomerModel();
    private final ItemModel itemModel = new ItemModel();
    public AnchorPane ancOrder;

    public OrderPageController() throws SQLException {
    }


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
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        comboQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblOrder.setItems(cartData);
    }

    private void refreshPage() throws Exception{
        lblOrderId.setText(orderModel.getNextOrderId());

        loadCustomerContactss();
        loadItemIds();
    }

    private void loadCustomerContactss() throws Exception{
        ArrayList<String> customerIdList = customerModel.getAllCustomerContact();
        ObservableList<String> contacts = FXCollections.observableArrayList();
        contacts.addAll(customerIdList);
        cmbContacts.setItems(contacts);
    }

    private void loadItemIds() throws Exception{
        ArrayList<String> itemIdList = itemModel.getAllItemIds();
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll(itemIdList);
        cmbItemId.setItems(items);
    }
    public void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = (String) cmbItemId.getSelectionModel().getSelectedItem();
        ItemDto itemDto = itemModel.findById(selectedItemId);

        if (itemDto != null) {
            lblItemName.setText(itemDto.getItemName());
            lblItemQty.setText(String.valueOf(itemDto.getQuantity()));
            lblItemPrice.setText(String.valueOf(itemDto.getBuyPrice()));
        } else {
            lblItemName.setText("");
            lblItemQty.setText("");
            lblItemPrice.setText("");
        }
    }

    public void cmbContactsOnAction(ActionEvent event) throws SQLException {
        String selectedContacts = (String) cmbContacts.getSelectionModel().getSelectedItem();
        CustomerDto customerDto = customerModel.findByContacts(selectedContacts);

        if (customerDto != null) {
            lblCustomerName.setText(customerDto.getCustomerName());
        } else {
            lblCustomerName.setText("");
        }
    }

    public void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = (String) cmbItemId.getSelectionModel().getSelectedItem();
        String cartQtyString = txtAddToCartQty.getText();

        if (selectedItemId == null){
            new Alert(Alert.AlertType.ERROR,"Please select an item").show();
            return;
        }
        if (!cartQtyString.matches("^[0-9]+$")){
            new Alert(Alert.AlertType.WARNING,"Please enter a valid quantity").show();
            return;
        }
        String itemQtyOnStockString = lblItemQty.getText();

            int cartQty = Integer.parseInt(cartQtyString);
            int itemQtyOnStock = Integer.parseInt(itemQtyOnStockString);

            if (itemQtyOnStock < cartQty){
                new Alert(Alert.AlertType.ERROR,"No enough item quantity").show();
                return;
            }
            String itemName = lblItemName.getText();
            String itemUnitPriceString = lblItemPrice.getText();

            double itemUnitPrice = Double.parseDouble(itemUnitPriceString);
            double total = itemUnitPrice * cartQty;

            for (CartTm cartTm : cartData){
                if(cartTm.getItemId().equals(selectedItemId)){
                    int newQty = cartTm.getCartQty() + cartQty;
                    
                    if (itemQtyOnStock < newQty){
                        new Alert(Alert.AlertType.ERROR,"Not enough item quantity").show();
                        return;
                    }
                    txtAddToCartQty.setText("");
                    cartTm.setCartQty(newQty);
                    cartTm.setTotal(newQty * itemUnitPrice);
                    
                    tblOrder.refresh();
                    return;
                }
            }
            Button removeBtn = new Button("Remove");
            CartTm cartTm = new CartTm(
                    selectedItemId,
                    (String) cmbContacts.getValue(),
                    itemName,
                    cartQty,
                    itemUnitPrice,
                    total
            );
            removeBtn.setOnAction(event1 -> {
                cartData.remove(cartTm);
                tblOrder.refresh();
            });
            txtAddToCartQty.setText("");
            cartData.add(cartTm);
    }

    public void btnResetOnAction(ActionEvent event) {
    }

    public void btnPlaceOrderOnAAction(ActionEvent event) {
        if (tblOrder.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an item").show();
            return;
        }
        String selectedCustomerContact = txtCustomerContact.getText();
        if (txtCustomerContact.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter a customer contact").show();
            return;
        }
        String orderId = lblOrderId.getText();
        java.sql.Date dateOfOrder = Date.valueOf(orderDate.getText());

        ArrayList<CartDto> cartList = new ArrayList<>();

        for (CartTm cartTm : cartData) {
            CartDto cartDto = new CartDto(
                    orderId,
                    (String) cmbContacts.getValue(),
                    cartTm.getItemId(),
                    cartTm.getCartQty(),
                    cartTm.getUnitPrice()
            );
            cartList.add(cartDto);
        }
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(
                orderId,
                selectedCustomerContact,
                dateOfOrder,
                cartList
        );

        try {
            boolean isSaved = orderModel.placeOrder(purchaseOrderDto);

            if (isSaved){
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION,"Order placed successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Failed to place order").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to place order");
        }
    }

    public void gotoDashboard(MouseEvent mouseEvent) {
        navigateTo("/view/Dashboard.fxml");
    }
    private void navigateTo(String path) {
        try {
            ancOrder.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancOrder.widthProperty());
            anchorPane.prefHeightProperty().bind(ancOrder.heightProperty());

            ancOrder.getChildren().add(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

}
