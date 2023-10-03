package com.example.demo.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@Column(name = "StaffID")
	private Long staffId;

	@Column(name = "OrderDate", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name = "Description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "Status", nullable = false, length = 20)
	private String status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    
    @Column(name = "ReasonDeny", columnDefinition = "TEXT")
    private String reasonDeny;
}
