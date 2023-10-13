package com.example.demo.domain;

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
@Table(name = "Rate")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
	@Id
	@Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Productid", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "Accountid", nullable = false)
    private Account account;

    @Column(name = "Star")
    private Integer star;

    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "Image", length = 255)
    private String image;
}
