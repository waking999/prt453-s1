package cdu.aop;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import cdu.algorithm.IAlgorithm;

@Aspect
@Component
public class LoggerInspector {
	private static Logger log;
	static {
		log = Logger.getLogger("cdu.aop.LoggerInspector");
		log.setLevel(Level.ALL);
	}
	@Around("execution(public void cdu.algorithm..*.generateDominatingSet())")
	public void aroundMethod(ProceedingJoinPoint pjp) throws Throwable {		
		long start = System.nanoTime();
		pjp.proceed();
		long end = System.nanoTime();
		
		log.info(pjp.getTarget() +" spends : " + (end - start)/1000000000.0 +" seconds");
		
		//print result
		IAlgorithm a = (IAlgorithm) pjp.getThis();
		Set<List<String>> dsSet = a.getDominatingSetSet();
		Iterator<List<String>> dsIt = dsSet.iterator();
		
		while(dsIt.hasNext()){
			StringBuffer sb = new StringBuffer();
			List<String> dsRow = dsIt.next();
			for(int i=0;i<dsRow.size();i++){
				sb.append(dsRow.get(i)).append(",");
			}
			log.info(sb.substring(0, sb.length()-1));
				
		}
	}

}
