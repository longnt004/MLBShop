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
@ToString
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	@Id
	@Column(name = "id_user",columnDefinition = "varchar(50)")
	String idUser;

	@Column(name= "fullname",columnDefinition = "nvarchar(100)")
	String fullname;

	@Column(name = "address",columnDefinition = "nvarchar(200)")
	String address;

	@Column(name = "email",columnDefinition = "nvarchar(100)", unique = true)
	String email;

	@Column(name = "phoneNumber",columnDefinition = "varchar(11)")
	String PhoneNumber;

	@Column(name = "status",columnDefinition = "bit")
	Boolean status;

	@Column(name = "password",columnDefinition = "varchar(255)")
	String password;

	@Column(name = "role", columnDefinition = "nvarchar(50)")
	String role;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL )
	@ToString.Exclude
	List<OrderDetail> orderDetails;

}
