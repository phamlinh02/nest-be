package com.example.demo.web.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.IAccountRepository;
import com.example.demo.service.AccountService;
import com.example.demo.service.dto.ResponseDTO;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RequestMapping("/api/nest/account")
@RestController 
public class AccountResource {
	private final AccountService accountService;
	
	public AccountResource(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@GetMapping("/get-all")
    @ApiOperation(value = "Lấy danh sách người dùng")
    public ResponseDTO getAllUser(Pageable pageable) {
        return ResponseDTO.success(this.accountService.getAllAccount(pageable));
    }
}
