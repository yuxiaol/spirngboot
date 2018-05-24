package com.tdhy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class HttpAspect {

	@Pointcut("within(com.tdhy.web.controller.*)")
	public void errorLogAspect() {
	}
	
	@Around("errorLogAspect()")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
		log.info(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "," + proceedingJoinPoint.getSignature().getName() );
		log.info("begin -- ");
		try {
			Object obj = proceedingJoinPoint.proceed();
			return obj; 
		} catch (Throwable e) {
			log.error(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "," + proceedingJoinPoint.getSignature().getName() + " 异常 ：{}", e);
		}
		log.info(" end");
		return null;
		
	}
	
}
