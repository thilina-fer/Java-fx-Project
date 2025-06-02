package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseOrderDto {
    private String orderId;
    private String customerContact;
    private String orderDate;
    private double totAmount;
    private String itemId;
    private ArrayList<CartDto> cartList;

    public PurchaseOrderDto(String orderId, String selectedCustomerContact, Date dateOfDate, ArrayList<CartDto> cartList) {
    }
}
