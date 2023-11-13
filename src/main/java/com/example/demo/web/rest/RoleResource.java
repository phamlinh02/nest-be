package com.example.demo.web.rest;


import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RoleService;
import com.example.demo.service.dto.ResponseDTO;
import com.example.demo.service.dto.product.UpdateProductStatusDTO;
import com.example.demo.service.dto.role.CreateRoleDTO;
import com.example.demo.service.dto.role.UpdateRoleDTO;
import com.example.demo.service.dto.role.UpdateRoleStatusDTO;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/nest/role")
@AllArgsConstructor
@CrossOrigin("*")
public class RoleResource {
	
	private final RoleService roleService;
	
	@GetMapping("/get-all")
	@ApiOperation(value = "Lấy danh sách vai trò người dùng")
	public ResponseDTO getAllRole(Pageable pageable) {
		return ResponseDTO.success(this.roleService.getAllRole(pageable));
	}
	
	@GetMapping("/get-all-active")
	@ApiOperation(value = "Lấy danh sách vai trò")
	public ResponseDTO getAllRoleIsActive(Pageable pageable) {
		return ResponseDTO.success(this.roleService.getAllRoleIsActive(pageable));
	}
	
	@PostMapping("/save")
	@ApiOperation(value = "Thêm vai trò")	
	public ResponseDTO saveCategory(@Validated @RequestBody CreateRoleDTO role) {
		return ResponseDTO.success(this.roleService.createRoleIfNotExist(role));
	}
	
	@PostMapping("/update")
	@ApiOperation(value = "Cập nhật vai trò")
	public ResponseDTO updateCategory(@Validated @ModelAttribute UpdateRoleDTO role) {
		return ResponseDTO.success(this.roleService.updateRole(role));
	}
	
	@PostMapping("/update-status")
	@ApiOperation(value = "Cập nhật trạng thái vai trò")
	public ResponseDTO updateRoleStatus(@RequestBody UpdateRoleStatusDTO updateStatusDTO) {
	    return ResponseDTO.success(this.roleService.updateRoleStatus(updateStatusDTO));
	}
	
	@GetMapping("/get-role")
	@ApiOperation(value = "Lấy thông tin vai trò")
	public ResponseDTO loadRoleById(Long id) {
		return ResponseDTO.success(this.roleService.loadRoleById(id));
	}
}
