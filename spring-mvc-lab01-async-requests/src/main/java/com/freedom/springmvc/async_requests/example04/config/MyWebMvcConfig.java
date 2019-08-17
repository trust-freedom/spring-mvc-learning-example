package com.freedom.springmvc.async_requests.example04.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public ThreadPoolTaskExecutor myThreadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(20);
        threadPoolTaskExecutor.setMaxPoolSize(50);
        threadPoolTaskExecutor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("myThreadPoolTaskExecutor %d").build());

        return threadPoolTaskExecutor;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 设置异步任务的任务执行器（线程池）
        configurer.setTaskExecutor(myThreadPoolTaskExecutor());
        configurer.setDefaultTimeout(60 * 1000);
    }

}
