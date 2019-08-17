package com.freedom.springmvc.async_requests.example02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * 捕获AsyncRequestTimeoutException，返回 304 NOT_MODIFIED
     * @param e
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.NOT_MODIFIED) // 返回304状态码
    @ResponseBody
    @ExceptionHandler(AsyncRequestTimeoutException.class) //捕获特定异常
    public void handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("handleAsyncRequestTimeoutException");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("异步请求超时");
        out.flush();
        out.close();
    }


    /**
     * 捕获Exception，返回 500 INTERNAL_SERVER_ERROR
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 返回500状态码
    @ResponseBody
    @ExceptionHandler(Exception.class) //捕获特定异常
    public void handleException(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("handleException");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("服务器端全局异常");
        out.flush();
        out.close();
    }

}
