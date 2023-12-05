package com.zrgj.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrgj.dao.ClassInfoDao;
import com.zrgj.po.ClassInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassInfoServiceImpl implements ClassInfoService {

    @Autowired
    private ClassInfoDao classInfoDao;

    @Override
    public PageInfo<ClassInfo> queryClassInfoAll(String name, Integer page, Integer limit) {
        List<ClassInfo> classInfos = classInfoDao.queryClassInfoAll(name);
        PageHelper.startPage(page,limit);
        return new PageInfo<>(classInfos);
    }

    @Override
    public void addTypeSubmit(ClassInfo info) {
        classInfoDao.addTypeSubmit(info);
    }

    @Override
    public ClassInfo queryClassInfoById(Integer id) {
        ClassInfo classInfo = classInfoDao.queryClassInfoById(id);
        return classInfo;
    }

    @Override
    public void updateTypeSubmit(ClassInfo classInfo) {
        classInfoDao.updateTypeSubmit(classInfo);
    }

    @Override
    public void deleteTypeByIds(List list) {
        classInfoDao.deleteTypeByIds(list);
    }
}
