package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseReportDto {
    private String reportId;
    private String orderId;
    private String description;
}
