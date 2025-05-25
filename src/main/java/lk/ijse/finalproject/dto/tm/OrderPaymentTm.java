package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderPaymentTm {
    private String opId;
    private String supplierId;
    private String supplyOrderId;
    private Double amount;
    private String payType;
}
