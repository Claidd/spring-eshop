package com.hunt.springeshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorControllerAdvice {
//    Подготавливаем сторонницу, которая будет показываться при возникновении ошибок
    @ExceptionHandler(Exception.class)
//    Статус ошибка на сервере
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exeption(Exception exception, Model model){
        String errorMessage = (exception != null ? exception.getMessage() : "Неизвестная ошибка");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
