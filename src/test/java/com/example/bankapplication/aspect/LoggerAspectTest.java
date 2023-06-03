package com.example.bankapplication.aspect;

import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LoggerAspectTest {
    LoggerAspect loggerAspect;
    JoinPoint joinPoint = mock(JoinPoint.class);

    @BeforeEach
    void setUp(){
        loggerAspect = new LoggerAspect();
    }

    @Test
    void logBefore_ShouldLogMethodSignature() {
        loggerAspect.logBefore(joinPoint);
        verify(joinPoint).getSignature();
    }

    @Test
    void logAfter_ShouldLogMethodSignature() {
        loggerAspect.logAfter(joinPoint);
        verify(joinPoint).getSignature();
    }

    @Test
    void beforeUseCreateAccount_ShouldLogMethodSignature() {
        loggerAspect.beforeUseCreateAccount(joinPoint);
        verify(joinPoint).getSignature();
    }

    @Test
    void beforeDeleteAccount_ShouldLogMethodSignature() {
        loggerAspect.beforeDeleteAccount(joinPoint);
        verify(joinPoint).getSignature();
    }
}