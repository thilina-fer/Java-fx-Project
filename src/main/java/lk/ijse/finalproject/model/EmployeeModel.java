package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.EmployeeDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public boolean saveEmployee(EmployeeDto employeeDto) throws ClassNotFoundException , SQLException {
        return CrudUtil.execute(
                "INSERT INTO employee VALUES(?,?,?,?,?,?)",
                employeeDto.getEmployeeId(),
                employeeDto.getEmployeeName(),
                employeeDto.getEmployeeContact(),
                employeeDto.getEmployeeAddress(),
                employeeDto.getEmployeeAge(),
                employeeDto.getSalary()
        );
    }
    public boolean updateEmployee(EmployeeDto employeeDto) throws ClassNotFoundException , SQLException{
        return CrudUtil.execute(
                "UPDATE employee SET emp_name = ? , emp_contact = ? , emp_address = ? , emp_age = ? , salary = ? WHERE emp_id = ?",
                employeeDto.getEmployeeName(),
                employeeDto.getEmployeeContact(),
                employeeDto.getEmployeeAddress(),
                employeeDto.getEmployeeAge(),
                employeeDto.getSalary(),
                employeeDto.getEmployeeId()

        );
    }
    public boolean deleteEmployee(String empId) throws ClassNotFoundException ,SQLException{
        return CrudUtil.execute("DELETE FROM employee WHERE emp_id = ?",
                empId);
    }
    public ArrayList<EmployeeDto> getAllEmployee() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee");
        ArrayList<EmployeeDto> employeeDtoArrayList = new ArrayList<>();
        while (resultSet.next()){
            EmployeeDto employeeDto = new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)
            );
            employeeDtoArrayList.add(employeeDto);
        }
        return employeeDtoArrayList;
    }
    public String getNextEmployeeId() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1");
        char tableChartacter = 'E';

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableChartacter + "%03d" , nextIdNumber);
            return nextIdString;
        }
        return tableChartacter + "001";
    }
    public ArrayList<EmployeeDto> getEmployeeDetailsFromContact(String phoneNum) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee WHERE emp_contact = ?", phoneNum);
        ArrayList<EmployeeDto> dtos = new ArrayList<>();
        if (rst.next()) {
            dtos.add(new EmployeeDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getDouble(6)
                    ));
        }
        return dtos;
    }
}
