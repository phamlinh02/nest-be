package com.example.demo.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.example.demo.config.Constant;
import com.example.demo.service.dto.order.BillDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@SqlResultSetMapping(
		name = "getAllOrder",
		classes = {
				@ConstructorResult(
						targetClass = BillDTO.class,
						columns = {
								@ColumnResult(name = "id", type = Long.class),
								@ColumnResult(name = "orderDate", type = Date.class),
								@ColumnResult(name = "status", type = String.class),
								@ColumnResult(name = "description", type = String.class),
								@ColumnResult(name = "reasonDeny", type = String.class),
								@ColumnResult(name = "countOrder", type = Long.class),
								@ColumnResult(name = "sumPriceBill", type = BigDecimal.class),
								@ColumnResult(name = "email", type = String.class),
								@ColumnResult(name = "username", type = String.class),
								@ColumnResult(name = "fullName", type = String.class),
								@ColumnResult(name = "paymentId", type = String.class)
						}
				)
		}
)
@Entity
@Table(name = "Bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long accountId;

	private Long staffId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	private String description;

	@Column(nullable = false, length = 20 )
	@Enumerated(EnumType.STRING)
	private Constant.BILL_STATUS status;

    private String reasonDeny;
	private String paymentId;
	private String orderId;
	@Enumerated(EnumType.STRING)
	private Constant.PAYMENT_METHOD paymentMethod;
}
