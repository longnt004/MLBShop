package com.fpoly.mlbshop;

import com.fpoly.mlbshop.Service.CategoryService;
import com.fpoly.mlbshop.entity.Catalogue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;

import java.util.List;

@SpringBootApplication
public class MlbShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MlbShopApplication.class, args);
    }

}
