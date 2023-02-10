package com.hunt.springeshop.controller;

import com.hunt.springeshop.domain.User;
import com.hunt.springeshop.dto.UserDTO;
import com.hunt.springeshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    /*Назначаем права доступа только для пользователей с ролью админов*/
    public String newUser(Model model) {
        System.out.println("Вызов метода нью юзер");
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    /*проверяем аутентификацию. Берем текущее имя и имя принципал*/
    @PostAuthorize("isAuthenticated() and #username == authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable ("name") String username){
        System.out.println("Вызов метода getRoles");
        /*Мы выделяем запросы, которые позвонляют обратиться к поиску ролей текущег опользователя, ко всем
        * аутифицированным, у которых логин пользователя равен самому пользователю. Через эту ссылку даже админ не сможет
        * посмотреть роль пользователя через ссылку страницы, но при этом можем позволять все что угодно если добавлять методы соотв
        *  */
        User byName = userService.findByName(username);
        return byName.getRole().name();
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
/*Задание добавить вызовы аоп, которые будут логировать вызовы любого контроллера, сделать страницу
* куда будет выводиться лог*/