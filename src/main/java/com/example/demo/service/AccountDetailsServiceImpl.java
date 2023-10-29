package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Account;
import com.example.demo.repository.IAccountRepository;
import com.example.demo.repository.IAuthorityRepository;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.security.service.AccountDetailsImpl;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
	@Autowired
	IAccountRepository accountRepository;
	@Autowired
	IAuthorityRepository authorityRepository;
	@Autowired
	IRoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Tên đăng nhập không tồn tại"));
		return AccountDetailsImpl.build(account, authorityRepository, roleRepository);
	}

}
