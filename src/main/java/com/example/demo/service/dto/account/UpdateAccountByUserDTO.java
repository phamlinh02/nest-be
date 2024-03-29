package com.example.demo.service.dto.account;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.Constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountByUserDTO {
	private Long id;
    private String username;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private MultipartFile avatar;
    private Boolean isActive;
}
