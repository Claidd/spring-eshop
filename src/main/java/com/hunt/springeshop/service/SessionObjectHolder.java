package com.hunt.springeshop.service;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


/*Данный бин будет рабоатть именно в сессии, он будет считать определенные клики*/
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class SessionObjectHolder {
    private long amountClick = 0;

    public SessionObjectHolder(){
        System.out.println("Объект сессии создан.");
    }

    public long getAmountClick(){
        return amountClick;
    }

    public void addClick(){
        amountClick++;
    }
}
