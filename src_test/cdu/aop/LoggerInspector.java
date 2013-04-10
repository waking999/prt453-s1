package cdu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerInspector {
	
	@Around("execution(public void cdu.algorithm..*.generateDominatingSet())")
	public void aroundMethod(ProceedingJoinPoint pjp) throws Throwable {		
		long start = System.nanoTime();
		pjp.proceed();
		long end = System.nanoTime();
		
		System.out.println(pjp.getTarget() +": " + (end - start));
	}

}
