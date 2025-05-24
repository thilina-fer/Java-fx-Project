package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchasePaymentTm {
    private String paymentId;
    private String orderId;
    private String paymentType;
    private String paymentDate;
    private String paymentTime;
    private double amount;
}
