package com.wangchuncheng;

import com.wangchuncheng.controller.DataEmulator;
import com.wangchuncheng.controller.TaskExecutePool;

import java.util.concurrent.Executor;

public class Main {
    public static void main(String[] args) {
        new Initializer().init();
        Executor executor = TaskExecutePool.getTaskExecutePool().getExecutor();
        executor.execute(new DataEmulator());
    }
}
