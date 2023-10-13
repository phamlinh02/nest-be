package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	@Id
	@Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username", nullable = false, length = 50)
    private String username;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Column(name = "Fullname", nullable = false, length = 50)
    private String fullname;

    @Column(name = "Address", length = 255)
    private String address;

    @Column(name = "Phone", length = 12)
    private String phone;

    @Column(name = "Avatar", length = 255)
    private String avatar;
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Authority> authorities = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Rate> rates = new ArrayList<>();
    
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    
}
