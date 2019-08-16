package com.freedom.springmvc.async_requests.example01.controller;

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
@RequestMapping("/async-requests/example01")
public class Example01Controller {

    private static Map<String, DeferredResult<String>> deferredResultMap = new HashMap<>();

    /**
     * 返回DeferredResult，客户端挂起
     * @param key
     * @return
     */
    @RequestMapping("/deferredResult/{key}")
    public DeferredResult<String> deferredResultExample01(@PathVariable("key") String key){
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



    @RequestMapping("/callable")
    public Callable<String> callableExample01(){
        log.info("callableExample01");
        return new Callable<String>() {
            public String call() throws Exception {
                log.info("callableExample01.call() start");
                // 业务逻辑
                TimeUnit.SECONDS.sleep(10);

                log.info("callableExample01.call() prepare to return");
                return "callableExample01";
            }
        };
    }


}
