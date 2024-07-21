package com.fpoly.mlbshop.repository;

import com.fpoly.mlbshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,String>{
    @Query("SELECT p FROM Product p WHERE p.catalogues.IdCatalogue = ?1")
    List<Product> findProductByCatalogue(String CateID);

    @Query("SELECT p FROM Product p WHERE p.nameProduct LIKE %:keyword%")
    List<Product> findProductByName(String keyword);

    @Query("SELECT u FROM Product u WHERE u.idProduct like 'SP%' ORDER BY u.idProduct DESC LIMIT 1")
    Product findLastIdProduct();

    Product findByNameProduct(String productName);

    @Query("SELECT COUNT(p) FROM Product p")
    String countProduct();
}

