package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.OrderPaymentDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderPaymentModel {
    public boolean saveOrderPayment(OrderPaymentDto orderPaymentDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO order_payment VALUES(?,?,?,?,?)",
                orderPaymentDto.getOpId(),
                orderPaymentDto.getSupplierId(),
                orderPaymentDto.getSupplyOrderId(),
                orderPaymentDto.getAmount(),
                orderPaymentDto.getPayType()
        );
    }
    public boolean updateOrderPayment(OrderPaymentDto orderPaymentDto) throws SQLException {
        return CrudUtil.execute("UPDATE order_payment SET supplier_id = ? , supply_order_id = ? , amount = ? , pay_type = ? WHERE op_id = ?",
                orderPaymentDto.getSupplierId(),
                orderPaymentDto.getSupplyOrderId(),
                orderPaymentDto.getAmount(),
                orderPaymentDto.getPayType(),
                orderPaymentDto.getOpId()
        );
    }
    public boolean deleteOrderPayment(String opId) throws SQLException {
        return CrudUtil.execute("Delete FROM order_payment WHERE op_id = ?",
                opId
        );
    }
    public ArrayList<OrderPaymentDto> getAllOrderPayment() throws SQLException {
        ArrayList<OrderPaymentDto> orderPaymentDtoArrayList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM order_payment");
        while (resultSet.next()) {
            OrderPaymentDto dto = new OrderPaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
            orderPaymentDtoArrayList.add(dto);
        }
        return orderPaymentDtoArrayList;
    }
    public String getNextOrderPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT op_id FROM order_payment ORDER BY op_id DESC LIMIT 1");
        String tableString = "OP";

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
    public ArrayList<OrderPaymentDto> searchOrderPayment(String searchText) throws SQLException {
        ArrayList<OrderPaymentDto> dtos = new ArrayList<>();
        String sql = "SELECT * FROM order_payment WHERE op_id LIKE ? OR supplier_id LIKE ? OR so_id LIKE ? OR amount LIKE ? OR op_pay_type LIKE ?";
        String pattern = "%" + searchText + "%";
        ResultSet resultSet = CrudUtil.execute(sql , pattern , pattern , pattern , pattern , pattern);

        while (resultSet.next()) {
            OrderPaymentDto dto = new OrderPaymentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5)
            );
            dtos.add(dto);
        }
        return dtos;
    }
}
