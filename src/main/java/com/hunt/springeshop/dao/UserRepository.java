package com.hunt.springeshop.dao;

import com.hunt.springeshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    /*Поиск в БД по активационному коду*/
    User findFirstByactivateCode(String activateCode);
}