package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.DetailProduct;
import com.fpoly.mlbshop.entity.Product;
import com.fpoly.mlbshop.repository.DetailProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailProductService {
    @Autowired
    DetailProductRepository detailProductRepository;

    public DetailProduct save(DetailProduct detailProduct) {
        return detailProductRepository.save(detailProduct);
    }

    public List<DetailProduct> findAll() {
        return detailProductRepository.findAll();
    }

    public List<DetailProduct> findDetailProductsByKeyWord(String keyword) {
        return detailProductRepository.findDetailProductsByKeyWord(keyword);
    }

    public DetailProduct findById(String idDetailProduct) {
        return detailProductRepository.findById(idDetailProduct).orElse(null);
    }

    public void update(DetailProduct detailProduct) {
        detailProductRepository.save(detailProduct);
    }

    public DetailProduct findByIdProduct(String id) {
        return detailProductRepository.findByIdProduct(id);
    }

    public List<String> findColorByIdProduct(String id) {
        return detailProductRepository.findColorByIdProduct(id);
    }

    public List<String> findSizeByIdProduct(String id) {
        return detailProductRepository.findSizeByIdProduct(id);
    }

    public String countByProductAndColor(String idProduct, String color) {
        return detailProductRepository.countByProductAndColor(idProduct, color);
    }

    public String countByProductAndSize(String idProduct, String size) {
        return detailProductRepository.countByProductAndSize(idProduct, size);
    }

    public String countByProductAndColorAndSize(String idProduct, String color, String size) {
        return detailProductRepository.countByProductAndColorAndSize(idProduct, color, size);
    }

    public String countByProduct(String idProduct) {
        return detailProductRepository.countByProduct(idProduct);
    }

    public DetailProduct findByProductAndColorAndSize(String idProduct, String color, String size) {
        return detailProductRepository.findByProductAndColorAndSize(idProduct, color, size);
    }

    public void delete(String idDetailProduct) {
        detailProductRepository.deleteById(idDetailProduct);
    }
}
