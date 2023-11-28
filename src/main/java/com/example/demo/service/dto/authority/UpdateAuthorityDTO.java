package com.example.demo.service.dto.authority;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.Constant;
import com.example.demo.service.dto.account.UpdateAccountDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorityDTO {
	private Long id;
	private Long roleId;
}
