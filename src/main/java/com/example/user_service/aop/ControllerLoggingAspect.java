package com.example.user_service.aop;

import com.example.user_service.dto.LoginRequest;
import com.example.user_service.dto.UserRequest;
import com.example.user_service.service.BaseRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;

    public ControllerLoggingAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Around("execution(* com.example.user_service.controller..*(..))")
    public Object logController(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        Object[] args = pjp.getArgs();

        String ackId = null;

        for (Object arg : args) {
            if (arg instanceof LoginRequest || arg instanceof UserRequest) {

                ackId = ((BaseRequest) arg).getAcknowledgementId();

                break;
            }
        }

        log.info("START | {} | ackId={}  | ip={}",
                pjp.getSignature().getName(), ackId, request.getRemoteAddr());

        Object result = null;
        try {
            result = pjp.proceed();
        } finally {
            log.info("END | {} | ackId={} | timeTaken={}ms",
                    pjp.getSignature().getName(), ackId, System.currentTimeMillis() - start);
        }


        return result;
    }

}
