package com.zjk.item.Aspect;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import com.zjk.item.Annotation.AnnTest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class AspectTestDemo {

    @Pointcut("@annotation(com.zjk.item.Annotation.AnnTest)")
    public void aspectPointCut(){

    }

    @Around("aspectPointCut()")
    public void aroundInfo(ProceedingJoinPoint point){
        aroundInfoMethod(point);
    }
    @Before("aspectPointCut()")
    public void beginInfo(){
        beginInfoMethod();
    }

    private void beginInfoMethod() {
        log.info("beginInfoMethod...");
    }

    @After("aspectPointCut()")
    public void endInfo(){
        endInfoMethod();
    }

    private void endInfoMethod() {
        log.info("endInfoMethod...");
    }

    private void aroundInfoMethod(ProceedingJoinPoint point){
        //传的值
        log.info(String.valueOf(point.getArgs()[0]));
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        AnnTest str = method.getAnnotation(AnnTest.class);
        //注解描述上的值
        log.info("desc is {}",str.value());
        log.info("aroundInfoMethod...");
    }

}
