package com.fpoly.mlbshop.entity;

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
@Table(name = "Catalogue")
public class Catalogue {
	@Id
	@Column(name = "IdCatalogue",columnDefinition = "varchar(50)")
	String IdCatalogue;
	
	@Column(name = "nameCatalogue",columnDefinition = "nvarchar(100)")
	String nameCatalogue;

	@Column(name = "quantity",columnDefinition = "int")
	Integer quantity;

	@Column(name = "imgCatalogue",columnDefinition = "nvarchar(255)")
	String imgCatalogue;

	@OneToMany(mappedBy = "catalogues", fetch = FetchType.EAGER)
	List<Product> products;

	@Override
	public String toString() {
		return "Catalogue{" +
				"IdCatalogue='" + IdCatalogue + '\'' +
				", nameCatalogue='" + nameCatalogue + '\'' +
				", quantity=" + quantity +
				", imgCatalogue='" + imgCatalogue + '\'' +
				", products=" + products +
				'}';
	}
}
