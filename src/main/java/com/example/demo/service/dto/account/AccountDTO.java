package com.example.demo.service.dto.account;


import java.util.List;

import com.example.demo.domain.Authority;
import com.example.demo.domain.Role;
import com.example.demo.domain.SharedAuditingEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO extends SharedAuditingEntity{
	
	private static final long serialVersionUID = 1L;

	private Long id;

    private String username;

    private String password;

    private String email;

    private String fullname;

    private String address;

    private String phone;

    private String avatar;
    
    private List<String> roles;
}
