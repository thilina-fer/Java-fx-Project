package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.PurchaseOrderDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderModel {
    public boolean savePurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Purchase_Order VALUES(?,?,?,?)",
                purchaseOrderDto.getOrderId(),
                purchaseOrderDto.getCustomerId(),
                purchaseOrderDto.getOrderDate(),
                purchaseOrderDto.getTotAmount()
        );
    }

    public boolean updatePurchaseOrder(PurchaseOrderDto purchaseOrderDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Purchase_Order SET customer_id = ? , order_date = ? , total_amount = ? WHERE order_id = ?",
                purchaseOrderDto.getCustomerId(),
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
        String sql = "SELECT * FROM purchase_order WHERE order_id LIKE ? OR customer_id LIKE ? OR order_date LIKE ? OR total_amount LIKE ?";
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

}