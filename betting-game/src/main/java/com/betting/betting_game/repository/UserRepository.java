package com.betting.betting_game.repository;

import com.betting.betting_game.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //mail ile mail adresini bulur
    Optional<User> findByMail(String mail);

    //mail veritabanında var mı yok mu
    boolean existsByMail(String mail);

}
