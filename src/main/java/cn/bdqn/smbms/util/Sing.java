package cn.bdqn.smbms.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 通过单例模式-饿汉模式
 * 读取数据库的配置文件
 */
public class Sing {
    private static Properties properties;
    private static Sing sing = new Sing();

    /**
     * 私有构造方法
     */
    private Sing() {
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
     * 对外提供的接口
     * 懒汉模式：延迟加载，线程不安全
     *
     * @return
     */
    public synchronized static Sing getInstance() {
        if (sing == null) sing = new Sing();
        return sing;
    }

    /**
     *饿汉模式：不延迟加载，线程安全
     */
    //    public static ConfigManager getInstance() {
    //        return configManager;
    //    }

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
