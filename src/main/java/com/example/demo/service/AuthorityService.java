package com.example.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.Constant;
import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Authority;
import com.example.demo.domain.Role;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IAuthorityRepository;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.account.UpdateAccountDTO;
import com.example.demo.service.dto.authority.AuthorityDTO;
import com.example.demo.service.dto.authority.UpdateAuthorityDTO;
import com.example.demo.service.mapper.MapperUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService {

	private final IAccountRepository accountRepository;
	private final IAuthorityRepository authorityRepository;
	private final IRoleRepository roleRepository;

	public Page<AuthorityDTO> getAllAuthority(Pageable pageable) {
		Page<AuthorityDTO> authorities = MapperUtils
				.mapEntityPageIntoDtoPage(this.authorityRepository.findAll(pageable), AuthorityDTO.class);

		// Fetch additional information for each authority
		authorities.forEach(authority -> {
			authority.setUsername(accountRepository.findById(authority.getAccountId())
					.orElseThrow(() -> new NotFoundException("Account not found")).getUsername());
			authority.setAvatar(accountRepository.findById(authority.getAccountId())
					.orElseThrow(() -> new NotFoundException("Account not found")).getAvatar());
			authority.setFullName(accountRepository.findById(authority.getAccountId())
					.orElseThrow(() -> new NotFoundException("Account not found")).getFullName());
			authority.setRoleName(roleRepository.findById(authority.getRoleId())
					.orElseThrow(() -> new NotFoundException("Role not found")).getRoleName());
		});

		return authorities;
	}

	public AuthorityDTO updateAuthority(UpdateAuthorityDTO updateAuthorityDTO) {
	    Authority authority = this.authorityRepository.findById(updateAuthorityDTO.getId())
	            .orElseThrow(() -> new NotFoundException("Không tìm thấy authority"));

	    Role role = this.roleRepository.findById(updateAuthorityDTO.getRoleId())
	            .orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
	    authority.setRoleId(role.getId());

	    Authority updatedAuthority = this.authorityRepository.save(authority);
	    AuthorityDTO authorityDTO = MapperUtils.map(updatedAuthority, AuthorityDTO.class);

	    return authorityDTO;
	}

}
