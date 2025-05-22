package lk.ijse.finalproject.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class SupplierOrderTm {
    private String supplyOrderId;
    private String supplierId;
    private String userId;
    private String date;
    private String itemId;
}
