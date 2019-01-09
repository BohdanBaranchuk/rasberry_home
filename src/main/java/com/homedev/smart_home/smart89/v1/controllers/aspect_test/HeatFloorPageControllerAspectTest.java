package com.homedev.smart_home.smart89.v1.controllers.aspect_test;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Aspect
public class HeatFloorPageControllerAspectTest {

    private static final Logger log = LoggerFactory.getLogger(
            HeatFloorPageControllerAspectTest.class);

    @Around("execution(* com.homedev.smart_home.smart89.v1.controllers.HeatFloorPageController.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        LocalDateTime start = LocalDateTime.now();

        Throwable toThrow = null;
        Object returnValue = null;

        try {
            returnValue = joinPoint.proceed();
        } catch (Throwable t) {
            toThrow = t;
        }

        LocalDateTime stop = LocalDateTime.now();

        log.info("starting @ " + start.toString());
        log.info("finishing @ " + stop.toString() + " with duration "
                + stop.minusNanos(start.getNano()).getNano());

        if (null != toThrow)
            throw toThrow;

        return returnValue;
    }

}
