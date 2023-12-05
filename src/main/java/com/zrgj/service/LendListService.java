package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.LendList;

import java.util.List;

public interface LendListService {
    PageInfo queryLendListAll(LendList info, Integer page, Integer limit);

    int queryBookNum(Integer id);

    void addLendListSubmit(LendList lendList);

    int deleteLendListById(List<String> list, List<String> blist);

    int updateLendListSubmit(List<String> list, List<String> blist);

    void backBook(LendList lendList);

    List<LendList> queryLookBookList(Integer rid, Integer bid);
}
