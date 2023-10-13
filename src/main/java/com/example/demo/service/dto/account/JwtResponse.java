package com.example.demo.service.dto.account;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private String address;
    private String phone;
    private String avatar;
    private String role;
}
