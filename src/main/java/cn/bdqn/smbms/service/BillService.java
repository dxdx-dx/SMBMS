package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单业务逻辑层
 */
public interface BillService {
    //分页查询订单列表
    List<Bill> findByPage(String productName, Integer providerId, Integer isPayment, Integer pageNo, Integer pageSize);

    //查询订单总条数
    int findByPageCount(String productName, Integer providerId, Integer isPayment);

    //根据id查询订单
    Bill findBillById(Integer id);

    //删除订单
    boolean delbill(Integer id);

    //修改订单
    boolean billModify(Bill bill);

    //添加订单
    boolean addBill(Bill bill);
}
