package com.kefu.common.util;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 请求响应工具类
 */
public class ResponseUtils {

    private static final String TYPE = "content-type";
    private static final String JSON_TYPE = "application/json;charset=UTF-8";

    /**
     * 写入Json响应信息
     *
     * @param httpResponse
     * @param obj
     * @throws IOException
     */
    public static void writeJson(HttpServletResponse httpResponse, Object obj) throws IOException {
        writeJson(httpResponse, JSON.toJSONString(obj));
    }

    public static void writeJson(HttpServletResponse httpResponse, String str) throws IOException {
        httpResponse.addHeader(TYPE, JSON_TYPE);
        httpResponse.getWriter().println(str);
    }
}
