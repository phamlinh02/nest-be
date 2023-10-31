package com.example.demo.service.dto.account;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayloadLogin {
	
	private String username;
	
	private String password;

}
