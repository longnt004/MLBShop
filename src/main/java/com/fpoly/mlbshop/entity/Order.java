package com.fpoly.mlbshop.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Orders")  // Đổi tên bảng thành Orders
public class Order {
	@Id
	@Column(name = "idOrder", columnDefinition = "varchar(50)")
	String idOrder;
	
	@Column(name = "orderDate", columnDefinition = "date")
	Date orderDate;
	
	@Column(name = "paymentStatus", columnDefinition = "nvarchar(255)")
	String paymentStatus;
	
	@Column(name = "totalPrice", columnDefinition = "float")
	long totalPrice;

	@ManyToOne
	@JoinColumn(name = "idUser")
	User user;

	@Column(name = "paymentTypes", columnDefinition = "nvarchar(255)")
	String paymentTypes;
	
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
	List<OrderDetail> orderDetails;

	@Override
	public String toString() {
		return "Order{" +
				"idOrder='" + idOrder + '\'' +
				", orderDate=" + orderDate +
				", paymentStatus='" + paymentStatus + '\'' +
				", totalPrice=" + totalPrice +
				", user=" + user +
				", orderDetails=" + orderDetails +
				'}';
	}
}