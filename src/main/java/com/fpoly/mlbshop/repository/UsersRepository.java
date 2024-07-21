package com.fpoly.mlbshop.repository;

import com.fpoly.mlbshop.entity.User;
import org.hibernate.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.idUser like 'USER%' ORDER BY u.idUser DESC LIMIT 1")
    User findLastIdClient();
}
