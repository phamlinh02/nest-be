package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.Constant;
import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Authority;
import com.example.demo.domain.Role;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IAuthorityRepository;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.security.service.AccountDetailsImpl;
import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.dto.account.AccountDetailDTO;
import com.example.demo.service.dto.account.ChangePassDTO;
import com.example.demo.service.dto.account.CreateAccountDTO;
import com.example.demo.service.dto.account.ForgetPassDTO;
import com.example.demo.service.dto.account.JwtResponse;
import com.example.demo.service.dto.account.PayloadLogin;
import com.example.demo.service.dto.account.UpdateAccountByUserDTO;
import com.example.demo.service.dto.account.UpdateAccountDTO;
import com.example.demo.service.mapper.MapperUtils;
import com.example.demo.service.util.PasswordGenerator;

import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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
	private final IAccountRepository accountRepository;
	private final PasswordEncoder encoder;
	private final IRoleRepository roleRepository;
	private final IAuthorityRepository authorityRepository;
	private final AuthenticationProvider authenticationProvider;
	private final NotificationService notificationService;

	public Page<AccountDTO> getAllAccount(Pageable pageable) {
		Page<AccountDTO> accounts = MapperUtils.mapEntityPageIntoDtoPage(this.accountRepository.findAll(pageable),
				AccountDTO.class);
		
		for (AccountDTO accountDTO : accounts.getContent()) {
			List<Authority> authorities = authorityRepository.findByAccountId(accountDTO.getId());
			if (!authorities.isEmpty()) {
				Authority authority = authorities.get(0);
				Role role = roleRepository.findById(authority.getRoleId())
						.orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
				Constant.ROLE_USER roleName = role.getRoleName();
				accountDTO.setRoleName(roleName);
			}
		}

		return accounts;
	}

	public AccountDTO register(CreateAccountDTO account) {
		if (this.accountRepository.existsByUsername(account.getUsername())) {
			throw new NotFoundException("Tên đăng nhập đã tồn tại");
		} else {

			Account accountEntity = MapperUtils.map(account, Account.class);
			accountEntity.setPassword(encoder.encode(account.getPassword()));

			accountEntity = accountRepository.save(accountEntity);

			Role customerRole = roleRepository.findByRoleName(Constant.ROLE_USER.ROLE_CUSTOMER);

			if (accountEntity.getId() != null) {
				Authority authority = new Authority();
				authority.setAccountId(accountEntity.getId());
				authority.setRoleId(customerRole.getId());
				authorityRepository.save(authority);
			}

			AccountDTO accountDTO = MapperUtils.map(accountEntity, AccountDTO.class);

			List<Authority> authorities = authorityRepository.findByAccountId(accountEntity.getId());

			if (!authorities.isEmpty()) {
				Authority authority = authorities.get(0);
				Role role = roleRepository.findById(authority.getRoleId())
						.orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
				Constant.ROLE_USER roleName = role.getRoleName();
				accountDTO.setRoleName(roleName);
			}

			return accountDTO;
		}
	}

	public AccountDTO saveAccount(CreateAccountDTO account, MultipartFile avatarFile) {
		if (accountRepository.existsByUsername(account.getUsername())) {
			throw new NotFoundException("Tên đăng nhập đã tồn tại");
		} else {
			Account accountEntity = MapperUtils.map(account, Account.class);
			accountEntity.setPassword(encoder.encode(account.getPassword()));
			String newAvatarPath = null;
			if (avatarFile != null) {
				try {
					String originalFileName = avatarFile.getOriginalFilename();
					String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
					newAvatarPath = "account_" + generateRandomNumber(100, 999) + fileExtension;
					String uploadDir = "uploads/account";
					Path uploadPath = Paths.get(uploadDir);

					if (!Files.exists(uploadPath)) {
						Files.createDirectories(uploadPath);
					}

					Path filePath = uploadPath.resolve(newAvatarPath);
					Files.copy(avatarFile.getInputStream(), filePath);

					accountEntity.setAvatar(newAvatarPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			accountEntity = accountRepository.save(accountEntity);

			Role customerRole = roleRepository.findByRoleName(account.getRoleName());

			if (customerRole == null) {
				throw new NotFoundException("Vai trò không tồn tại");
			}

			if (accountEntity.getId() != null) {
				Authority authority = new Authority();
				authority.setAccountId(accountEntity.getId());
				authority.setRoleId(customerRole.getId());
				authorityRepository.save(authority);
			}

			AccountDTO accountDTO = MapperUtils.map(accountEntity, AccountDTO.class);

			List<Authority> authorities = authorityRepository.findByAccountId(accountEntity.getId());

			if (!authorities.isEmpty()) {
				Authority authority = authorities.get(0);
				Role role = roleRepository.findById(authority.getRoleId())
						.orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
				Constant.ROLE_USER roleName = role.getRoleName();
				accountDTO.setRoleName(roleName);
			}

			return accountDTO;
		}
	}

	public AccountDetailDTO loadUserById(Long id) {
		Account account = this.accountRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy account"));

		if (account == null) {
			throw new NotFoundException("Không tìm thấy account");

		} else {

			List<Authority> authorities = authorityRepository.findByAccountId(account.getId());

			if (!authorities.isEmpty()) {
				Authority authority = authorities.get(0);
				Role role = roleRepository.findById(authority.getRoleId())
						.orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
				Constant.ROLE_USER roleName = role.getRoleName();
				AccountDetailDTO accountDetailDTO = MapperUtils.map(account, AccountDetailDTO.class);
				accountDetailDTO.setRoleName(roleName);
				return accountDetailDTO;
			}

			AccountDetailDTO accountDetailDTO = MapperUtils.map(account, AccountDetailDTO.class);
			return accountDetailDTO;
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
					accountDetails.getEmail(), accountDetails.getFullName(), accountDetails.getAddress(),
					accountDetails.getPhone(), accountDetails.getAvatar(), accountDetails.getRoleName());
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

		List<Authority> authorities = authorityRepository.findByAccountId(accountDTO.getId());
		if (!authorities.isEmpty()) {
			Authority authority = authorities.get(0);
			Role role = roleRepository.findById(authority.getRoleId())
					.orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
			Constant.ROLE_USER roleName = role.getRoleName();
			accountDTO.setRoleName(roleName);
		}

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

		List<Authority> authorities = authorityRepository.findByAccountId(accountDTO.getId());
		if (!authorities.isEmpty()) {
			Authority authority = authorities.get(0);
			Role role = roleRepository.findById(authority.getRoleId())
					.orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
			Constant.ROLE_USER roleName = role.getRoleName();
			accountDTO.setRoleName(roleName);
		}

		return accountDTO;
	}

	@Transactional
	public AccountDTO updateAccount(UpdateAccountDTO updateAccountDTO,MultipartFile avatarFile) {
		Account account = this.accountRepository.findById(updateAccountDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy account"));
		
		String oldAvatarPath = account.getAvatar();

		account.setUsername(updateAccountDTO.getUsername());
		account.setFullName(updateAccountDTO.getFullName());
		account.setEmail(updateAccountDTO.getEmail());
		account.setAddress(updateAccountDTO.getAddress());
		account.setPhone(updateAccountDTO.getPhone());
		String newAvatarPath = null;

		if (avatarFile != null) {
			try {
				String originalFileName = avatarFile.getOriginalFilename();
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				newAvatarPath = "account_" + generateRandomNumber(100, 999) + fileExtension;
				String uploadDir = "uploads/account";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(newAvatarPath);
				Files.copy(avatarFile.getInputStream(), filePath);

				if (oldAvatarPath != null) {
					Path oldFilePath = uploadPath.resolve(oldAvatarPath);
					Files.delete(oldFilePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (newAvatarPath != null) {
			account.setAvatar(newAvatarPath);
		} else {
			account.setAvatar(oldAvatarPath);
		}

		Role role = roleRepository.findByRoleName(updateAccountDTO.getRoleName());

		if (role == null) {
			throw new NotFoundException("Không tìm thấy role");
		}
		authorityRepository.deleteByAccountId(account.getId());

		Authority newAuthority = new Authority();
		newAuthority.setAccountId(account.getId());
		newAuthority.setRoleId(role.getId());
		authorityRepository.save(newAuthority);

		Account accountEntity = MapperUtils.map(account, Account.class);
		AccountDTO accountDTO = MapperUtils.map(this.accountRepository.save(accountEntity), AccountDTO.class);

		accountDTO.setRoleName(updateAccountDTO.getRoleName());

		return accountDTO;
	}

	public AccountDTO updateAccountByUser(UpdateAccountByUserDTO updateAccountDTO, MultipartFile avatarFile) {
		Account account = this.accountRepository.findById(updateAccountDTO.getId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy account"));

		String oldAvatarPath = account.getAvatar();

		account.setUsername(updateAccountDTO.getUsername());
		account.setFullName(updateAccountDTO.getFullName());
		account.setEmail(updateAccountDTO.getEmail());
		account.setAddress(updateAccountDTO.getAddress());
		account.setPhone(updateAccountDTO.getPhone());
		String newAvatarPath = null;

		if (avatarFile != null) {
			try {
				String originalFileName = avatarFile.getOriginalFilename();
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				newAvatarPath = "account_" + generateRandomNumber(100, 999) + fileExtension;
				String uploadDir = "uploads/account";
				Path uploadPath = Paths.get(uploadDir);

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(newAvatarPath);
				Files.copy(avatarFile.getInputStream(), filePath);

				if (oldAvatarPath != null) {
					Path oldFilePath = uploadPath.resolve(oldAvatarPath);
					Files.delete(oldFilePath);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (newAvatarPath != null) {
			account.setAvatar(newAvatarPath);
		} else {
			account.setAvatar(oldAvatarPath);
		}
		Account accountEntity = MapperUtils.map(account, Account.class);
		AccountDTO accountDTO = MapperUtils.map(this.accountRepository.save(accountEntity), AccountDTO.class);
		return accountDTO;
	}

	public String checkAsyncSendEmail() {
		notificationService.sendMail(List.of("dotruong0108t@gmail.com"), "sub", "Hello World");
		return "email";
	}
	
	public int generateRandomNumber(int min, int max) {
	    return (int) (Math.random() * (max - min + 1) + min);
	}

}
