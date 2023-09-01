package lk.me.compass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.me.compass.dto.req.AuthReqDTO;
import lk.me.compass.dto.req.UserDTO;
import lk.me.compass.service.AuthService;
import lk.me.compass.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    // Login authentication with username & password
    @PostMapping("/login")
    public ResponseUtil validateAuthentication(@RequestBody AuthReqDTO authReqDTO) {
        try {
            return authService.authenticationLogin(authReqDTO);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    // Login authentication with username & password
    @PostMapping("/logout/{id}")
    public ResponseUtil logOutFromAccount(@PathVariable Integer id ) {
        try {
            return authService.authenticationLogout(id);
        } catch (Exception e) {
            return new ResponseUtil(200, e.getMessage(), null);
        }
    }

    // save new user
    @PostMapping("/saveUser")
    public ResponseUtil saveNewUser(@RequestBody UserDTO user) {
        try {
            return authService.saveUser(user);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

}
