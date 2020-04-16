package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单业务逻辑层
 */
public interface BillService {
    //分页查询供应商列表
    List<Bill> findByPage(String productName, Integer providerId, Integer isPayment, Integer pageNo, Integer pageSize);

    //查询供应商总条数
    int findByPageCount(String productName, Integer providerId, Integer isPayment);

}
