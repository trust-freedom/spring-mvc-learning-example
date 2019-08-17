package com.freedom.springmvc.async_requests.example03.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@RestController
@RequestMapping("/async-requests/example03")
public class Example03Controller {

    //============================= 返回DeferredResult

    private static Map<String, DeferredResult<String>> deferredResultMap = new HashMap<>();

    /**
     * 返回DeferredResult，客户端挂起
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


    @RequestMapping("/deferredResult/onEvent/{key}")
    public DeferredResult<String> deferredResultOnEvent(@PathVariable("key") String key){
        DeferredResult<String> deferredResult = new DeferredResult<String>();
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                log.info("deferredResult.onTimeout()");
            }
        });
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                log.info("deferredResult.onCompletion()");
            }
        });


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

    @RequestMapping("/callable/onEvent")
    public WebAsyncTask<String> callableOnEvent(){
        log.info("callableOnEvent");

        Callable<String> callable =  new Callable<String>() {
            public String call() throws Exception {
                log.info("callable.call() start");
                // 业务逻辑
                TimeUnit.SECONDS.sleep(5);

                log.info("callable.call() prepare to return");
                return "callableOnEvent";
            }
        };

        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<String>(callable);
        webAsyncTask.onTimeout(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("webAsyncTask.onTimeout()");
                return "webAsyncTask.onTimeout()";
            }
        });
        webAsyncTask.onCompletion(new Runnable() {
            @Override
            public void run() {
                log.info("webAsyncTask.onCompletion()");
            }
        });
        webAsyncTask.onError(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("webAsyncTask.onError()");
                return "webAsyncTask.onError()";
            }
        });

        return webAsyncTask;
    }

}
