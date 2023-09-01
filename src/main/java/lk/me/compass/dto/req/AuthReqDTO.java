package lk.me.compass.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthReqDTO {
    private String username;
    private String password;
    private String ipAddress;
}
