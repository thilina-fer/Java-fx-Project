package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PreOrderTm {
    private String preOrderId;
    private String userId;
    private String itemId;
    private double advance;
}
