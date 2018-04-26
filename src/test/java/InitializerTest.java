import com.wangchuncheng.Initializer;
import com.wangchuncheng.config.DataProperties;
import com.wangchuncheng.config.MqttProperties;

public class InitializerTest {
    public static void main(String[] args) {
        new Initializer().init();
        DataProperties dataProperties = DataProperties.getDataProperties();
        MqttProperties mqttProperties = MqttProperties.getMqttProperties();
        System.out.println(dataProperties);
        System.out.println(mqttProperties);
    }
}
