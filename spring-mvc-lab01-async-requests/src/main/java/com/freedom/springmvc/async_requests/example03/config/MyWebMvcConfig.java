package com.freedom.springmvc.async_requests.example03.config;

import com.freedom.springmvc.async_requests.example03.interceptor.MyAsyncHandlerInerceptor;
import com.freedom.springmvc.async_requests.example03.interceptor.MyCallableProcessingInterceptor;
import com.freedom.springmvc.async_requests.example03.interceptor.MyDeferredResultProcessingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册添加AsyncHandlerInerceptor
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyAsyncHandlerInerceptor());
    }


    /**
     * 添加DeferredResultProcessingInterceptor、CallableProcessingInterceptor
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.registerDeferredResultInterceptors(new MyDeferredResultProcessingInterceptor())
                  .registerCallableInterceptors(new MyCallableProcessingInterceptor());
    }

}
