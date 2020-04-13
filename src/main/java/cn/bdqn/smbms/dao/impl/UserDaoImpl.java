package cn.bdqn.smbms.dao.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.UserDao;
import cn.bdqn.smbms.pojo.User;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户dao层实现类
 */
@Repository
public class UserDaoImpl implements UserDao {
    /**
     * 根据userCode查询用户
     *
     * @param connection 连接对象
     * @param userCode   用户编码
     * @return 用户对象
     * @throws SQLException sql异常
     */
    @Override
    public List<User> findByUserCode(Connection connection, String userCode) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT * FROM `smbms_user` WHERE `userCode`=?";
        Object[] params = {userCode};
        List<User> userList = new ArrayList<>();
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql, params);
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getDate("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getDate("modifyDate"));
            userList.add(user);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return userList;
    }

    /**
     * 分页查询用户
     *
     * @param connection 连接对象
     * @param userName   用户名称
     * @param userRole   用户角色
     * @param pageNo     当前页
     * @param pageSize   页大小
     * @return 用户集合
     * @throws SQLException sql异常
     */
    @Override
    public List<User> findByPage(Connection connection, String userName, Integer userRole, Integer pageNo, Integer pageSize) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<>();
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.*,r.roleName FROM `smbms_role` r,`smbms_user` u WHERE r.id=u.`userRole`");
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append(" AND `userName` LIKE CONCAT('%',?,'%')");
            paramsList.add(userName);
        }
        if (userRole > 0) {
            sql.append(" AND `userRole`=?");
            paramsList.add(userRole);
        }
        sql.append(" ORDER BY `creationDate`DESC LIMIT ?,? ");
        paramsList.add(pageNo);
        paramsList.add(pageSize);
        System.out.println("=====================sql.toString()===========================");
        System.out.println(sql.toString());
        System.out.println("=====================userName===========================");
        System.out.println(userName + "+++" + userRole + "+++" + pageNo + "+++" + pageSize);
        System.out.println("=====================paramsList===========================");
        System.out.println(paramsList.toArray());
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        User user;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getDate("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getDate("modifyDate"));
            user.setUserRoleName(resultSet.getString("roleName"));
            userList.add(user);
        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return userList;
    }

    /**
     * 查询用户总条数
     *
     * @param connection 连接对象
     * @param userName   用户名
     * @param userRole   用户角色
     * @return 记录数
     * @throws SQLException sql异常
     */
    @Override
    public int findByPageCount(Connection connection, String userName, Integer userRole) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int result = 0;
        List<Object> paramsList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(1) FROM `smbms_user` u WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(userName)) {
            sql.append("AND `userName` LIKE CONCAT('%',?,'%')");
            paramsList.add(userName);
        }
        if (userRole > 0) {
            sql.append("AND u.`userRole`=?");
            paramsList.add(userRole);
        }
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql.toString(), paramsList.toArray());
        while (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        return result;
    }

    /**
     * 添加用户
     *
     * @param connection 连接对象
     * @param user       用户对象
     * @return 受影响行数
     * @throws SQLException sql异常
     */
    @Override
    public int adduser(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result = 0;
        String sql = "INSERT INTO `smbms`.`smbms_user` (`userCode`,`userName`, `userPassword`, `gender`,`birthday`, " +
                "`phone`, `address`,`userRole`,`createdBy`,`creationDate`) \n" +
                "  VALUES( ?, ?, ?, ?,?, ?,?,?,?,?) ";
        Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(), user.getGender(),
                user.getBirthday(), user.getPhone(), user.getAddress(), user.getUserRole(),
                user.getCreatedBy(), user.getCreationDate()};
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }

    /**
     * 根据id查询用户
     *
     * @param connection 连接对象
     * @param id         用户编号
     * @return 用户对象
     */
    @Override
    public User findUserById(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT u.* ,r.`roleName` FROM `smbms_user` u,`smbms_role` r WHERE u.`userRole`= r.`id` AND u.id=?";
        Object[] params = {id};
        resultSet = BaseDao.executeQuery(connection, preparedStatement, resultSet, sql, params);
        User user = null;
        while (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUserCode(resultSet.getString("userCode"));
            user.setUserName(resultSet.getString("userName"));
            user.setUserPassword(resultSet.getString("userPassword"));
            user.setGender(resultSet.getInt("gender"));
            user.setBirthday(resultSet.getDate("birthday"));
            user.setPhone(resultSet.getString("phone"));
            user.setAddress(resultSet.getString("address"));
            user.setUserRole(resultSet.getInt("userRole"));
            user.setCreatedBy(resultSet.getInt("createdBy"));
            user.setCreationDate(resultSet.getDate("creationDate"));
            user.setModifyBy(resultSet.getInt("modifyBy"));
            user.setModifyDate(resultSet.getDate("modifyDate"));
            user.setUserRoleName(resultSet.getString("roleName"));

        }
        BaseDao.closeResource(null, preparedStatement, resultSet);
        return user;
    }

    /**
     * 修改用户
     *
     * @param connection 连接对象
     * @param user       用户对象
     * @return 受影响行数
     * @throws SQLException SQL异常
     */
    @Override
    public int modifyUser(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result;
        String sql = "UPDATE   `smbms`.`smbms_user` SET `userName` = ?," +
                "  `gender` = ?,  `birthday` = ?,  `phone` =?,  `address` = ?," +
                "  `userRole` =?, `modifyBy` =?,`modifyDate` = ? WHERE `id` =?";
        Object[] params = {user.getUserName(), user.getGender(), user.getBirthday(), user.getPhone(),
                user.getAddress(), user.getUserRole(), user.getModifyBy(), user.getModifyDate(), user.getId()};
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }

    /**
     * 删除用户
     *
     * @param connection
     * @param id
     * @return
     * @throws SQLException
     */
    @Override
    public int deluser(Connection connection, Integer id) throws SQLException {
        PreparedStatement preparedStatement = null;
        int result;
        Object[] params = {id};
        String sql = "DELETE FROM`smbms`.`smbms_user` WHERE `id` = ? ";
        result = BaseDao.executeUpdate(connection, preparedStatement, sql, params);
        BaseDao.closeResource(null, preparedStatement, null);
        return result;
    }
}
