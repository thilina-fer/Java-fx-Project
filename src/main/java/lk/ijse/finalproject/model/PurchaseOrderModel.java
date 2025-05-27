package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.PurchaseOrderDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderModel {
    public PurchaseOrderModel() throws SQLException {
    }

    public boolean savePurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Purchase_Order VALUES(?,?,?,?)",
                purchaseOrderDto.getOrderId(),
                purchaseOrderDto.getCustomerContact(),
                purchaseOrderDto.getOrderDate(),
                purchaseOrderDto.getTotAmount()
        );
    }

    public boolean updatePurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Purchase_Order SET customer_contact = ? , order_date = ? , total_amount = ? WHERE order_id = ?",
                purchaseOrderDto.getCustomerContact(),
                purchaseOrderDto.getOrderDate(),
                purchaseOrderDto.getTotAmount(),
                purchaseOrderDto.getOrderId()
        );
    }

    public boolean deletePurchaseOrder(String orderId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Purchase_Order WHERE order_id = ?",
                orderId);
    }

    public ArrayList<PurchaseOrderDto> getAllPurchaseOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Purchase_Order");
        ArrayList<PurchaseOrderDto> purchaseOrderDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            PurchaseOrderDto dto = new PurchaseOrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
            purchaseOrderDtoArrayList.add(dto);
        }
        return purchaseOrderDtoArrayList;
    }


    public String getNextPurchaseOrder() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT order_id FROM purchase_order ORDER BY order_id DESC LIMIT 1");
        String tableString = "PO";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(tableString.length());
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableString + "001";
    }

    /*public String getNextPurchaseOrder() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT order_id FROM purchase_order ORDER BY order_id DESC LIMIT 1");
        char tableCharacter = 'O';

        if (resultSet.next()) {
            String lastId = resultSet.getString("order_id"); // Always use column name
            if (lastId != null && lastId.length() > 1) {
                try {
                    String numberPart = lastId.substring(1); // Remove 'O'
                    int nextNumber = Integer.parseInt(numberPart) + 1;
                    return String.format(tableCharacter + "%03d", nextNumber);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid order ID format in DB: " + lastId);
                }
            }
        }

        return tableCharacter + "001";
    }*/


    public ArrayList<PurchaseOrderDto> searchPurchaseOrder(String id) throws SQLException {
        ArrayList<PurchaseOrderDto> dtos = new ArrayList<>();
        String sql = "SELECT * FROM purchase_order WHERE order_id LIKE ? OR customer_contact LIKE ? OR order_date LIKE ? OR total_amount LIKE ?";
        String pattern = "%" + id + "%";
        ResultSet resultSet = CrudUtil.execute(sql , pattern , pattern , pattern , pattern);

        while (resultSet.next()) {
            PurchaseOrderDto dto = new PurchaseOrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
            dtos.add(dto);
        }
        return dtos;
    }
    public ArrayList<String> getAllItemDetails() throws SQLException {
        ArrayList<String> itemDetails = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT item_id , item_name , quantity , buying_price , " +
                "selling_price FROM item");

        while (resultSet.next()){
            String details = "item Id : " + resultSet.getString("item_id") + "\n" +
                    "item Name : " + resultSet.getString("item_name") + "\n" +
                    "quantity : " + resultSet.getInt("quantity") + "\n" +
                    "buying Price : " + resultSet.getDouble("buying_price") + "\n" +
                    "selling Price : " + resultSet.getDouble("selling_price") + "\n\n";

            itemDetails.add(details);
        }
        return itemDetails;
    }
    /*public ArrayList<CustomerDto> searchCustomer(String id) throws SQLException {
        ArrayList<CustomerDto> dtos = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE customer_id LIKE ? OR customer_name LIKE ? OR customer_contact LIKE ? OR customer_address LIKE ?";
        String pattern = "%" + id + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern, pattern, pattern, pattern);

        while (resultSet.next()) {
            CustomerDto dto = new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            dtos.add(dto);
        }
        return dtos;
    }*/
    public ArrayList<CustomerDto> searchCustomer(String keyword) throws SQLException {
        ArrayList<CustomerDto> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE customer_id LIKE ? OR customer_name LIKE ? OR customer_contact LIKE ? OR customer_address LIKE ?";
        String pattern = "%" + keyword.trim() + "%";

        ResultSet resultSet = CrudUtil.execute(sql, pattern, pattern, pattern, pattern);

        while (resultSet.next()) {
            customers.add(new CustomerDto(
                    resultSet.getString("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("customer_contact"),
                    resultSet.getString("customer_address")
            ));
        }

        return customers;
    }
   public ArrayList<CustomerDto> getAllCustomers() throws SQLException {
        ArrayList<CustomerDto> customers = new ArrayList<>();
        String sql = "SELECT * FROM customer";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            customers.add(new CustomerDto(
                    resultSet.getString("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("customer_contact"),
                    resultSet.getString("customer_address")
            ));
        }

        return customers;
    }

}