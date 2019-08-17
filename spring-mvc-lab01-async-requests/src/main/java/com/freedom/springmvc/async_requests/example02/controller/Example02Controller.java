package com.freedom.springmvc.async_requests.example02.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/async-requests/example02")
public class Example02Controller {

    //============================= 返回DeferredResult

    private static Map<String, DeferredResult<String>> deferredResultMap = new HashMap<>();

    /**
     * 正常返回DeferredResult，客户端挂起
     * @param key
     * @return
     */
    @RequestMapping("/deferredResult/{key}")
    public DeferredResult<String> deferredResult(@PathVariable("key") String key){
        DeferredResult<String> deferredResult = new DeferredResult<String>();

        // Save the deferredResult somewhere..
        deferredResultMap.put(key, deferredResult);
        log.info("deferredResult key:" + key);

        return deferredResult;
    }


    /**
     * 调用deferredResult.setResult()，返回异步执行结果
     * @param key
     * @return
     */
    @RequestMapping("/deferredResult/setResult/{key}")
    public String invokeSetResult(@PathVariable("key") String key){
        DeferredResult<String> deferredResult = deferredResultMap.get(key);

        if(deferredResult!=null){
            deferredResult.setResult(key + " invoke at:" + System.currentTimeMillis());
            log.info("invokeSetResult key:" + key);
            deferredResultMap.remove(key);
        }

        return "success";
    }


    /**
     * 调用deferredResult.setErrorResult()，返回正常的错误，非Exception
     * @param key
     * @return
     */
    @RequestMapping("/deferredResult/setErrorResult/normal/{key}")
    public String invokeSetErrorResultNormal(@PathVariable("key") String key){
        DeferredResult<String> deferredResult = deferredResultMap.get(key);

        if(deferredResult!=null){
            deferredResult.setErrorResult("ErrorResult " + key + " invoke at:" + System.currentTimeMillis());
            log.info("invokeSetErrorResult normal key:" + key);
            deferredResultMap.remove(key);
        }

        return "error normal result";
    }


    /**
     * 调用deferredResult.setErrorResult()，返回Exception
     * 异常由全局异常处理器捕获，返回500状态码+全局异常信息
     * @param key
     * @return
     */
    @RequestMapping("/deferredResult/setErrorResult/exception/{key}")
    public String invokeSetErrorResultException(@PathVariable("key") String key){
        DeferredResult<String> deferredResult = deferredResultMap.get(key);

        if(deferredResult!=null){
            deferredResult.setErrorResult(new NullPointerException());
            log.info("invokeSetErrorResult exception key:" + key);
            deferredResultMap.remove(key);
        }

        return "error exception result";
    }


    //============================= 返回Callable

    @RequestMapping("/callable")
    public Callable<String> callable(){
        log.info("callable");
        return new Callable<String>() {
            public String call() throws Exception {
                log.info("callable.call() start");
                // 业务逻辑
                TimeUnit.SECONDS.sleep(5);

                log.info("callable.call() prepare to return");
                return "callable";
            }
        };
    }

    /**
     * callable 直接上抛异常
     * 也会由全局异常处理器捕获
     * @return
     */
    @RequestMapping("/callableRaiseException")
    public Callable<String> callableRaiseException(){
        log.info("callableRaiseException");
        return new Callable<String>() {
            public String call() throws Exception {
                log.info("callableRaiseException.call() start");
                // 业务逻辑
                TimeUnit.SECONDS.sleep(5);

                log.info("callableRaiseException.call() prepare to raise exception");

                // 模拟上抛异常
                if(true){
                    throw new NullPointerException();
                }

                return "callableRaiseException";
            }
        };
    }

}
