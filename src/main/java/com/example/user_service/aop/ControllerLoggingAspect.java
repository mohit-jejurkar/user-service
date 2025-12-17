package com.example.user_service.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {
    @Around("execution(* com.example.user_service.controller..*(..))")
    public Object logController(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        Object[] args = pjp.getArgs();

        String ackId = null;
        String email = null;

        for(Object arg: args){
            if (arg instanceof com.example.user_service.dto.UserRequest userRequest) {
                ackId = userRequest.getAcknowledgementId();
                email = userRequest.getEmailId();
                break;
            }
        }

        log.info("START | {} | ackId={} | email={} | ip={}",
                pjp.getSignature().getName(), ackId, email, request.getRemoteAddr());

        Object result = pjp.proceed();

        log.info("END | {} | ackId={} | timeTaken={}ms",
                pjp.getSignature().getName(), ackId, System.currentTimeMillis() - start);

        return result;
    }
}
