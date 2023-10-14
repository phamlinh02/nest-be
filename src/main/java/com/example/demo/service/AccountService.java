package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.IAccountRepository;
import com.example.demo.service.dto.account.AccountDTO;
import com.example.demo.service.mapper.MapperUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;

@Service
public class AccountService {
    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Page<AccountDTO> getAllAccount(Pageable pageable) {
        Page<AccountDTO> users = MapperUtils.mapEntityPageIntoDtoPage(this.accountRepository.findAll(pageable), AccountDTO.class);
        return users;
    }

}
