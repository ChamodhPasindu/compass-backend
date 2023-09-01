package lk.me.compass.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import lk.me.compass.dto.req.AuthReqDTO;
import lk.me.compass.dto.req.UserDTO;
import lk.me.compass.util.ResponseUtil;

public interface AuthService extends UserDetailsService{

    //login verification
    public ResponseUtil authenticationLogin(AuthReqDTO authReqDTO) throws Exception;

    //logout and save log details    
    public ResponseUtil authenticationLogout(Integer logId) throws Exception;

    //save new user
    public ResponseUtil saveUser(UserDTO userDTO) throws Exception;


    
}
