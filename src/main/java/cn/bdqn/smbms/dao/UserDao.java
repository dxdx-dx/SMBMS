package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户dao层
 */
public interface UserDao {
    /**
     * 根据UserCode查询用户
     *
     * @param connection 连接对象
     * @param userCode   用户名Code
     * @return 用户
     * @throws SQLException 异常
     */
    List<User> findByUserCode(Connection connection, String userCode) throws SQLException;

    /**
     * 分页查询用户列表
     *
     * @param connection 连接对象
     * @param userName   用户名
     * @param userRole   用户角色
     * @param pageNo     当前页
     * @param pageSize   页大小
     * @return 用户列表
     * @throws SQLException 异常
     */

    List<User> findByPage(Connection connection, String userName, Integer userRole, Integer pageNo, Integer pageSize) throws SQLException;

    /**
     * 查询用户总条数
     *
     * @param connection 连接对象
     * @param userName   用户名
     * @param userRole   用户角色
     * @return 总记录数
     * @throws SQLException 异常
     */
    int findByPageCount(Connection connection, String userName, Integer userRole) throws SQLException;

    /**
     * 添加用户
     *
     * @param connection 连接对象
     * @param user       用户对象
     * @return 受影响行数
     * @throws SQLException 异常
     */
    int adduser(Connection connection, User user) throws SQLException;

    /**
     * 根据id查询用户
     *
     * @param connection 连接对象
     * @param id         用户id
     * @return user对象
     */
    User findById(Connection connection, Integer id) throws SQLException;

}
