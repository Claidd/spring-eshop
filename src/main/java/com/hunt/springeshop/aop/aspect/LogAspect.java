package com.hunt.springeshop.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
/*Аспект описание дого, что именно мы будем делать*/
public class LogAspect {
    /*Нужно указать на какие именно слои этот срез будет распространяться. Ставим путь до нужной папки с сервисами или
    до нужного класса. Указываем ,что мы используем все классы и всю их начинку ..*.*(..) */
//    Первая звездочка указывает, что может возвращаться любое значение (как дженерик)
    @Pointcut("execution(* com.hunt.springeshop.controller..*.*(..))")
    private void anyMethod() {
    }

    /*Ставим круглые скобки, чтобы указать, что мы обращаемся именно к методу*/
    @Before("anyMethod()")
    public void logBefore(JoinPoint joinPoint) {
        Logger logger = LoggerFactory.getLogger(LogAspect.class);
        logger.info("Log before (Лог дог): " + joinPoint);
    }
}