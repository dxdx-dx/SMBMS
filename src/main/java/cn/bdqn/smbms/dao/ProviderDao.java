package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 供应商Dao层
 */
public interface ProviderDao {
    //分页查询供应商列表
    List<Provider> findByPage(Connection connection, String proCode, String proName, Integer pageNo, Integer pageSize) throws SQLException;

    //查询供应商总条数
    int findByPageCount(Connection connection, String proCode, String proName) throws SQLException;

    //查询所有供应商名称
    List<Provider> findName(Connection connection) throws SQLException;

    //添加供应商
    int addPrivider(Connection connection, Provider provider) throws SQLException;

    //根据id查询供应商
    Provider findProviderById(Connection connection, Integer id) throws SQLException;

    //修改供应商
    int providerModify(Connection connection, Provider provider) throws SQLException;

    //删除供应商
    int delprovider(Connection connection, Integer id) throws SQLException;

}
