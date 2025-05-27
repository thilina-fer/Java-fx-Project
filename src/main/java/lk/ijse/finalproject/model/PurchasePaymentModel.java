package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.EmployeeDto;
import lk.ijse.finalproject.dto.PurchasePaymentDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchasePaymentModel {
    public boolean savePayment(PurchasePaymentDto purchasePaymentDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO purchase_payment VALUES (?,?,?,?,?,?)",
                purchasePaymentDto.getPaymentId(),
                purchasePaymentDto.getOrderId(),
                purchasePaymentDto.getPaymentType(),
                purchasePaymentDto.getPaymentDate(),
                purchasePaymentDto.getPaymentTime(),
                purchasePaymentDto.getAmount()
        );
    }

    public boolean updatePayment(PurchasePaymentDto purchasePaymentDto) throws SQLException {
        return CrudUtil.execute("UPDATE purchase_payment SET order_id=?, payment_type=?, payment_date=?, payment_time=?, amount=? WHERE payment_id=?",
                purchasePaymentDto.getOrderId(),
                purchasePaymentDto.getPaymentType(),
                purchasePaymentDto.getPaymentDate(),
                purchasePaymentDto.getPaymentTime(),
                purchasePaymentDto.getAmount(),
                purchasePaymentDto.getPaymentId()
        );
    }

    public boolean deletePayment(String paymentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM purchase_payment WHERE payment_id=?",
                paymentId
        );
    }

    public ArrayList<PurchasePaymentDto> getAllPurchasePayments() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM purchase_payment");
        ArrayList<PurchasePaymentDto> paymentList = new ArrayList<>();

        while (resultSet.next()) {
            PurchasePaymentDto payment = new PurchasePaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6)
            );
            paymentList.add(payment);
        }
        return paymentList;
    }
    public String getNextPurchasePaymentId() throws ClassNotFoundException, SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT payment_id FROM purchase_payment ORDER BY payment_id DESC LIMIT 1");
        String tableString = "PP";

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(tableString.length());
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableString + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableString + "001";
    }

    public ArrayList<PurchasePaymentDto> searchPurchasePayment(String searchText) throws SQLException {
        ArrayList<PurchasePaymentDto> dtos = new ArrayList<>();
        String sql = "SELECT * FROM purchase_payment WHERE payment_id LIKE ? OR order_id LIKE ? OR payment_type LIKE ? OR pay_day_date LIKE ? OR pay_day_time LIKE ? OR amount LIKE ?";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = CrudUtil.execute(sql, pattern, pattern, pattern, pattern, pattern , pattern);

        while (resultSet.next()) {
            PurchasePaymentDto purchasePaymentDto = new PurchasePaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6)
            );
            dtos.add(purchasePaymentDto);
        }
        return dtos;
    }


    /*public ArrayList<String> getAllOrderIds() throws SQLException {
        ArrayList<String> orderIds = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT order_id, order_date, customer_id, total FROM purchase_order");
        while (resultSet.next()){
            orderIds.add(
                    resultSet.getString("order_id"));
        }
        return orderIds;
    }*/

    public ArrayList<String> getAllOrderDetails() throws SQLException {
        ArrayList<String> orderDetails = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT order_id, customer_contact, order_date, total_amount FROM purchase_order");
        while (resultSet.next()){
            String detail = "orderId : " + resultSet.getString("order_id") + "\n" +
                    "customerContact : " + resultSet.getString("customer_contact") + "\n" +
                    "orderDate : " + resultSet.getString("order_date") + "\n" +
                    "total : " + resultSet.getDouble("total_amount") + "\n\n";
            orderDetails.add(detail);
        }
        return orderDetails;
    }
}
