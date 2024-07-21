package com.fpoly.mlbshop.repository;

import com.fpoly.mlbshop.entity.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailProductRepository extends JpaRepository<DetailProduct,String> {

    @Query("SELECT d FROM DetailProduct d WHERE d.product.nameProduct LIKE %?1%")
    List<DetailProduct> findDetailProductsByKeyWord(String keyword);

    @Query("SELECT d FROM DetailProduct d WHERE d.product.idProduct = ?1")
    DetailProduct findByIdProduct(String id);

    @Query("SELECT distinct d.color FROM DetailProduct d WHERE d.product.idProduct = ?1")
    List<String> findColorByIdProduct(String id);

    @Query("SELECT distinct d.size FROM DetailProduct d WHERE d.product.idProduct = ?1")
    List<String> findSizeByIdProduct(String id);

    @Query("SELECT d FROM DetailProduct d WHERE d.product.idProduct = ?1 AND d.color = ?2 AND d.size = ?3")
    DetailProduct findByProductAndColorAndSize(String idProduct, String color, String size);

    @Query("SELECT sum(d.quantity) FROM DetailProduct d WHERE d.product.idProduct = ?1 AND d.color = ?2")
    String countByProductAndColor(String idProduct, String color);

    @Query("SELECT sum(d.quantity) FROM DetailProduct d WHERE d.product.idProduct = ?1 AND d.size = ?2")
    String countByProductAndSize(String idProduct, String size);

    @Query("SELECT d.quantity FROM DetailProduct d WHERE d.product.idProduct = ?1 AND d.color = ?2 AND d.size = ?3")
    String countByProductAndColorAndSize(String idProduct, String color, String size);

    @Query("SELECT sum(d.quantity) FROM DetailProduct d WHERE d.product.idProduct = ?1")
    String countByProduct(String idProduct);
}
