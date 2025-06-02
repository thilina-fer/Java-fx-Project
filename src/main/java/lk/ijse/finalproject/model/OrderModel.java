package lk.ijse.finalproject.model;

import lk.ijse.finalproject.db.DBConnection;
import lk.ijse.finalproject.dto.PurchaseOrderDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {
    private final OrderDetailsModel orderDetailsModel = new OrderDetailsModel();

    public String getNextOrderId() throws SQLException , ClassNotFoundException{
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

    public boolean placeOrder(PurchaseOrderDto purchaseOrderDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isOrderSave = CrudUtil.execute("INSERT INTO purchase_order VALUER(?,?,?,?,?)",
                    purchaseOrderDto.getOrderId(),
                    purchaseOrderDto.getCustomerContact(),
                    purchaseOrderDto.getOrderDate(),
                    purchaseOrderDto.getTotAmount(),
                    purchaseOrderDto.getItemId()
                    );

            if (isOrderSave){
                boolean isOrderDetailsSaved = orderDetailsModel.saveOrderDetailsList(purchaseOrderDto.getCartList());
                if (isOrderDetailsSaved){
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }
    public OrderModel() throws SQLException {
    }
}
