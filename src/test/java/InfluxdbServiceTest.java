import com.wangchuncheng.Initializer;
import com.wangchuncheng.entity.HomeData;
import com.wangchuncheng.service.InfluxdbService;

import java.util.Date;
import java.util.List;

public class InfluxdbServiceTest {

    public static void main(String[] args) {

        new Initializer().init();
        InfluxdbService influxdbService = InfluxdbService.getInfluxdbService();
        influxdbService.connect();
        //write data
//        HomeData homeData = new HomeData(true,false,"101",25.0,34,40000,new Date().getTime());
//        influxdbService.writeToInfluxdb(homeData,"homedata");
        //query data
        List dataList = influxdbService.query("homedata","101",1);
        for (int i = 0; i < dataList.size(); i++) {
            System.out.println(dataList.get(i));
        }
    }
}
