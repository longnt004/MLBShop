package com.fpoly.mlbshop.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
		@Id
		@Column(name = "idProduct",columnDefinition = "varchar(50)")
		String idProduct;
		
		@Column(name = "nameProduct",columnDefinition = "nvarchar(100)")
		String nameProduct;
		
		@Column(name = "price",columnDefinition = "float")
		double price;
		
		@Column(name = "describe",columnDefinition = "nvarchar(200)")
		String describe;
		
		@Column(name = "image",columnDefinition = "nvarchar(200)")
		String image;

		@Column(name = "status",columnDefinition = "bit")
		boolean status;

		@ManyToOne(cascade = CascadeType.ALL)
		@JoinColumn(name = "IdCatalogue")
		Catalogue catalogues;

		@Transient
		@JsonManagedReference
		@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
		List<imgSlide> imgSlides = new ArrayList<>();

		@Override
		public String toString() {
			return "Product{" +
					"idProduct='" + idProduct + '\'' +
					", nameProduct='" + nameProduct + '\'' +
					", price=" + price +
					", describe='" + describe + '\'' +
					", image='" + image + '\'' +
					", status=" + status +
					", catalogues=" + catalogues +
					", imgSlides=" + imgSlides +
					'}';
		}
}
