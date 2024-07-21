package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.Order;
import com.fpoly.mlbshop.entity.Product;
import com.fpoly.mlbshop.repository.OderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OderRepository oderRepository;

    public String autoIncreaseIdOrder() {
        Order lastOrder = oderRepository.findLastIdOrder();
        if (lastOrder == null) {
            return "Bill01";
        }else {
            String lastId = lastOrder.getIdOrder();
            String[] indexCut = lastId.split("(?<=\\D)(?=\\d)");
            return indexCut[0] + String.format("%0"+indexCut[1].length()+"d", Integer.parseInt(indexCut[1])+1);
        }
    }

    public Order save(Order order) {
        return oderRepository.save(order);
    }

    public void update(Order order) {
        oderRepository.save(order);
    }

    public List<Order> findAll() {
        return oderRepository.findAll();
    }

    public List<Order> findOrderByKeyWord(String keyword) {
        return oderRepository.findOrderByKeyWord(keyword);
    }

    public Order findById(String idOrder) {
        return oderRepository.findById(idOrder).orElse(null);
    }

    public String countOrder() {
        return oderRepository.countOrder();
    }

    public String countOrderCancel(String status) {
        return oderRepository.countOrderCancel(status);
    }
}
