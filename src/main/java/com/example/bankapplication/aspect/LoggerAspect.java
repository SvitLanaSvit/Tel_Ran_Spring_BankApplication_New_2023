package com.example.bankapplication.aspect;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggerAspect {
    @Generated
    @Pointcut("execution(* com.example.bankapplication.service..*.*(..))")
    private void getName(){}

    @Before("getName()")
    public void logBefore(JoinPoint joinPoint){
        log.info("Service methode called: {}", joinPoint.getSignature());
    }

    @After("getName()")
    public void logAfter(JoinPoint joinPoint){
        log.info("Service methode then after called: {}", joinPoint.getSignature());
    }

    @Before("execution(public * com.example.bankapplication.controller.AccountController.createAccount(..))")
    public void beforeUseCreateAccount(JoinPoint joinPoint){
        log.info("Account Controller called: {}", joinPoint.getSignature());
    }

    @Before("execution(public * com.example.bankapplication.controller.AccountController.delete(..))")
    public void beforeDeleteAccount(JoinPoint joinPoint){
        log.info("Account Controller called: {}", joinPoint.getSignature());
    }
}