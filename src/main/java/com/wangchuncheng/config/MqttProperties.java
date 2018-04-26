package com.wangchuncheng.config;

import java.io.Serializable;

public class MqttProperties implements Serializable {
    static MqttProperties mqttProperties = new MqttProperties();
    private String userName;
    private String password;
    private String brokerURL;
    private String pubTopic;
    private String subTopic;
    private int qos;

    private MqttProperties() {
    }

    public static MqttProperties getMqttProperties() {
        return mqttProperties;
    }

    public static void setMqttProperties(MqttProperties mqttProperties) {
        MqttProperties.mqttProperties = mqttProperties;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrokerURL() {
        return brokerURL;
    }

    public void setBrokerURL(String brokerURL) {
        this.brokerURL = brokerURL;
    }

    public String getPubTopic() {
        return pubTopic;
    }

    public void setPubTopic(String pubTopic) {
        this.pubTopic = pubTopic;
    }

    public String getSubTopic() {
        return subTopic;
    }

    public void setSubTopic(String subTopic) {
        this.subTopic = subTopic;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    @Override
    public String toString() {
        return "brokerURL:"+brokerURL+"\n"+
                "userName:"+userName+"\n"+
                "password:"+password+"\n"+
                "pubTopic:"+pubTopic+"\n"+
                "subTopic:"+subTopic+"\n"+
                "qos:"+qos+"\n";
    }
}
