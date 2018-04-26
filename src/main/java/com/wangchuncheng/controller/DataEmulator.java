package com.wangchuncheng.controller;

import com.wangchuncheng.entity.HomeData;
import com.wangchuncheng.service.InfluxdbService;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;

public class DataEmulator implements Runnable{
    private InfluxdbService influxdbService = InfluxdbService.getInfluxdbService();

    public DataEmulator() {
    }

    public void run() {
        int maxtime = 10000000;
        while (maxtime>0) {
            emulateHomedata();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            maxtime--;
        }
    }

    /**
     * 产生随机homedata
     */
    public void emulateHomedata() {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        HomeData homeData = new HomeData();
        Random random = new Random();

        int homeId = (int) (Math.random()*12);      //12 rooms per floor
        boolean hasHuman = random.nextBoolean();
        boolean smoke = random.nextBoolean();
        double temperature = Math.random()*120-40;  //-40~80℃
        double humidity = Math.random()*100;        //0~100%RH Relative Humidity
        double brightness = Math.random()*200000;    //0.000001~200000lux

        homeData.setHasHuman(hasHuman);
        homeData.setSmoke(smoke);
        homeData.setPointtime(new Date().getTime());
        homeData.setHomeId(preHomeIds[(int) (Math.random()*20)]+homeId);
        homeData.setTemperature(Double.parseDouble(decimalFormat.format(temperature)));
        homeData.setHumidity(Double.parseDouble(decimalFormat.format(humidity)));
        homeData.setBrightness(brightness);

        System.out.println("emulated : "+homeData);
        /**
         * measurement : homedata
         */
        influxdbService.connect();
        influxdbService.writeToInfluxdb(homeData, "homedata");
    }

    public String[] preHomeIds = new String[]{      //20 floors
            "1-", "2-", "3-", "4-", "5-", "6-", "7-", "8-", "9-", "10-",
            "11-", "12-", "13-", "14-", "15-", "16-", "17-", "18-", "19-", "20-"
    };
}