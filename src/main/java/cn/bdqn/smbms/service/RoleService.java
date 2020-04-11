package cn.bdqn.smbms.service;

import cn.bdqn.smbms.pojo.Role;

import java.util.List;

/**
 * 角色业务逻辑层
 */
public interface RoleService {
    //查询所有角色
    List<Role> findAll();
}
