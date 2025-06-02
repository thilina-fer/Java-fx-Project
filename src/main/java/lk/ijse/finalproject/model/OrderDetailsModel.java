package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.CartDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsModel {
    private final ItemModel itemModel = new ItemModel();

    public boolean saveOrderDetailsList(ArrayList<CartDto> cartList) throws SQLException {
        for (CartDto cartDto : cartList) {
            boolean isOrderDetailsSaved = saveOrderDetail(cartDto);
            if (!isOrderDetailsSaved){
                return false;
            }
            boolean isItemUpdated = itemModel.reduceQty(cartDto);
            if (!isItemUpdated){
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(CartDto cartDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO purchase_order VALUES (?,?,?,?,?)",
                cartDto.getOrderId(),
                cartDto.getItemId(),
                cartDto.getQty(),
                cartDto.getUnitPrice()
        );
    }
}
