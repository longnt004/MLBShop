package com.fpoly.mlbshop.Controller;

import com.fpoly.mlbshop.entity.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InformationController {
    @RequestMapping("/information")
    public String Information(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getIdUser());
        model.addAttribute("user", user);
        return "payment-cod";
    }
}
