package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.Service.UsersService;
import com.fpoly.mlbshop.entity.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @Autowired
    UsersService usersService;

     @RequestMapping("/formLogin")
     public String view(){
         return "page/login";
     }

    @RequestMapping("/login")
    public String login(Model model) {
        return "page/homepage";
    }
}
