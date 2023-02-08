package com.hunt.springeshop.controller;

import com.hunt.springeshop.domain.User;
import com.hunt.springeshop.dto.UserDTO;
import com.hunt.springeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userList";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    @PostMapping("/new")
    public String saveUser(UserDTO dto, Model model) {
        if (userService.save(dto)) {
            return "redirect:/users";
        } else {
            model.addAttribute("user", dto);
            return "user";
        }
    }

    /*
     * Открываем страницу для редактирования, моздаем ДТО и передаем его на эту страницу*/
    /*Принципал это авторизованный юзер с точки зрения спринг секьюрити*/
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Вы не авторизированны");
        }
        User user = userService.findByName(principal.getName());

        UserDTO userDTO = UserDTO.builder()
                .username(user.getName())
                .email((user.getEmail()))
                .build();
        model.addAttribute("user", userDTO);
        return "profile";
    }

    /*После внесения изменений, возравращестя отбратно дто, мы проверяем сперав, авторизирован ли пользователь
     * Далее проверяем не пустой ли пароль и равен ли он повторению, далее обновляем данные из дто */
    @PostMapping("/profile")
    public String updateProfileUser(UserDTO userDTO, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), userDTO.getUsername())) {
            throw new RuntimeException("Вы не авторизированны");
        }
        if (userDTO.getPassword() != null
                && !userDTO.getPassword().isEmpty()
                && !Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
            model.addAttribute("user", userDTO);
            return "profile";
        }
        userService.updateProfile(userDTO);
        return "profile";
    }


}
