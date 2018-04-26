package com.wangchuncheng;

import com.wangchuncheng.config.DataProperties;
import com.wangchuncheng.config.MqttProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Initializer {
    private DataProperties dataProperties = DataProperties.getDataProperties();
    private MqttProperties mqttProperties = MqttProperties.getMqttProperties();
    private String datastore = "datastore.";
    private String mqtt = "mqtt.";
    Properties properties;// = new Properties();

    public Initializer() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取配置文件失败");
        }
    }

    public void init() {
        initDataProperties();
        initMqttProperties();
    }

    private void initDataProperties() {
        dataProperties.setUrl(properties.getProperty(datastore + "url"));
        dataProperties.setDbName(properties.getProperty(datastore + "dbName"));
        dataProperties.setUserName(properties.getProperty(datastore + "userName"));
        dataProperties.setPassword(properties.getProperty(datastore + "password"));
        dataProperties.setRetention(properties.getProperty(datastore + "retention"));
        dataProperties.setBatchNum(Integer.parseInt(properties.getProperty(datastore + "batchNum")));
        dataProperties.setTopicName(properties.getProperty(datastore + "topicName"));
    }

    private void initMqttProperties() {
        mqttProperties.setBrokerURL(properties.getProperty(mqtt + "brokerURL"));
        mqttProperties.setUserName(properties.getProperty(mqtt + "userName"));
        mqttProperties.setPassword(properties.getProperty(mqtt + "password"));
        mqttProperties.setSubTopic(properties.getProperty(mqtt + "subTopic"));
        mqttProperties.setPubTopic(properties.getProperty(mqtt + "pubTopic"));
        mqttProperties.setQos(Integer.parseInt(properties.getProperty(mqtt + "qos")));
    }
}
