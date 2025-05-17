package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.CustomerDto;
import lk.ijse.finalproject.dto.EmployeeAttendanceDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeAttendanceModel {
    public boolean saveAttendance(EmployeeAttendanceDto employeeAttendanceDto) throws ClassNotFoundException , SQLException {
        return CrudUtil.execute("INSERT INTO employee_attendance VALUES(?,?,?,?,?)",
                employeeAttendanceDto.getAttendanceId(),
                employeeAttendanceDto.getEmployeeNic(),
                employeeAttendanceDto.getDate(),
                employeeAttendanceDto.getAttendTime(),
                employeeAttendanceDto.getDuration()

        );
    }
    public boolean updateAttendance(EmployeeAttendanceDto employeeAttendanceDto) throws ClassNotFoundException , SQLException {
        return CrudUtil.execute("UPDATE employee_attendance SET emp_nic = ? , date = ? , attend_time = ? , duration = ? WHERE attendance_id = ? ",
                employeeAttendanceDto.getEmployeeNic(),
                employeeAttendanceDto.getDate(),
                employeeAttendanceDto.getAttendTime(),
                employeeAttendanceDto.getDuration(),
                employeeAttendanceDto.getAttendanceId()
        );
    }
    public boolean deleteAttendance(String attId) throws ClassNotFoundException , SQLException {
        return CrudUtil.execute("DELETE FROM Employee_Attendance WHERE attendance_id = ? ",
                attId
        );
    }
    public ArrayList<EmployeeAttendanceDto> getAllAttendance() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee_Attendance");
        ArrayList<EmployeeAttendanceDto> empAttendanceDtoArrayList = new ArrayList<>();
        while (resultSet.next()){
            EmployeeAttendanceDto dto = new EmployeeAttendanceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            empAttendanceDtoArrayList.add(dto);
        }
        return empAttendanceDtoArrayList;
    }
    public String getNextAttendanceId() throws ClassNotFoundException , SQLException{
        ResultSet resultSet = CrudUtil.execute("SELECT attendance_id FROM employee_attendance ORDER BY attendance_id DESC LIMIT 1");
        char tableCharacter = 'A';
        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    public ArrayList<EmployeeAttendanceDto> getAttendanceDetailsFromNic(String nic) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM employee_attendance WHERE emp_nic = ?", nic);
        ArrayList<EmployeeAttendanceDto> dtos = new ArrayList<>();
        if (rst.next()) {
            dtos.add(new EmployeeAttendanceDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
                    ));
        }
        return dtos;
    }
}
