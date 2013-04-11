package cdu.aop;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import cdu.algorithm.IAlgorithm;

@Aspect
@Component
public class LoggerInspector {
	
	@Around("execution(public void cdu.algorithm..*.generateDominatingSet())")
	public void aroundMethod(ProceedingJoinPoint pjp) throws Throwable {		
		long start = System.nanoTime();
		pjp.proceed();
		long end = System.nanoTime();
		
		System.out.println(pjp.getTarget() +": " + (end - start));
		
		//print result
		IAlgorithm a = (IAlgorithm) pjp.getThis();
		Set<List<String>> dsSet = a.getDominatingSetSet();
		Iterator<List<String>> dsIt = dsSet.iterator();
		
		while(dsIt.hasNext()){
			List<String> dsRow = dsIt.next();
			for(int i=0;i<dsRow.size();i++){
				System.out.print(dsRow.get(i)+",");
			}
			System.out.println();
				
		}
	}

}
