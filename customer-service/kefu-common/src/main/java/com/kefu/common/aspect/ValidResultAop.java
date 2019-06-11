package com.kefu.common.aspect;

import com.kefu.common.exception.common.ApiException;
import com.kefu.common.vo.ResultVo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidResultAop {

    /**
     * 切点
     */
    @Pointcut("@annotation(com.kefu.common.annotation.ValidResult)")
    public void pointCut() {
    }

    /**
     * 环绕通知
     */
    @Around("pointCut()")
    public Object advice(ProceedingJoinPoint point) throws Throwable {

        Object result = point.proceed();

        if (result instanceof ResultVo) {
            ResultVo resultVO = (ResultVo) result;
            Integer code = resultVO.getStatus();
            String message = resultVO.getMessage();

            if (resultVO.getStatus() != 200) {
                throw new ApiException(code, message);
            }
        }

        return result;
    }
}
