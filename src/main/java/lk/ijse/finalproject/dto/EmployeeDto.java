package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeDto {
    private String employeeId;
    private String employeeName;
    private String employeeContact;
    private String employeeAddress;
    private int employeeAge;
    private double salary;
}
