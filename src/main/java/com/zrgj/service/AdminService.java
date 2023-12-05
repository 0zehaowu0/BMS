package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.Admin;

import java.util.List;

public interface AdminService {
    PageInfo<Admin> queryAdminInfoAll(Admin admin, Integer page, Integer limit);

    int addAdminSubmit(Admin admin);


    Admin queryAdminById(Integer id);

    void updatePwdSubmit(Admin admin);

    void deleteAdminByIds(List<String> list);

    Admin queryUserByNameAndPassword(String username, String password);
}
