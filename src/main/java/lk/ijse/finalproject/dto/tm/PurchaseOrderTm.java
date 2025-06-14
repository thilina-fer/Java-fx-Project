package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseOrderTm {
    private String orderId;
    private String customerContact;
    private String orderDate;
    private double totAmount;
    private String itemId;
}
