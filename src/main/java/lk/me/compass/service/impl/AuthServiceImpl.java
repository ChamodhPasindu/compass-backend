package lk.me.compass.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lk.me.compass.config.Encoder;
import lk.me.compass.dto.req.AuthReqDTO;
import lk.me.compass.dto.req.UserDTO;
import lk.me.compass.dto.res.AuthResDTO;
import lk.me.compass.entity.LogData;
import lk.me.compass.entity.Role;
import lk.me.compass.entity.User;
import lk.me.compass.repo.LogDataRepo;
import lk.me.compass.repo.RoleRepo;
import lk.me.compass.repo.UserRepo;
import lk.me.compass.service.AuthService;
import lk.me.compass.util.CommonUtil;
import lk.me.compass.util.JWTUtil;
import lk.me.compass.util.RandomLoginDetail;
import lk.me.compass.util.ResponseUtil;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    Encoder encoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    LogDataRepo logDataRepo;

    @Override
    public ResponseUtil authenticationLogin(AuthReqDTO authReqDTO) throws Exception {

        Optional<User> userEntity = userRepo.findByUsername(authReqDTO.getUsername());

        if (userEntity.isPresent()) {
            Boolean isCorrect = encoder.matches(authReqDTO.getPassword().trim(), userEntity.get().getPassword());

            if (isCorrect) {

                LogData logDataEntity = new LogData();
                logDataEntity.setIpAddress(authReqDTO.getIpAddress());
                logDataEntity.setLoginTime(new Date());
                logDataEntity.setRoleName(userEntity.get().getRoleId().getRoleName());
                logDataEntity.setUserId(userEntity.get());

                LogData savedLogData = logDataRepo.save(logDataEntity);

                String token = jwtUtil.generateJwtToken(Integer.toString(userEntity.get().getId()));
                AuthResDTO authResDTO = new AuthResDTO();
                authResDTO.setId(userEntity.get().getId());
                authResDTO.setName(userEntity.get().getVenderName());
                authResDTO.setToken(token);
                authResDTO.setUsername(userEntity.get().getUsername());
                authResDTO.setRoleName(userEntity.get().getRoleId().getRoleName());
                authResDTO.setLoginId(savedLogData.getId());

                return new ResponseUtil(200, "Login Successfully", authResDTO);

            } else {
                throw new RuntimeException("Incorrect username or password");
            }
        } else {
            throw new RuntimeException("Incorrect username or password");
        }
    }

    @Override
    public ResponseUtil authenticationLogout(Integer logId) throws Exception {

        Optional<LogData> logData = logDataRepo.findById(logId);

        if (logData.isPresent()) {
            logData.get().setLogoutTime(new Date());
            logDataRepo.save(logData.get());
            return new ResponseUtil(200, "Logout Successfully", null);

        } else {
            throw new RuntimeException("Incorrect Log ID");
        }
    }

    // need to change the save random password for database(LINE 72)
    @Override
    public ResponseUtil saveUser(UserDTO userDTO) throws Exception {

        Optional<Role> roleEntity = roleRepo.findById(CommonUtil.ARTIST_ID);

        if (!roleEntity.isPresent()) {
            throw new RuntimeException("Could not find artist role");
        }

        Optional<User> optionalUser = userRepo.findByVenderNo(userDTO.getVenderNo());

        if(optionalUser.isPresent()){
            throw new RuntimeException("Already has a record for this vendor number");
        }

        UserDTO randomDetail = RandomLoginDetail.createRandomUserDetail(userDTO.getEmail());

        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setVenderName(userDTO.getVenderName());
        user.setAlternativeName(userDTO.getAlternativeName());
        user.setVenderNo(userDTO.getVenderNo());
        user.setNic(userDTO.getNic());
        user.setContactNumber(userDTO.getContact());
        // user.setPassword(encoder.encode(randomDetail.getPassword()));
        user.setPassword(encoder.encode(randomDetail.getUsername()));
        user.setUsername(randomDetail.getUsername());
        user.setRoleId(roleEntity.get());
        user.setAddress(userDTO.getAddress());
        user.setStatus(CommonUtil.ACTIVE);

        User savedUser = userRepo.save(user);

        if (savedUser != null) {
            return new ResponseUtil(200, "New user added successfully", randomDetail);
        } else {
            return new ResponseUtil(500, "Error,User saved unsuccessfully", null);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findFirstById(Integer.parseInt(username));
        if(user != null){
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), new ArrayList<>());
            return userDetails;
        }
        else{
            throw new UsernameNotFoundException(username);
        }
    }

}
