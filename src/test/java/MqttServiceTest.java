import com.wangchuncheng.Initializer;
import com.wangchuncheng.entity.HomeData;
import com.wangchuncheng.service.MqttService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MqttServiceTest {
    public static void main(String[] args) {
        new Initializer().init();
        MqttService mqttService = MqttService.getMqttService();
        HomeData homeData = new HomeData(true,false,"101",25.0,34,40000,new Date().getTime());
        List data = new LinkedList<HomeData>();
        data.add(homeData);
        data.add(homeData);
        data.add(homeData);
        data.add(homeData);
        data.add(homeData);
        data.add(homeData);
        mqttService.publishHomeData(data);
    }
}
