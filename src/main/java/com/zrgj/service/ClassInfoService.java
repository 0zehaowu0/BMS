package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.ClassInfo;

import java.util.List;

public interface ClassInfoService {

    PageInfo<ClassInfo> queryClassInfoAll(String name, Integer page, Integer limit);

    void addTypeSubmit(ClassInfo info);

    ClassInfo queryClassInfoById(Integer id);

    void updateTypeSubmit(ClassInfo classInfo);

    void deleteTypeByIds(List list);
}
