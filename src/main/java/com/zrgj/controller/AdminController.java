package com.zrgj.controller;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.Admin;
import com.zrgj.service.AdminService;
import com.zrgj.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {
    @GetMapping("adminIndex")
    public String type(){
        return "admin/adminIndex";
    }

    @Autowired
    private AdminService adminService;
    @ResponseBody
    @RequestMapping("adminAll")
    public R typeAll(Admin admin, @RequestParam(defaultValue = "1") Integer page,
                     @RequestParam(defaultValue = "5") Integer limit){
        PageInfo<Admin> pageInfo = adminService.queryAdminInfoAll(admin,page,limit);
        return R.ok("成功",pageInfo.getTotal(),pageInfo.getList());
    }

    @GetMapping("addAdmin")
    public String type1(){
        return "admin/addAdmin";
    }

    @ResponseBody
    @RequestMapping("addAdminSubmit")
    public R addAdminSubmit(Admin admin) {
        int num=adminService.addAdminSubmit(admin);
        if(num>0){
            return R.ok();
        }else {
            return R.fail("添加失败");
        }
    }

    @RequestMapping("queryAdminById")
    public String queryAdminById(Integer id,Model model){
        Admin admin=adminService.queryAdminById(id);
        model.addAttribute("id",id);
        return "admin/updateAdmin";
    }

    @RequestMapping("updatePwdSubmit")
    @ResponseBody
    public R updatePwdSubmit(Integer id,String oldPwd,String newPwd){
        Admin info=adminService.queryAdminById(id);
        if(!oldPwd.equals(info.getPassword())){
            return R.fail("输入的密码不一致");
        }else{
            Admin admin=new Admin();
            admin.setPassword(newPwd);
            admin.setType(info.getType());
            admin.setUsername(info.getUsername());
            admin.setId(id);
            adminService.updatePwdSubmit(admin);
            return R.ok("修改密码成功");
        }
    }

    @RequestMapping("deleteAdmin")
    @ResponseBody
    public R deleteAdmin(String ids){
        List<String> list= Arrays.asList(ids.split(","));
        adminService.deleteAdminByIds(list);
        return R.ok();
    }
}
