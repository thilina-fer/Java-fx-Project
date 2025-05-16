package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomerTm {
    private String customerId;
    private String customerName;
    private String customerContact;
    private String customerAddress;

}
