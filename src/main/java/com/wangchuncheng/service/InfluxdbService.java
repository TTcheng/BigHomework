package com.wangchuncheng.service;

import com.wangchuncheng.config.DataProperties;
import com.wangchuncheng.entity.HomeData;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Influxdb Service.
 * this class storage home data to influxdb and query from influxdb.
 * Design pattern:Single instance
 */
public class InfluxdbService {// Serializable {
    public static InfluxdbService influxdbService = new InfluxdbService();
    public static final String MEASUREMENTS = "homedatas";

    private DataProperties dataProperties = DataProperties.getDataProperties();

    private String url = dataProperties.getUrl();
    private String dbName = dataProperties.getDbName();
    private String user = dataProperties.getUserName();
    private String password = dataProperties.getPassword();
    private String retention = dataProperties.getRetention();
    private int batchNum = dataProperties.getBatchNum();

    private boolean connected = false;

    InfluxDB influxDB;//InfluxDBFactory.connect(url, user, password);
    BatchPoints batchPoints;

    private InfluxdbService() {
    }

    /**
     * 将给定home data写入influxdb的数据表
     *
     * @param data
     * @param measurement
     * @return status code:int
     */
    synchronized public int writeToInfluxdb(HomeData data, String measurement) {

        // Flush every 2000 Points, at least every 1000ms
        // influxDB.enableBatch(1, 1, TimeUnit.MILLISECONDS);


        Point point = Point.measurement(measurement)
                .time(data.getPointtime(), TimeUnit.MILLISECONDS)
                .addField("homeId", data.getHomeId())
                .addField("temperature", data.getTemperature())
                .addField("humidity", data.getHumidity())
                .build();
        batchPoints = BatchPoints
                .database(dbName)
//                .retentionPolicy(retention)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();
        batchPoints.point(point);
        influxDB.write(batchPoints);
        return 0;
    }

    /**
     * select * from measurement where homeid = @param2 and time between begin and end
     *
     * @param measurement
     * @param homeId
     * @param begin
     * @param end
     * @return homedataList
     */
    public List<HomeData> query(String measurement, String homeId, long begin, long end) {
        begin*=1000000;
        end*=1000000;

        List<HomeData> homeDataList = new LinkedList<HomeData>();
        String command = "select * from measurement" + measurement.trim() + "  where homeId='" + homeId + "' and time>" + begin + " and time<" + end;
        QueryResult queryResult = influxDB.query(new Query(command, dbName));

        return processQueryResults(queryResult);
    }

    /**
     * 查询前　@limit　条数据
     * @param measurement
     * @param homeId
     * @param limit
     * @return homedataList
     */
    public List<HomeData> query(String measurement, String homeId, long limit) {
        String command = "SELECT * FROM " + measurement.trim() + "  WHERE homeId='" + homeId + "' LIMIT " + limit;
        QueryResult queryResult = influxDB.query(new Query(command, dbName));

        return processQueryResults(queryResult);
    }

    /**
     * 处理query函数的查询结果
     * @param queryResult
     * @return home data list.
     */
    public List<HomeData> processQueryResults(QueryResult queryResult) {
        List<HomeData> homeDataList = new LinkedList<>();
        if (queryResult.getResults().isEmpty()) {
            System.out.println("查询结果为空");
            return null;
        } else {
            List<QueryResult.Result> results = queryResult.getResults();
            List<List<Object>> objList = results.get(0).getSeries().get(0).getValues();
            int size = objList.size();

            for (int i = 0; i < size; i++) {
                HomeData data = new HomeData();

                List<Object> fieldList = objList.get(i);
                String timeStr = (String) fieldList.get(0);
                //timeStr 2018-04-25T07:02:18.119Z
                timeStr = timeStr.replace('T', ' ');
                timeStr = timeStr.substring(0,timeStr.length()-1);
                System.out.println("Time timeStr is :" + timeStr);
                Timestamp timestamp = Timestamp.valueOf(timeStr);
                data.setPointtime(timestamp.getTime());
                String homeId = (String) fieldList.get(3);
                double humidity = (double) fieldList.get(4);
                double temperature = (double) fieldList.get(6);
                data.setPointtime(timestamp.getTime());
                data.setHomeId(homeId);
                data.setTemperature(temperature);
                data.setHumidity(humidity);
                System.out.println(data);
                homeDataList.add(data);
            }
        }
        return homeDataList;
    }

    //    init
    public void connect() {
        influxDB = InfluxDBFactory.connect(url, user, password);
    }//end of initPara

    public static InfluxdbService getInfluxdbService() {
        return influxdbService;
    }

}//end of class