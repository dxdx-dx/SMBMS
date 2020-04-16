package cn.bdqn.smbms.dao;

import cn.bdqn.smbms.pojo.Bill;
import cn.bdqn.smbms.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单Dao层
 */
public interface BillDao {
    //分页查询供应商列表
    List<Bill> findByPage(Connection connection, String productName, Integer providerId, Integer isPayment, Integer pageNo, Integer pageSize) throws SQLException;

    //查询供应商总条数
    int findByPageCount(Connection connection, String productName, Integer providerId, Integer isPayment) throws SQLException;

}
