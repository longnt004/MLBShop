package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.CategoryService;
import com.fpoly.mlbshop.Service.ProductService;
import com.fpoly.mlbshop.entity.Catalogue;
import com.fpoly.mlbshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/catalogue/{id}")
    public String getProductByCataloge(@PathVariable("id") String catalogueId, Model model) {
        List<Product> ProductList = productService.findProductByCatalogue(catalogueId);
        model.addAttribute("productByCatalogue", ProductList);
        return "page/product";
    }

    @RequestMapping("/product")
    public String getProduct(Model model) {
        List<Product> ProductList = productService.findAll();
        model.addAttribute("ListProduct", ProductList);
        return "page/shop";
    }

}
