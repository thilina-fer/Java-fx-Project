package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CartDto {
    private String orderId;
    private String customerId;
    private String itemId;
    private int qty;
    private double unitPrice;
}
