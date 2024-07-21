package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.*;
import com.fpoly.mlbshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    UsersService usersService;
    @Autowired
    OrderDetailService orderDetailService;


    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Page<Product> findAll(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1, 7);
        return productRepository.findAll(pageable);
    }

    public List<Product> searchProduct(String keyword) {
        return productRepository.findProductByName(keyword);
    }

    public Page<Product> searchProduct(String keyword, int PageNo) {
        Pageable pageable = PageRequest.of(PageNo-1, 7);
        List<Product> list = productRepository.findProductByName(keyword);
        int start = (int) pageable.getOffset();
        int end = (pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<Product>(list, pageable, list.size());
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void updateListProduct(List<Product> listProduct) {
        productRepository.saveAll(listProduct);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public List<Product> findProductByCatalogue(String CateID) {
        return productRepository.findProductByCatalogue(CateID);
    }

    public String autoIncreaseIdProduct() {
        Product lastProd = productRepository.findLastIdProduct();
        String lastId = lastProd.getIdProduct();
        String[] indexCut = lastId.split("(?<=\\D)(?=\\d)");
        return indexCut[0] + String.format("%0"+indexCut[1].length()+"d", Integer.parseInt(indexCut[1])+1);
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product findByName(String productName) {
        return productRepository.findByNameProduct(productName);
    }

    public String countProduct() {
        return productRepository.countProduct();
    }
}
