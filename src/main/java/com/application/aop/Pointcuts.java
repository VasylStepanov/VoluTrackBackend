package com.application.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.application.content.*.*.controller.*Controller.*(..))")
    public void exceptionEndpoints(){}
}
