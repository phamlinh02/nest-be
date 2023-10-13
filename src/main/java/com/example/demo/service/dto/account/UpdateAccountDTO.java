package com.example.demo.service.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDTO {
	private Long id;
    private String username;
    private String email;
    private String fullname;
    private String address;
    private String phone;
    private String avatar;
}
