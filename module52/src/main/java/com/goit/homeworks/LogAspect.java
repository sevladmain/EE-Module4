package com.goit.homeworks;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;


/**
 * Created by SeVlad on 09.10.2016.
 */
public class LogAspect {
    private Logger logger = Logger.getLogger(getClass());
    public Object onNormalExecutionLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Before excution of: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        logger.info("After excution of: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() +
         " Result is: " + result);
        return result;
    }

    public void onExceptionLog(JoinPoint joinPoint, Exception exception) throws Throwable {
        logger.info("Error in execution: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("Error is: " + exception.getLocalizedMessage());
        StringBuilder string = new StringBuilder("Arguments is: ");

        for (Object arg :
                joinPoint.getArgs()) {
            string.append(arg.toString());
        }
        logger.info(string.toString());
    }
}
