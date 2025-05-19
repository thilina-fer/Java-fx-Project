package lk.ijse.finalproject.model;

import lk.ijse.finalproject.dto.UserDto;
import lk.ijse.finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    public static boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?)",
                userDto.getUserId(),
                userDto.getUserName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getContact(),
                userDto.getAddress(),
                userDto.getRole()
        );
    }
    public boolean updateUser(UserDto userdto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE user SET user_name = ? , email = ? , password = ? , contact = ? , address = ? , role = ? WHERE user_id = ?",
                userdto.getUserName(),
                userdto.getEmail(),
                userdto.getPassword(),
                userdto.getContact(),
                userdto.getAddress(),
                userdto.getRole(),
                userdto.getUserId()
        );
    }
    public boolean deleteUser(String  userId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM User WHERE user_id = ?"
                ,userId);
    }

    public static String getNextUserId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");
        char tableChartacter = 'U';

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
}
