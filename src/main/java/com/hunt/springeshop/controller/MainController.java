package com.hunt.springeshop.controller;

import com.hunt.springeshop.service.SessionObjectHolder;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class MainController {

    private final SessionObjectHolder sessionObjectHolder;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @RequestMapping({"", "/"})
    public String index(Model model, HttpSession httpSession) {
        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClick());

        if  (httpSession.getAttribute("myID") == null){
            /*У http сессии есть определенный ид, мы его генерируем рандомно и выводи(если его нет)*/
            String uuid = UUID.randomUUID().toString();
            /*Добавляем эту информацию hhtp сессии*/
            httpSession.setAttribute("myID", uuid);
            System.out.println("Сгенерирован UUID ->" + uuid);
        }
        model.addAttribute("uuid", httpSession.getAttribute("myID"));
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login-error") // чтобы пользователь не попал при ошибке на 404 страницу
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
