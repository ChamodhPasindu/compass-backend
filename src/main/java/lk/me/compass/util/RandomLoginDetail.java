package lk.me.compass.util;

import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.stereotype.Component;

import lk.me.compass.dto.req.UserDTO;

@Component
public class RandomLoginDetail {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";

    public static UserDTO createRandomUserDetail(String email) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(generateUsername(email));
        userDTO.setPassword(generatePassword());
        return userDTO;

    }

    public static String generateUsername(String email) {
        StringTokenizer st = new StringTokenizer(email, "@");
        return st.nextToken();
    }

    public static String generatePassword() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
