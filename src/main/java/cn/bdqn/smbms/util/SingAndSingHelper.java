package cn.bdqn.smbms.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 通过静态内部类实现饿汉模式的延迟加载
 */
public class SingAndSingHelper {
    private static SingAndSingHelper singAndSingHelper;
    private static Properties properties;

    //私有构造器
    private SingAndSingHelper() {
        InputStream in = null;
        String name = "database.properties";
        try {
            in = ConfigManager.class.getClassLoader().getResourceAsStream(name);
            properties = new Properties();
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 静态内部类
     */
    public static class SingHelper {
        public static final SingAndSingHelper SING = new SingAndSingHelper();
    }

    /**
     * 对外提供的接口
     */
    public static SingAndSingHelper getInstance() {
        singAndSingHelper = SingHelper.SING;
        return singAndSingHelper;
    }

    /**
     * 对外提供的接口
     */
    public static SingAndSingHelper test() {
        return singAndSingHelper;
    }

    /**
     * 从properties对象中通过key获取value，例如：（user=root）
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
