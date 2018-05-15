package com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.aop;

/**
 * Created by yi
 */

import com.fab.core.logger.BaseLogger;
import com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.IYingZTDataInteractionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;


/**
 * yingzt 接口交互日志处理
 */
@Aspect
public class YingZTAspectJLoggerAOP {

    // @Remote 约定在Service中调用 不能直接调用
    @Autowired
    private IYingZTDataInteractionService yingZTDataInteractionService;

    @Around("execution(*  com.zhuanyi.vjwealth.wallet.service.mainbiz.yingzt.server.service.impl.YingZTInvestServiceImpl.apiInvest*(..))")
    //spring中Around通知
    public Object logAround(ProceedingJoinPoint joinPoint) {
        // 方法执行前的代理处理
        BaseLogger.audit("YingZTAspectJLoggerAOP  logAround开始" );

        Object result = null;

        String methodName = joinPoint.getSignature().getName();
        // 入参
        Object[] args = joinPoint.getArgs();
        // 入参处理
        String argsStr = "";
        try {
        for(Object arg : args){
            argsStr += arg.toString()+"  ;  ";
        }
        if(argsStr.length()>2500) {
            argsStr.substring(0, 2400);
        }
            //前置通知
            BaseLogger.audit("The method " + methodName + " begins with " + Arrays.asList(args));
            //执行目标方法
            result = joinPoint.proceed(args);
            //返回通知
            BaseLogger.audit("The method " + methodName + " ends with " + Arrays.asList(args));
        } catch (Throwable e) {
            //异常通知
            BaseLogger.error("The method " + methodName + " occurs expection : " + e);
            throw new RuntimeException(e);
        }finally {
            //记录日志
            yingZTDataInteractionService.insertYingZTDataInteraction(methodName, argsStr , (null == result) ? null : result.toString());
        }
        //后置通知
        BaseLogger.audit("YingZTAspectJLoggerAOP The method " + methodName + " ends logAround结束" );
        return result;
    }
}
