package com.mzl0101.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;

@Aspect
@Component
public class DiarySysAspect {
    private static final Logger logger =  LoggerFactory.getLogger(DiarySysAspect.class);
    public static final String format = "yyyy-MM-dd HH:mm:ss";

    private String startTime;
    private String endTime;
    private Instant inst1;
    private Instant inst2;

    @Before(value = "controllerPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        inst1 = Instant.now();//当前的时间
        Calendar calStart = Calendar.getInstance();
        startTime = new SimpleDateFormat(format).format(calStart.getTime());
        logger.info("===" + startTime + " 开始 请求  " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName() + "===\n");
    }

    @After(value = "controllerPointCut()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        inst2 = Instant.now();//当前的时间
        Calendar calEnd = Calendar.getInstance();
        endTime = new SimpleDateFormat(format).format(calEnd.getTime());
        logger.info("===" + endTime + " 请求   "  + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName() + " 结束  === \n");
        logger.info("===总共耗时： " + Duration.between(inst1, inst2).toMillis()+ "(毫秒)，"+Duration.between(inst1, inst2).getSeconds()+"(秒)===\n");
    }

    @Pointcut("execution(* com.mzl0101.*.controller..*.*(..))")
    private void controllerPointCut() {
    }
}
