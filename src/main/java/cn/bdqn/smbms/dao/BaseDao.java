package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.util.ConfigManager;

import java.sql.*;

public class BaseDao {
    /**
     * 获取链接
     *
     * @return
     */
    public static Connection getConnection() {
        String driver = ConfigManager.getInstance().getValue("driver");
        String url = ConfigManager.getInstance().getValue("url");
        String user = ConfigManager.getInstance().getValue("user");
        String password = ConfigManager.getInstance().getValue("password");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
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
