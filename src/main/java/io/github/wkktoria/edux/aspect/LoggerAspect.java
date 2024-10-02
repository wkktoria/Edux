package io.github.wkktoria.edux.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
class LoggerAspect {
    @Around("execution(* io.github.wkktoria.edux..*.*(..))")
    Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("{} method execution started", joinPoint.getSignature());

        Instant start = Instant.now();
        Object result = joinPoint.proceed();
        Instant end = Instant.now();
        long timeElapsed = Duration.between(start, end).toMillis();

        log.info("Time elapsed to execute {} method: {} ms", joinPoint.getSignature(), timeElapsed);
        log.info("{} method execution ended", joinPoint.getSignature());

        return result;
    }

    @AfterThrowing(value = "execution(* io.github.wkktoria.edux.*.*(..))", throwing = "ex")
    void logException(JoinPoint joinPoint, Exception ex) {
        log.error("{} method execution exception happened due to {}", joinPoint.getSignature(), ex.getMessage());
    }
}
