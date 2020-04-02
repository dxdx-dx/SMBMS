package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.util.ConfigManager;

import java.sql.*;

public class BaseDao {
    static String driver = ConfigManager.getInstance().getValue("driver");
    static String url = ConfigManager.getInstance().getValue("url");
    static String user = ConfigManager.getInstance().getValue("user");
    static String password = ConfigManager.getInstance().getValue("password");


    /**
     * 加载驱动
     */
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
     * @return
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
     * @param connection
     * @param preparedStatement
     * @param resultSet
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static ResultSet executeQuery(Connection connection, PreparedStatement preparedStatement,
                                         ResultSet resultSet, String sql, Object[] params) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    /**
     * 增删改方法
     *
     * @param connection
     * @param preparedStatement
     * @param sql
     * @param params
     * @return
     * @throws SQLException
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
     * @param connection
     * @param preparedStatement
     * @param resultSet
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
