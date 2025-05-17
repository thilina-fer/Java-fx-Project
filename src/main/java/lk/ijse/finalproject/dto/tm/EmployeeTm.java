package lk.ijse.finalproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeTm {
    private String employeeId;
    private String employeeName;
    private String employeeContact;
    private String employeeAddress;
    private String employeeNic;
    private int employeeAge;
    private double salary;
}
