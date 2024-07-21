package com.fpoly.mlbshop.repository;

import com.fpoly.mlbshop.entity.imgSlide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface imgSlideRepository extends JpaRepository<imgSlide, Long> {
    @Query("SELECT i FROM imgSlide i WHERE i.product.idProduct = ?1")
    List<imgSlide> findByIdProduct(String id);
}
