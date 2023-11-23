package com.example.demo.domain;

import java.util.Date;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long accountId;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date rateDate;

    private Integer star;

    @Column(nullable = false)
    private String comment;

    private String image;
}
