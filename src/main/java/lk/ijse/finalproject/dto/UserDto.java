package lk.ijse.finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserDto {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private String contact;
    private String address;
    private String role;
}
