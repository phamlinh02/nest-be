package com.example.demo.web.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.AccountService;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.account.UpdateAccountDTO;
import com.example.demo.service.dto.authority.UpdateAuthorityDTO;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/nest/authority")
@RestController
@CrossOrigin("*")
public class AuthorityResource {
	
	private final AuthorityService authorityService;

	public AuthorityResource(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}
	
	@GetMapping("/get-all")
    @ApiOperation(value = "Lấy danh sách authority")
    public ResponseDTO getAllAuthority(Pageable pageable) {
        return ResponseDTO.success(this.authorityService.getAllAuthority(pageable));
    }
	
	@PostMapping("/update")
    @ApiOperation(value = "Chỉnh sửa authority")
    public ResponseDTO updateUser(@Validated @ModelAttribute UpdateAuthorityDTO authority) {
        return ResponseDTO.success(this.authorityService.updateAuthority(authority));
    }

}
