package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.dao.AdminMapper;
import com.zrgj.po.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public PageInfo<Admin> queryAdminInfoAll(Admin admin, Integer page, Integer limit) {
        List<Admin> admins = adminMapper.queryAdminInfoAll(admin);
        return new PageInfo<>(admins);
    }

    @Override
    public int addAdminSubmit(Admin admin) {
        int insert = adminMapper.insert(admin);
        return insert;
    }

    @Override
    public Admin queryAdminById(Integer id) {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return admin;
    }

    @Override
    public void updatePwdSubmit(Admin admin) {

        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void deleteAdminByIds(List<String> list) {
        for(String id:list){
            int id2=Integer.valueOf(id);
            adminMapper.deleteByPrimaryKey(id2);
        }
    }

    @Override
    public Admin queryUserByNameAndPassword(String username, String password) {
        Admin admin = adminMapper.queryUserByNameAndPassword(username, password);
        return admin;
    }
}
