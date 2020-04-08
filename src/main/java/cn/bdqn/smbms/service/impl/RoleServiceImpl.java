package cn.bdqn.smbms.service.impl;

import cn.bdqn.smbms.dao.BaseDao;
import cn.bdqn.smbms.dao.RoleDao;
import cn.bdqn.smbms.pojo.Role;
import cn.bdqn.smbms.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色业务逻辑层实现类
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        Connection connection = null;
        List<Role> roleList = new ArrayList<Role>();
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.findAll(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roleList;
    }
}
