package com.example.util;

import com.example.dto.JwtDTO;

public class Container {

    public static JwtDTO authorization(String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDTO jwtDTO = JwtUtil.decode(jwt);
        return jwtDTO;
    }
}
