package com.example.demo.web.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.AccountService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.account.ChangePassDTO;
import com.example.demo.service.dto.account.CreateAccountDTO;
import com.example.demo.service.dto.account.ForgetPassDTO;
import com.example.demo.service.dto.account.PayloadLogin;
import com.example.demo.service.dto.account.UpdateAccountByUserDTO;
import com.example.demo.service.dto.account.UpdateAccountDTO;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api/nest/account")
@RestController 
@CrossOrigin("*")
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
	
	@PostMapping("/save") 
    @ApiOperation(value = "Thêm người dùng")
    public ResponseDTO saveUser(@Validated @ModelAttribute CreateAccountDTO account,
    		@RequestParam(value="avatarFile", required = false) MultipartFile avatarFile) {
        return ResponseDTO.success(this.accountService.saveAccount(account,avatarFile));
    }
	
	@PostMapping("/update")
    @ApiOperation(value = "Thêm người dùng")
    public ResponseDTO updateUser(@Validated @ModelAttribute UpdateAccountDTO account,
    	    @RequestParam(value="avatarFile", required = false) MultipartFile avatarFile) {
        return ResponseDTO.success(this.accountService.updateAccount(account, avatarFile));
    }
	
	@PostMapping("/updateByUser")
	@ApiOperation(value = "Cập nhật thông tin người dùng")
	public ResponseDTO updateAccountByUser(
	    @Validated @ModelAttribute UpdateAccountByUserDTO account,
	    @RequestParam(value="avatarFile", required = false) MultipartFile avatarFile) {
	    return ResponseDTO.success(this.accountService.updateAccountByUser(account, avatarFile));
	}

	@GetMapping("/get-user")
    @ApiOperation(value = "Lấy thông tin người dùng")
    public ResponseDTO loadUserById(Long id) {
        return ResponseDTO.success(this.accountService.loadUserById(id));
    }
	
	@PostMapping("/register") 
    @ApiOperation(value = "Thêm người dùng")
    public ResponseDTO register(@Validated @RequestBody CreateAccountDTO account) {
        return ResponseDTO.success(this.accountService.register(account));
    }
	
	@PostMapping("/login")
    @ApiOperation(value = "Đăng nhập")
    public ResponseDTO login(@Validated @RequestBody PayloadLogin account) {
        return ResponseDTO.success(this.accountService.login(account));
    }
	
	@PostMapping("/update-pass")
    @ApiOperation(value = "Đổi mật khẩu")
    public ResponseDTO changePassword(@Validated @RequestBody ChangePassDTO changePassDTO) {
        return ResponseDTO.success(this.accountService.changePassword(changePassDTO));
    }
	
	@PostMapping("/forget-pass")
	@ApiOperation(value = "Quên mật khẩu")
	public ResponseDTO forgetPassword(@Validated @RequestBody ForgetPassDTO forgetPassDTO) {
		 return ResponseDTO.success(this.accountService.forgetPassword(forgetPassDTO));
	}
	
	@GetMapping("/test-send-email")
    @ApiOperation(value = "Test gửi email")
    public ResponseDTO existsByUsername() {
        return ResponseDTO.success(this.accountService.checkAsyncSendEmail());
    }
}
