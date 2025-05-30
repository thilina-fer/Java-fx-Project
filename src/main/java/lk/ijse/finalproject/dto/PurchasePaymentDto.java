package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PurchasePaymentDto {
    private String paymentId;
    private String orderId;
    private String paymentType;
    private String paymentDate;
    private String paymentTime;
    private double amount;
}
