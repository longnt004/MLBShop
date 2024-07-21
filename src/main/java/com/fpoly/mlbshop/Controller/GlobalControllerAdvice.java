package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.CategoryService;
import com.fpoly.mlbshop.Service.UsersService;
import com.fpoly.mlbshop.entity.Catalogue;
import com.fpoly.mlbshop.entity.CustomUserDetails;
import com.fpoly.mlbshop.entity.OrderDetail;
import com.fpoly.mlbshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Security;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    CategoryService categoryService;
    @Autowired
    UsersService usersService;

    @ModelAttribute("cataList")
    public List<Catalogue> cataList() {
        return categoryService.findAll();
    }

    @ModelAttribute("user")
    public User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            return usersService.findById(customUserDetails.getIdUser());
        }else {
            return null;
        }
    }

    @ModelAttribute("countCart")
    public int countCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")){
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = usersService.findById(customUserDetails.getIdUser());
            return user.getOrderDetails().size();
        }else {
            return 0;
        }
    }
}
