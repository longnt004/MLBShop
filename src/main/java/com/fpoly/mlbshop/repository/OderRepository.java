package com.fpoly.mlbshop.repository;

import com.fpoly.mlbshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OderRepository extends JpaRepository<Order,String> {
    @Query("SELECT o FROM Order o ORDER BY o.idOrder DESC LIMIT 1")
    Order findLastIdOrder();

    @Query("SELECT o FROM Order o WHERE o.paymentStatus LIKE %?1%")
    List<Order> findOrderByKeyWord(String keyword);

    @Query("SELECT COUNT(o) FROM Order o")
    String countOrder();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.paymentStatus = ?1")
    String countOrderCancel(String status);
}
