package com.application.volunteers.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.application.volunteers.*.controller.*Controller.*(..))")
    public void exceptionEndpoints(){}
}
