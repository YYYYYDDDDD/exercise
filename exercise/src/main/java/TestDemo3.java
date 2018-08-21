import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class TestDemo3 {
    public static void main(String args[])
        throws IOException {
        Properties properties = new Properties();
        InputStreamReader inputStreamReader = new InputStreamReader(TestDemo3.class.getClass().getResourceAsStream("/info.properties"),"GBK");
        properties.load(inputStreamReader);
        Map<String, String> map = new HashMap<String, String>((Map) properties);
        inputStreamReader.close();
        DemoJsonBean demoJsonBean = (DemoJsonBean)JSONObject.toBean(JSONObject.fromObject(map),DemoJsonBean.class);
        System.out.println(demoJsonBean.getBasics_site());
    }
}
