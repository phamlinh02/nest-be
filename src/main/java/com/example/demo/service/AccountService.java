package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Authority;
import com.example.demo.domain.Role;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.security.service.AccountDetailsImpl;
import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.account.AccountDetailDTO;
import com.example.demo.service.dto.account.ChangePassDTO;
import com.example.demo.service.dto.account.CreateAccountDTO;
import com.example.demo.service.dto.account.ForgetPassDTO;
import com.example.demo.service.dto.account.JwtResponse;
import com.example.demo.service.dto.account.PayloadLogin;
import com.example.demo.service.dto.account.UpdateAccountDTO;
import com.example.demo.service.mapper.MapperUtils;
import com.example.demo.service.util.PasswordGenerator;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {
	private final PasswordEncoder encoder;
	private final IAccountRepository accountRepository;
	private final AuthenticationProvider authenticationProvider;
	private final NotificationService notificationService;
	private final IRoleRepository roleRepository;

	public Page<AccountDTO> getAllAccount(Pageable pageable) {
		Page<Account> accountPage = this.accountRepository.findAll(pageable);

		Page<AccountDTO> accountDTOPage = accountPage.map(account -> {
			AccountDTO accountDTO = MapperUtils.map(account, AccountDTO.class);

			List<String> roles = account.getAuthorities().stream().map(authority -> authority.getRole().getRolename())
					.collect(Collectors.toList());

			accountDTO.setRoles(roles);

			return accountDTO;
		});

		return accountDTOPage;
	}

	public AccountDetailDTO loadUserByUsername(String username) {
		Account account = this.accountRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy account"));

		AccountDetailDTO accountDetailDTO = MapperUtils.map(account, AccountDetailDTO.class);

		List<String> roles = account.getAuthorities().stream().map(authority -> authority.getRole().getRolename())
				.collect(Collectors.toList());

		accountDetailDTO.setRoles(roles);

		return accountDetailDTO;
	}

	public AccountDTO saveAccount(CreateAccountDTO account) {
		if (this.accountRepository.existsByUsername(account.getUsername())) {
			throw new NotFoundException("Tên đăng nhập đã tồn tại");
		} else {
			Account accountEntity = MapperUtils.map(account, Account.class);
			accountEntity.setPassword(encoder.encode(account.getPassword()));

			String roleName = account.getRoleName();

			Role role = roleRepository.findByRolename(roleName);

			if (role == null) {
				throw new NotFoundException("Vai trò không tồn tại");
			}

			Authority authority = new Authority();
			authority.setAccount(accountEntity);
			authority.setRole(role);

			accountEntity.getAuthorities().add(authority);

			AccountDTO accountDTO = MapperUtils.map(this.accountRepository.save(accountEntity), AccountDTO.class);

			List<String> roles = accountEntity.getAuthorities().stream().map(auth -> auth.getRole().getRolename())
					.collect(Collectors.toList());
			accountDTO.setRoles(roles);

			return accountDTO;
		}
	}
	

	public AccountDTO register(CreateAccountDTO account) {
		if (this.accountRepository.existsByUsername(account.getUsername())) {
			throw new NotFoundException("Tên đăng nhập đã tồn tại");
		} else {
			Account accountEntity = MapperUtils.map(account, Account.class);
			accountEntity.setPassword(encoder.encode(account.getPassword()));
			Role customerRole = roleRepository.findByRolename("Customer");
			Authority authority = new Authority();
			authority.setAccount(accountEntity);
			authority.setRole(customerRole);
			accountEntity.getAuthorities().add(authority);

			AccountDTO accountDTO = MapperUtils.map(this.accountRepository.save(accountEntity), AccountDTO.class);

			List<String> roles = accountEntity.getAuthorities().stream().map(auth -> auth.getRole().getRolename())
					.collect(Collectors.toList());
			accountDTO.setRoles(roles);

			return accountDTO;
		}
	}

	public JwtResponse login(PayloadLogin payloadLogin) {
		String username = payloadLogin.getUsername();
		String password = payloadLogin.getPassword();

		Account account = accountRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Tên đăng nhập không tồn tại"));

		if (encoder.matches(password, account.getPassword())) {
			Authentication authentication = authenticationProvider
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();

			JwtResponse jwtResponse = new JwtResponse(accountDetails.getId(), accountDetails.getUsername(),
					accountDetails.getEmail(), accountDetails.getFullname(), accountDetails.getAddress(),
					accountDetails.getPhone(), accountDetails.getAvatar(), accountDetails.getRole());
			return jwtResponse;
		} else {
			throw new NotFoundException("Sai mật khẩu");
		}
	}

	public AccountDTO changePassword(ChangePassDTO changePassDTO) {
		Account account = this.accountRepository.findByUsername(changePassDTO.getUsername())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy account"));
		if (!encoder.matches(changePassDTO.getOldPassword(), account.getPassword())) {
			throw new NotFoundException("Mật khẩu cũ không đúng");
		}
		account.setPassword(encoder.encode(changePassDTO.getPassword()));
		Account accountEntity = MapperUtils.map(account, Account.class);
		AccountDTO accountDTO = MapperUtils.map(this.accountRepository.save(accountEntity), AccountDTO.class);

		List<String> roles = accountEntity.getAuthorities().stream().map(auth -> auth.getRole().getRolename())
				.collect(Collectors.toList());
		accountDTO.setRoles(roles);

		return accountDTO;
	}
	
	public AccountDTO updateAccount(UpdateAccountDTO updateAccountDTO) {
		Account account = this.accountRepository.findById(updateAccountDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy account"));
		
		account.setUsername(updateAccountDTO.getUsername());
		account.setFullname(updateAccountDTO.getFullname());
		account.setEmail(updateAccountDTO.getEmail());
		account.setAddress(updateAccountDTO.getAddress());
		account.setPhone(updateAccountDTO.getPhone());
		account.setAvatar(updateAccountDTO.getAvatar());
			
		Account accountEntity = MapperUtils.map(account, Account.class);
		AccountDTO accountDTO = MapperUtils.map(this.accountRepository.save(accountEntity), AccountDTO.class);

		List<String> roles = accountEntity.getAuthorities().stream().map(auth -> auth.getRole().getRolename())
				.collect(Collectors.toList());
		accountDTO.setRoles(roles);

		return accountDTO;
	}

	public AccountDTO forgetPassword(ForgetPassDTO forgetPassDTO) {
		Account account = this.accountRepository.findByUsername(forgetPassDTO.getUsername())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy account"));
		if (!forgetPassDTO.getEmail().equals(account.getEmail())) {
			throw new NotFoundException("Email không tồn tại");
		}
		String newPassword = PasswordGenerator.generateRandomPassword(5);
		notificationService.sendMail(List.of(forgetPassDTO.getEmail()), "ForgetPassword", newPassword);
		account.setPassword(encoder.encode(newPassword));
		Account accountEntity = MapperUtils.map(account, Account.class);
		AccountDTO accountDTO = MapperUtils.map(this.accountRepository.save(accountEntity), AccountDTO.class);

		List<String> roles = accountEntity.getAuthorities().stream().map(auth -> auth.getRole().getRolename())
				.collect(Collectors.toList());
		accountDTO.setRoles(roles);

		return accountDTO;
	}

	public String checkAsyncSendEmail() {
		notificationService.sendMail(List.of("dotruong0108t@gmail.com"), "sub", "Hello World");
		return "email";
	}
}
