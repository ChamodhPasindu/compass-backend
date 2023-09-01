package lk.me.compass.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResDTO {
    private int id;
    private String name;
    private String username;
    private String token;
    private String roleName;
    private int loginId;
}
