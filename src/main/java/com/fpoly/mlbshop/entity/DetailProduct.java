package com.fpoly.mlbshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detailProduct")
public class DetailProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetailProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProduct", referencedColumnName = "idProduct")
    private Product product;

    @OneToMany(mappedBy = "detailProduct",cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;

    private String color;

    private String size;

    @Column(name = "quantity",columnDefinition = "int")
    private int quantity;

    @Override
    public String toString() {
        return "DetailProduct{" +
                "idDetailProduct=" + idDetailProduct +
                ", product=" + product +
                ", orderDetails=" + orderDetails +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
