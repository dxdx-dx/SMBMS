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
    //分页查询订单列表
    List<Bill> findByPage(Connection connection, String productName, Integer providerId, Integer isPayment, Integer pageNo, Integer pageSize) throws SQLException;

    //查询订单总条数
    int findByPageCount(Connection connection, String productName, Integer providerId, Integer isPayment) throws SQLException;

    //根据id查询订单
    Bill findBillById(Connection connection, Integer id) throws SQLException;

    //删除订单
    int delbill(Connection connection, Integer id) throws SQLException;

    //修改订单
    int billModify(Connection connection, Bill bill) throws SQLException;

    //添加订单
    int addBill(Connection connection, Bill bill) throws SQLException;

}
