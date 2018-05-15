package com.wangchuncheng.controller;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Task Execute Pool
 */
public class TaskExecutePool {
    static TaskExecutePool taskExecutePool = new TaskExecutePool();
    Executor executor;

    private TaskExecutePool(){
        initExecutor();
    }

    public void initExecutor() {
        executor = Executors.newFixedThreadPool(4);//new ThreadPoolExecutor(100,100,1000,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));
    }

    public Executor getExecutor() {
        return executor;
    }

    public static TaskExecutePool getTaskExecutePool() {
        return taskExecutePool;
    }
}