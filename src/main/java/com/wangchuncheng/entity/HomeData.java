package com.wangchuncheng.entity;

import java.io.Serializable;

/**
 * Data entity class.
 */
public class HomeData implements Serializable {
    //    private boolean hasHuman;   //人      true false
//    private boolean smoke;      //烟雾    true false
    private String homeId;      //房间号    1-01 ~ 20-12
    private double temperature; //温度    -40~80℃
    private double humidity;    //湿度    相对湿度0~100％RH
    //    private double brightness;  //光照度   0.000001~200000lux
    private long pointtime;

    public HomeData() {
    }

    /**
     * constructor with all param
     *
     * @param homeId
     * @param temperature
     * @param humidity
     * @param pointtime
     */
    public HomeData(String homeId, double temperature, double humidity, long pointtime) {
        this.homeId = homeId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pointtime = pointtime;
    }
    /*
    public HomeData(boolean hasHuman, boolean smoke, String homeId, double temperature, double humidity, double brightness, long pointtime) {
        this.hasHuman = hasHuman;
        this.smoke = smoke;
        this.homeId = homeId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.brightness = brightness;
        this.pointtime = pointtime;
    }*/

    //getter and setter

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public long getPointtime() {
        return pointtime;
    }

    public void setPointtime(long pointtime) {
        this.pointtime = pointtime;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    @Override
    public String toString() {
        return "HomeData{" +
                "homeId='" + homeId + '\'' +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                ", pointtime=" + pointtime +
                '}';
    }
}
