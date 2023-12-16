package com.example.demo.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 250)
	private String productName;

	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal price;

	@Column(nullable = false)
	private Long quantity;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;

	@Column(length = 255)
	private String image;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private Boolean isActive;

	private Long categoryId;


	@Column(name = "search_count")
	private Long searchCount = 0L;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	// Constructors, getters, setters, and other methods...

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();}

	public Product(Long id){
		this.id = id;
	}

	public void incrementSearchCount() {
		if (this.searchCount == null) {
			this.searchCount = 0L;
		}
		this.searchCount++;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
	    return endDate;
	}

}
