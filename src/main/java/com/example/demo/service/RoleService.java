package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Category;
import com.example.demo.domain.Product;
import com.example.demo.domain.Role;
import com.example.demo.repository.ICategotyRepository;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.service.dto.category.CategoryDTO;
import com.example.demo.service.dto.category.CategoryDetailDTO;
import com.example.demo.service.dto.category.CreateCategoryDTO;
import com.example.demo.service.dto.product.ProductDTO;
import com.example.demo.service.dto.product.UpdateProductStatusDTO;
import com.example.demo.service.dto.role.CreateRoleDTO;
import com.example.demo.service.dto.role.RoleDTO;
import com.example.demo.service.dto.role.RoleDetailDTO;
import com.example.demo.service.dto.role.UpdateRoleDTO;
import com.example.demo.service.dto.role.UpdateRoleStatusDTO;
import com.example.demo.service.mapper.MapperUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
	
	private final IRoleRepository roleRepository;
	
	public Page<RoleDTO> getAllRole(Pageable pageable) {
        Page<RoleDTO> roles = MapperUtils.mapEntityPageIntoDtoPage(this.roleRepository.findAll(pageable), RoleDTO.class);
        return roles;
    }
	
	public Page<RoleDTO> getAllRoleIsActive(Pageable pageable) {
        Page<RoleDTO> roles = MapperUtils.mapEntityPageIntoDtoPage(this.roleRepository.findByIsActiveTrue(pageable), RoleDTO.class);
        return roles;
    }
	
	public RoleDTO createRoleIfNotExist(CreateRoleDTO role) {
        if (this.roleRepository.existsByRoleName(role.getRoleName())) {
        	throw new NotFoundException("Vai trò đã tồn tại");	
        } else {
        	Role roleEntity = MapperUtils.map(role, Role.class);
            return MapperUtils.map(this.roleRepository.save(roleEntity), RoleDTO.class);
        }
    }
	
	public RoleDTO updateRole(UpdateRoleDTO updateRoleDTO) {
		Role role = this.roleRepository.findById(updateRoleDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy vai trò người dùng"));
		
		role.setRoleName(updateRoleDTO.getRoleName());
		role.setIsActive(updateRoleDTO.getIsActive());
		
		Role roleEntity = MapperUtils.map(role, Role.class);
		return MapperUtils.map(this.roleRepository.save(roleEntity), RoleDTO.class);
	}
	
	public RoleDTO updateRoleStatus(UpdateRoleStatusDTO updateStatusDTO) {
		Role role = this.roleRepository.findById(updateStatusDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy vai trò"));

		role.setIsActive(updateStatusDTO.isActive());

		Role updatedRole = this.roleRepository.save(role);

		RoleDTO roleDTO = MapperUtils.map(updatedRole, RoleDTO.class);

		return roleDTO;
	}
	
	public RoleDetailDTO loadRoleById(Long id) {
		Role role = this.roleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy vai trò"));
		
		RoleDetailDTO roleDetailDTO = MapperUtils.map(role, RoleDetailDTO.class);

		return roleDetailDTO;
	}
	
}
