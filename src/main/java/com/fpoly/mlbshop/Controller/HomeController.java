package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.CategoryService;
import com.fpoly.mlbshop.Service.ProductService;
import com.fpoly.mlbshop.Service.UsersService;
import com.fpoly.mlbshop.entity.Catalogue;
import com.fpoly.mlbshop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UsersService usersService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String home(Model model) {
        List<Catalogue> cataList= categoryService.findAll();
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        model.addAttribute("cataList", cataList);
        return "page/homepage";
    }

    @RequestMapping("/homepage")
    public String homepage(Model model) {

        return "page/homepage";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        return "page/register";
    }

}
