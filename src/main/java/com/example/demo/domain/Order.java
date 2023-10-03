package com.example.demo.domain;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "AccountID", nullable = false)
	private Account account;

	@ManyToOne
	@JoinColumn(name = "StaffID")
	private Account staff;

	@Column(name = "OrderDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name = "Description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "Status", nullable = false, length = 20)
	private String status;
}
