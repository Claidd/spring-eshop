package com.hunt.springeshop.service;

import com.hunt.springeshop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {// security
    boolean save(UserDTO userDTO);
}
