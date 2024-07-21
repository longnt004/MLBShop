package com.fpoly.mlbshop.repository;

import com.fpoly.mlbshop.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    @Query("SELECT o FROM OrderDetail o WHERE o.detailProduct = ?1 AND o.user = ?2")
    OrderDetail findByProductAndUser(DetailProduct detailProduct, User user);

    @Query("SELECT o FROM OrderDetail o ORDER BY o.idOrderDetails DESC LIMIT 1")
    OrderDetail findLastIdOrderDetail();

    @Query("SELECT o FROM OrderDetail o WHERE o.user.idUser = ?1")
    List<OrderDetail> findByUser(String idUser);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderDetail o WHERE o.user.idUser = ?1")
    void deleteByUser(String id);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderDetail o WHERE o.idOrderDetails = ?1")
    void deleteById(String id);
}
