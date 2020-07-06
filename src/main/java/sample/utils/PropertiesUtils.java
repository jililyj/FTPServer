package sample.utils;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

    private static PropertiesUtils propertiesUtils = new PropertiesUtils();

    private static Map<String, String> map = Maps.newHashMap();

    public static PropertiesUtils getInstance() {
        return propertiesUtils;
    }

    /**
     * 写入properties信息
     *
     * @param key
     *            名称
     * @param value
     *            值
     */
    public static void setProperties(String key, String value) {
        try {
            // 从输入流中读取属性列表（键和元素对）
            Properties prop = getProperties();
            prop.setProperty(key, value);
            FileOutputStream outputFile=new FileOutputStream(Thread.currentThread().getContextClassLoader().getResource("sample/server.properties").getFile());
            prop.store(outputFile, "modify");
            outputFile.close();
            outputFile.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回　Properties
     * @param
     * @return
     */
    public static Properties getProperties(){
        Properties prop = new Properties();
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("sample/server.properties");
            prop.load(is);
        } catch (Exception e) {
            return null;
        }
        return prop;
    }

    public static String getValue(String key) {
        String value = map.get(key);
        if (value == null){
            value = getProperties().getProperty(key);
            map.put(key, value != null ? value : StringUtils.EMPTY);
        }
        return value;
    }



}
