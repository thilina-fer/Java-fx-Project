package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class SupplierDto {
    private String supplierId;
    private String supplierName;
    private String supplierContact;
    private String supplierAddress;

    public SupplierDto(String supplierName, String supplierContact, String supplierAddress) {
        this.supplierName = supplierName;
        this.supplierContact = supplierContact;
        this.supplierAddress = supplierAddress;
    }
}
