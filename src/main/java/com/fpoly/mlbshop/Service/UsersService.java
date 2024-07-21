package com.fpoly.mlbshop.Service;

import com.fpoly.mlbshop.entity.User;
import com.fpoly.mlbshop.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public User findByEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public String autoIncreaseIdClient() {
        User lastClient = usersRepository.findLastIdClient();
        String lastId = lastClient.getIdUser();
        String[] indexCut = lastId.split("(?<=\\D)(?=\\d)");
        return indexCut[0] + String.format("%0"+indexCut[1].length()+"d", Integer.parseInt(indexCut[1])+1);
    }

    public User save(User user){
        return usersRepository.save(user);
    }

    public User update(User user){
        return usersRepository.save(user);
    }

    public void deleteById(String idUser) {
        usersRepository.deleteById(idUser);
    }

    public User findById(String idUser) {
        return usersRepository.findById(idUser).orElse(null);
    }
}
