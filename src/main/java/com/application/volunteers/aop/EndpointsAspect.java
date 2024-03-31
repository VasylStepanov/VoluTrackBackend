package com.application.volunteers.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class EndpointsAspect {

    @Around("Pointcuts.exceptionEndpoints()")
    public Object aroundEndpoints(ProceedingJoinPoint joinPoint){
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            result = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return result;
    }
}
