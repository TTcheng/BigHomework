package com.wangchuncheng.controller;

import com.wangchuncheng.entity.HomeData;
import com.wangchuncheng.service.InfluxdbService;
import com.wangchuncheng.service.MqttService;

import java.util.List;

/**
 * Query data from influx and Send it to client using MQTT
 */
public class DataSender implements Runnable {
    private long limit;// = 1;
    private String homeID;// = "101";
    InfluxdbService influxdbService = InfluxdbService.getInfluxdbService();

    public DataSender() {

    }

    @Override
    public void run() {
        influxdbService.connect();
        List<HomeData> homeDataList = influxdbService.query(InfluxdbService.MEASUREMENTS, homeID, limit);
        MqttService.getMqttService().publishHomeData(homeDataList);
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public void setHomeID(String homeID) {
        this.homeID = homeID;
    }
}
