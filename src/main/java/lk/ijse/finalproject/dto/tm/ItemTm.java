package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ItemTm {
    private String itemId;
    private String itemName;
    private int quantity;
    private double buyPrice;
    private double sellPrice;
}
