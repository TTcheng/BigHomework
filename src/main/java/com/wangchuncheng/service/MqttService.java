package com.wangchuncheng.service;

import com.wangchuncheng.config.MqttProperties;
import com.wangchuncheng.controller.TaskExecutePool;
import com.wangchuncheng.entity.HomeData;
import org.eclipse.paho.client.mqttv3.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

public class MqttService {
    public static MqttService mqttService = new MqttService();

    MqttProperties mqttProperties = MqttProperties.getMqttProperties();
    private int qos = mqttProperties.getQos();
    private String broker = mqttProperties.getBrokerURL();
    private String userName = mqttProperties.getUserName();
    private String password = mqttProperties.getPassword();
    private String pubTopic = mqttProperties.getPubTopic();
    private String subTopic = mqttProperties.getSubTopic();
    private String clientId = "monitor_mqtt_java_" + UUID.randomUUID().toString();

    private MqttClient mqttClient;
    private MqttConnectOptions connOpts;
    private Executor executor;

    private MqttService() {
        executor = TaskExecutePool.getTaskExecutePool().getExecutor();
        connOpts = new MqttConnectOptions();
        connOpts.setUserName(userName);
        connOpts.setPassword(password.toCharArray());
        try {
            mqttClient = new MqttClient(broker, clientId);
        } catch (MqttException e) {
            System.out.println("mqttClient = new MqttClient(broker, clientId) ");
            e.printStackTrace();
        }
        mqttClient.setCallback(new MyMqttCallback());
        connect();
//        executor.execute(() -> connect());
    }

    public void connect() {
        try {
            mqttClient.connect(connOpts);
            System.out.println("连接建立成功");
            mqttClient.subscribe(subTopic);
            System.out.println("Subscribed to topic: " + subTopic);
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("连接建立失败");
        }
    }

    private void pub(String msg, String topic) {
        MqttMessage message = new MqttMessage(msg.getBytes());
        message.setQos(qos);
        message.setRetained(false);
        try {
            mqttClient.publish(topic, message);
        } catch (MqttException e) {
            e.printStackTrace();
            System.out.println("发布失败！");
        }
    }

    public int publishHomeData(List<HomeData> homeDataList) {
        if (mqttClient != null) {

        } else {
            connect();
            System.out.println("mqtt client is null 开始重连！");
        }
        for (int i = 0; i < homeDataList.size(); i++) {
            pub(homeDataList.get(i).toString(), pubTopic);
        }
        return 0;
    }

    public static MqttService getMqttService() {
        return mqttService;
    }
}


class MyMqttCallback implements MqttCallback {

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection lost!!!!!!!!!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String msg = new String(mqttMessage.getPayload());
        System.out.println("MQTT message received: " + msg);

        Executor executor = TaskExecutePool.getTaskExecutePool().getExecutor();
        executor.execute(()->{
            InfluxdbService influxdbService = InfluxdbService.getInfluxdbService();
            influxdbService.connect();

            String[] queries = msg.split("%");  //homeId & limit num
            List<HomeData> gasEvents = influxdbService.query("homedata",queries[0], Long.parseLong(queries[1]));
            MqttService.getMqttService().publishHomeData(gasEvents);
        });
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
