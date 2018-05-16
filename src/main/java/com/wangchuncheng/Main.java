package com.wangchuncheng;

import com.wangchuncheng.controller.DataEmulator;
import com.wangchuncheng.controller.TaskExecutePool;
import com.wangchuncheng.service.MqttService;

import java.util.concurrent.Executor;

public class Main {
    public static void main(String[] args) {
        //init properties
        new Initializer().init();
        //open mqtt service
        MqttService mqttService = MqttService.getMqttService();

        //start emulating
        Executor executor = TaskExecutePool.getTaskExecutePool().getExecutor();
        executor.execute(new DataEmulator());
    }
}
