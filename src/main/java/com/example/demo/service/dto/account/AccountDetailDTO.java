package com.example.demo.service.dto.account;

import java.util.List;

import com.example.demo.domain.SharedAuditingEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDetailDTO extends SharedAuditingEntity{
	
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
