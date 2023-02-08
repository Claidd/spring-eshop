package com.hunt.springeshop.service;

import com.hunt.springeshop.dao.UserRepository;
import com.hunt.springeshop.domain.Role;
import com.hunt.springeshop.domain.User;
import com.hunt.springeshop.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean save(UserDTO userDTO) {
        if(!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())){
            throw new RuntimeException("Password is not equsals");
        }
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found with name " + username);
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles
        );
    }

    private UserDTO toDto(User user){
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    public void updateProfile(UserDTO userDTO) {
        /*Ищем пользоваеля, если пользователь не найден, то выбрасываем сообщение с ошибкой*/
        User saveUser = userRepository.findFirstByName(userDTO.getUsername());
        if (saveUser == null){
            throw new RuntimeException("Пользователь не найде н по имени" + userDTO.getUsername());
        }
/*Проверяем, не равен ли пароль предыдущему */
        boolean isChanged = false;
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
                saveUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                isChanged = true;
            }
/*Проверяем не равен ли мейл предыдущему, если нет, то меняем флаг на тру*/
            if (!Objects.equals(userDTO.getEmail(), saveUser.getEmail())){
                saveUser.setEmail(userDTO.getEmail());
                isChanged = true;
            }
/*если какое-то изменение было и флаг сменился на тру, то только тогда мы изменяем запись в БД
* А если нет, то и обращаться к базе данны и грузить ее мы не будем*/
            if  (isChanged){
                userRepository.save(saveUser);
            }
    }
}
