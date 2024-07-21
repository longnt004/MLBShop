package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.CustomUserDetails;
import com.fpoly.mlbshop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User account = usersService.findByEmail(email);
        System.out.println(account);
        if (account == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }else {
            Collection<GrantedAuthority> authorities = new HashSet<>();
            if (account.getRole().equals("ADMIN")) {
                authorities.add(new SimpleGrantedAuthority("ADMIN"));
            } else {
                authorities.add(new SimpleGrantedAuthority("USER"));
            }
            return new CustomUserDetails(authorities, account.getIdUser(), account.getEmail(), account.getPassword(), account.getFullname(), account.getStatus(), true, true, true);
        }
    }
}
