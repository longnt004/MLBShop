package com.fpoly.mlbshop.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
	@Id
	@Column(name = "idOrderDetails", columnDefinition = "int")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int idOrderDetails;
	
	@Column(name = "quantity", columnDefinition = "int")
	Integer quantity;
	
	@Column(name = "price", columnDefinition = "float")
	double price;
	
    @ManyToOne
    @JoinColumn(name = "idDetailProduct")
    private DetailProduct detailProduct;

    @ManyToOne
    @JoinColumn(name = "idOrder")
    private Order orders;  // Đảm bảo tham chiếu này đến bảng Orders đã đổi tên

	@ManyToOne
	@JoinColumn(name = "idusers")
	User user;

	@Column(name = "amount")
	double amount;

	@Override
	public String toString() {
		return "OrderDetail{" +
				"idOrderDetails=" + idOrderDetails +
				", quantity=" + quantity +
				", price=" + price +
				", detailProduct=" + detailProduct +
				", orders=" + orders +
				", user=" + user +
				", amount=" + amount +
				'}';
	}
}