package com.kefu.common.aspect;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

    /**
     * 切点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void webLog() {
    }

    @Around("webLog()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取request
        HttpServletRequest request = getHttpServletRequest();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // Method method = signature.getMethod();

        // 请求的参数
        Object[] args = point.getArgs();

        // 请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();

        log.info(">>>{}.{}", className, methodName);
        log.info(">>>{}", request.getRequestURI());
        log.info(">>>{}", request.getMethod());
        log.info(">>>{}", getIpAddr(request));

        if (args != null && args.length != 0) {
            log.info(">>>{}", JSON.toJSONString(args[0]));
        }

        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = null;
        try {
            result = point.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;

            log.info(">>>time:{},response parameter : {}", time, JSON.toJSONString(result));
        }
        return result;
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取IP地址
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IPUtils ERROR ", e);
        }

        // 使用代理，则获取第一个IP地址
//        if(StringUtils.isEmpty(ip) && ip.length() > 15) {
//			if(ip.indexOf(",") > 0) {
//				ip = ip.substring(0, ip.indexOf(","));
//			}
//		}

        return ip;
    }

}
