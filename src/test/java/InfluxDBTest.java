import com.wangchuncheng.Initializer;
import com.wangchuncheng.config.DataProperties;
import com.wangchuncheng.entity.HomeData;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.sql.Timestamp;
import java.util.List;

public class InfluxDBTest {
    public static void main(String[] args) {
        new Initializer().init();
//        HomeData homeData = new HomeData(true,false,"101",25.0,34,40000,new Date().getTime());
        String url = DataProperties.getDataProperties().getUrl();
        String user = DataProperties.getDataProperties().getUserName();
        String password = DataProperties.getDataProperties().getPassword();
        InfluxDB influxDB = InfluxDBFactory.connect(url, user, password);
        String command = "SELECT * FROM homedata WHERE homeId='101' LIMIT 1";
        String dbName = "homedata";
        QueryResult queryResult = influxDB.query(new Query(command,dbName));
        List<QueryResult.Result> results = queryResult.getResults();
        List<List<Object>> objList = results.get(0).getSeries().get(0).getValues();
        int size = objList.size();
        for (int i = 0; i < size; i++) {
            List<Object> fieldList = objList.get(i);
            HomeData data = new HomeData();
            //read field value
            String timeStr = (String) fieldList.get(0);
            double brightness = (double) fieldList.get(1);
            boolean hasHuman = (boolean) fieldList.get(2);
            String homeId = (String) fieldList.get(3);
            double humidity = (double) fieldList.get(4);
            boolean smoke = (boolean) fieldList.get(5);
            double temperature = (double) fieldList.get(6);
            //time format
            //timeStr 2018-04-25T07:02:18.119Z
            timeStr = timeStr.replace("T"," ");
//            timeStr = timeStr.replace("Z","");
            timeStr = timeStr.substring(0,timeStr.length()-1);
            Timestamp timestamp = Timestamp.valueOf(timeStr);
            long pointtime  = timestamp.getTime();
            //write to homedata
            data.setPointtime(pointtime);
            data.setHomeId(homeId);
            data.setTemperature(temperature);
            data.setHumidity(humidity);
            data.setBrightness(brightness);
            data.setHasHuman(hasHuman);
            data.setSmoke(smoke);

            System.out.println(data);
        }
    }
}
