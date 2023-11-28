package com.example.demo.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.config.Constant;
import com.example.demo.config.exception.common.NotFoundException;
import com.example.demo.domain.Account;
import com.example.demo.domain.Authority;
import com.example.demo.domain.Role;
import com.example.demo.repository.IAuthorityRepository;
import com.example.demo.repository.IRoleRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class AccountDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private String avatar;
    private Boolean isActive;
    @JsonIgnore
    private String password;
    private Constant.ROLE_USER roleName;

    public AccountDetailsImpl(Long id, String username,String password, String email, String fullName, String address, String phone, String avatar, Constant.ROLE_USER roleName,Boolean isActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.avatar = avatar;
        this.roleName = roleName;
        this.isActive = isActive;
    }

    public static AccountDetailsImpl build(Account user, IAuthorityRepository authorityRepository, IRoleRepository roleRepository) {
        if (user.getId() != null) {
            List<Authority> userAuthorities = authorityRepository.findByAccountId(user.getId());
            if (!userAuthorities.isEmpty()) {
                Authority authority = userAuthorities.get(0);
                Role role = roleRepository.findById(authority.getRoleId())
                        .orElseThrow(() -> new NotFoundException("Không tìm thấy role"));
                Constant.ROLE_USER roleName = role.getRoleName();

                return new AccountDetailsImpl(
                    Long.valueOf(user.getId()),
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getAddress(),
                    user.getPhone(),
                    user.getAvatar(),
                    roleName,
                    user.getIsActive()
                );
            }
        }

        return new AccountDetailsImpl(
            Long.valueOf(user.getId()),
            user.getUsername(),
            user.getPassword(),
            user.getEmail(),
            user.getFullName(),
            user.getAddress(),
            user.getPhone(),
            user.getAvatar(),
            null,
            user.getIsActive()
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getAvatar() {
        return avatar;
    }
     
    public Boolean getIsActive() {
		return isActive;
	}

	public Constant.ROLE_USER getRoleName() {
		return roleName;
	}

	public void setRoleName(Constant.ROLE_USER roleName) {
		this.roleName = roleName;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AccountDetailsImpl user = (AccountDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
