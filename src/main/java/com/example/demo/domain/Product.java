package com.example.demo.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ProductName", nullable = false, length = 250)
	private String productName;

	@Column(name = "Price", nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "Description", nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(name = "Image", length = 255)
	private String image;

	@ManyToOne
	@JoinColumn(name = "CategoryID", nullable = false)
	private Category category;
}
