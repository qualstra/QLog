package com.enoch.config;

import static java.lang.String.format;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.enoch.ApplicationContext;
import com.enoch.Permission;
import com.enoch.exception.UnAuthException;
import com.enoch.service.EntitlementService;
 
@Aspect
@Component
public class SecurityAspect 
{
    private static final Logger LOGGER = LogManager.getLogger(SecurityAspect.class);
     
    @Autowired
    EntitlementService secRepo;
    @Around("execution(* com.enoch.repository..*(..)) && @annotation(com.enoch.Permission)")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable 
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Permission perm = methodSignature.getMethod().getAnnotation(Permission.class);
        String[] perms = perm.value();
        boolean auth = false;
        for (int i = 0; i < perms.length; i++) {
        	auth = secRepo.hasPermission(ApplicationContext.getContext().getLoggedInUser(), perms[i]);
        	if(auth) break;
		}
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        if(!auth) {
        	String str = "";
        	for(String permi : perms) {
        		str += permi;
        	}
        	throw new UnAuthException(
        			format("Not authorised User doesnot have %s privillege to execute %s on Class %s",
        					str,methodName,className));
        }
          
        final StopWatch stopWatch = new StopWatch();
          
        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
  
        //Log method execution time
        LOGGER.info("Execution time of " + className + "." + methodName + " "
                            + ":: " + stopWatch.getTotalTimeMillis() + " ms");
  
        return result;
    }
}