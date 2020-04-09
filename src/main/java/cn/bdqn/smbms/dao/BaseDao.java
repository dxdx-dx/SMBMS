package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.util.ConfigManager;

import java.sql.*;

/**
 * 数据库连接帮助类
 */
public class BaseDao {
    static String driver = ConfigManager.getInstance().getValue("driver");//驱动
    static String url = ConfigManager.getInstance().getValue("url");//路径
    static String user = ConfigManager.getInstance().getValue("user");//用户名
    static String password = ConfigManager.getInstance().getValue("password");//密码


    //加载驱动
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取链接
     *
     * @return 数据库连接对象
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 查询方法
     *
     * @param connection        连接对象
     * @param preparedStatement preparedStatement对象
     * @param resultSet         结果集对象
     * @param sql               SQL语句
     * @param params            参数数组
     * @return resultSet结果集
     * @throws SQLException 异常
     */
    public static ResultSet executeQuery(Connection connection, PreparedStatement preparedStatement,
                                         ResultSet resultSet, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        System.out.println("----------------------------------------------------------------------");
        System.out.println(sql);
        System.out.println("----------------------------------------------------------------------");
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    /**
     * 增删改方法
     *
     * @param connection        连接对象
     * @param preparedStatement preparedStatement对象
     * @param sql               sql语句
     * @param params            参数数组
     * @return 受影响行数
     * @throws SQLException 异常
     */
    public static int executeUpdate(Connection connection, PreparedStatement preparedStatement,
                                    String sql, Object[] params) throws SQLException {
        int result = 0;
        preparedStatement = connection.prepareStatement(sql);

        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        result = preparedStatement.executeUpdate();
        return result;

    }

    /**
     * 关闭资源
     *
     * @param connection        连接对象
     * @param preparedStatement preparedStatement对象
     * @param resultSet         resultSet对象
     */
    public static void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
