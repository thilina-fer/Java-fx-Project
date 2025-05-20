package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseOrderDto {
    private String orderId;
    private String customerId;
    private String orderDate;
    private double totAmount;
}
