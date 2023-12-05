package com.zrgj.controller;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.ClassInfo;
import com.zrgj.service.ClassInfoService;
import com.zrgj.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class TypeController {

    @GetMapping("typeIndex")
    public String type(){
        return "type/typeIndex";
    }

    @Autowired
    private ClassInfoService classInfoService;

    //查询
    @RequestMapping(value = "typeAll",method = RequestMethod.GET)
    @ResponseBody
    public R typeAll(String name, @RequestParam(defaultValue = "1")Integer page, @RequestParam(defaultValue = "5")Integer limit){
        PageInfo<ClassInfo> pageInfo=classInfoService.queryClassInfoAll(name,page,limit);
        return R.ok("成功",pageInfo.getTotal(),pageInfo.getList());
    }

    //添加
    @GetMapping("typeAdd")
    public String type1(){
        return "type/typeAdd";
    }
    @RequestMapping(value = "addTypeSubmit",method = RequestMethod.POST)
    @ResponseBody
    public R typeAdd(ClassInfo info){
        classInfoService.addTypeSubmit(info);
        return R.ok();
    }

    //数据回滚
    @RequestMapping(value = "queryClassInfoById",method = RequestMethod.GET)
    public String queryClassInfoById(Integer id, Model model){
        ClassInfo info=classInfoService.queryClassInfoById(id);
        model.addAttribute("info",info);
        return "type/updateType";
    }

    //更新
    @RequestMapping(value = "updateTypeSubmit",method = RequestMethod.POST)
    @ResponseBody
    public R updateTypeSubmit(@RequestBody ClassInfo classInfo){
        classInfoService.updateTypeSubmit(classInfo);
        return R.ok();
    }

    //删
    @RequestMapping(value = "deleteType",method = RequestMethod.POST)
    @ResponseBody
    public R deleteType(String ids){
        List list= Arrays.asList(ids.split(","));
        classInfoService.deleteTypeByIds(list);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/findAllList")
    public List<ClassInfo> findAll() {
        PageInfo<ClassInfo> pageInfo = classInfoService.queryClassInfoAll(null, 1, 100);
        List<ClassInfo> lists = pageInfo.getList();
        return lists;
    }
}
