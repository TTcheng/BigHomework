package com.wangchuncheng.config;

import java.io.Serializable;

/**
 * Data source properties.
 * Design pattern:Single instance
 */
public class DataProperties implements Serializable {
    private static DataProperties dataProperties = new DataProperties();

    private String dbName;
    private String retention;
    private int batchNum;
    private String userName;
    private String password;
    private String url;
    private String topicName;

    //private constructor
    private DataProperties() {
    }

    //get instance
    public static DataProperties getDataProperties() {
        return dataProperties;
    }

    //getter and setter
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getRetention() {
        return retention;
    }

    public void setRetention(String retention) {
        this.retention = retention;
    }

    public int getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(int batchNum) {
        this.batchNum = batchNum;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "URL:"+url+"\n"+
                "dbName:"+dbName+"\n"+
                "userName:"+userName+"\n"+
                "password:"+password+"\n"+
                "topicName:"+topicName+"\n"+
                "";
    }
}
