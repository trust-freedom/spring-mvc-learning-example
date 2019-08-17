package com.freedom.springmvc.async_requests.example03.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Callable;

@Slf4j
public class MyCallableProcessingInterceptor implements CallableProcessingInterceptor {

    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest request, Callable<T> task) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " beforeConcurrentHandling");
    }

    @Override
    public <T> void preProcess(NativeWebRequest request, Callable<T> task) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " preProcess");
    }

    @Override
    public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " postProcess");
    }

    @Override
    public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " handleTimeout");
        return null;
    }

    @Override
    public <T> Object handleError(NativeWebRequest request, Callable<T> task, Throwable t) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " handleError");
        return null;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest request, Callable<T> task) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " afterCompletion");
    }
}
