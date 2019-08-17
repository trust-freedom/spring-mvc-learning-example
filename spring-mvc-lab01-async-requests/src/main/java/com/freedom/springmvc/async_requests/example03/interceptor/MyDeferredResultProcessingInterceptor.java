package com.freedom.springmvc.async_requests.example03.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class MyDeferredResultProcessingInterceptor implements DeferredResultProcessingInterceptor {

    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " beforeConcurrentHandling");
    }

    @Override
    public <T> void preProcess(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " preProcess");
    }

    @Override
    public <T> void postProcess(NativeWebRequest request, DeferredResult<T> deferredResult, Object concurrentResult) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " postProcess");
    }

    @Override
    public <T> boolean handleTimeout(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " handleTimeout");
        return false;
    }

    @Override
    public <T> boolean handleError(NativeWebRequest request, DeferredResult<T> deferredResult, Throwable t) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " handleError");
        return false;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        log.info(request.getNativeRequest(HttpServletRequest.class).getRequestURI() + " afterCompletion");
    }
}
