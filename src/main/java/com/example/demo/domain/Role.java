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
@Table(name = "Role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Rolename", nullable = false)
	private String rolename;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Authority> authorities = new ArrayList<>();
	
	@Column(name = "Active")
	private String active;

}
