package com.zjk.item.Aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectTestDemo {

    @Pointcut("@annotation(com.zjk.item.Annotation.AnnTest)")
    public void aspectPointCut(){

    }

    @Around("aspectPointCut()")
    public void aroundInfo(ProceedingJoinPoint point){
        aroundInfoMethod();
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

    private void aroundInfoMethod(){
        log.info("aroundInfoMethod...");
    }

}
