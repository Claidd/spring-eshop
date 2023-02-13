package com.hunt.springeshop.service;

import com.hunt.springeshop.domain.User;
import com.hunt.springeshop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {// security
    boolean save(UserDTO userDTO);
    void save(User user);
    List<UserDTO> getAll();

    User findByName(String name);

    void updateProfile(UserDTO userDTO);

    boolean activateUser(String activateCode);
}
