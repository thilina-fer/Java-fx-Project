package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderPaymentDto {
    private String opId;
    private String supplierId;
    private String supplyOrderId;
    private Double amount;
    private String payType;
}
