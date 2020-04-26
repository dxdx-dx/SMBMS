package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.Provider;
import cn.bdqn.smbms.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 供应商业务逻辑层
 */
public interface ProviderService {
    //分页查询供应商列表
    List<Provider> findByPage(String proCode, String proName, Integer pageNo, Integer pageSize);

    //查询供应商总条数
    int findByPageCount(String proCode, String proName);

    //查询所有供应商名称
    List<Provider> findName();

    //添加供应商
    boolean addPrivider(Provider provider);

    //根据id查询供应商
    Provider findProviderById(Integer id);

    //修改供应商
    boolean providerModify(Provider provider);

    //删除供应商
    boolean delprovider(Integer id);

}
