package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseReportTm {
    private String reportId;
    private String orderId;
    private String description;
}
