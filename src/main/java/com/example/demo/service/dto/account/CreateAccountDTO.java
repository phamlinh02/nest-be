package com.example.demo.service.dto.account;

import java.util.List;

import com.example.demo.domain.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {
	
	@Size(min = 5, max = 50, message = "Tên người dùng phải từ 5 đến 50 ký tự")
	private String username;
	
	@Size(min = 5, max = 100, message = "Mật khẩu phải từ 5 đến 100 ký tự")
	private String password;
	
	@Email(message = "Email phải đúng định dạng")
	private String email;
	
	private String fullname;
	
	private String address;
	
	@Pattern(regexp = "^[0-9]{1,12}$", message = "Số điện thoại không đúng định dạng" )
	private String phone;
	
	private String avatar;
	
	private String roleName;
	
}
