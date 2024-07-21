package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.*;
import com.fpoly.mlbshop.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    public OrderDetail findByProductsAndUser(DetailProduct detailProduct, User user) {
        return orderDetailRepository.findByProductAndUser(detailProduct, user);
    }

    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    public void update(OrderDetail findOrder) {
        orderDetailRepository.save(findOrder);
    }

    public List<OrderDetail> findALl() {
        return orderDetailRepository.findAll();
    }

    public OrderDetail findById(String id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    public void delete(OrderDetail orderDetail) {
        orderDetailRepository.delete(orderDetail);
    }

    public List<OrderDetail> findByUser(String idUser) {
        return orderDetailRepository.findByUser(idUser);
    }

    public void deleteAll(List<OrderDetail> orderDetail) {
        orderDetailRepository.deleteAll(orderDetail);
    }

    public void deleteByUser(String id) {
        orderDetailRepository.deleteByUser(id);
    }

    public void deleteById(String id) {
        System.out.println(12);
        orderDetailRepository.deleteById(id);
    }
}
