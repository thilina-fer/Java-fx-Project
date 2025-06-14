package lk.ijse.finalproject.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartTm {
    private String itemId;
    private String customerId;
    private String itemName;
    private int cartQty;
    private double unitPrice;
    private double total;
    private Button btnRemove;

    public CartTm(String selectedItemId, String value, String itemName, int cartQty, double itemUnitPrice, double total) {
        this.itemId = selectedItemId;
        this.customerId = value;
        this.itemName = itemName;
        this.cartQty = cartQty;
        this.unitPrice = itemUnitPrice;
        this.total = total;
    }
}
