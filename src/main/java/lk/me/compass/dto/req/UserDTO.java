package lk.me.compass.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String venderName;
    private String venderNo;
    private String alternativeName;
    private String email;
    private String contact;
    private String nic;
    private String username;
    private String password;
    private String address;
}
