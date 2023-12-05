package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.dao.BookInfoMapper;
import com.zrgj.dao.LendListMapper;
import com.zrgj.po.BookInfo;
import com.zrgj.po.LendList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LendListServiceImpl implements LendListService {
    @Autowired
    private LendListMapper lendListMapper;
    @Autowired
    private BookInfoMapper bookInfoMapper;

    @Override
    public PageInfo queryLendListAll(LendList info, Integer page, Integer limit) {
        List<LendList> lendLists = lendListMapper.queryLendListAll(info);
        return new PageInfo(lendLists);
    }

    @Override
    public int queryBookNum(Integer id) {
        int i = lendListMapper.queryLendNumberById(id);
        return i;
    }

    @Override
    public void addLendListSubmit(LendList lendList) {
        lendListMapper.insert(lendList);
    }

    @Override
    public int deleteLendListById(List<String> list, List<String> blist) {
        int i=0;
        for(String id: list) {
            i += lendListMapper.deleteLendListById(Integer.valueOf(id));
        }
        for (String bid:blist){
            BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(Integer.valueOf(bid));
            bookInfo.setStatus(0);
            bookInfoMapper.updateByPrimaryKeyWithBLOBs(bookInfo);
        }
        return i;
    }

    @Override
    public int updateLendListSubmit(List<String> list, List<String> blist) {
        int num=0;
        for(String id:list) {
//根据id查询借阅记录信息
            LendList lendList = new LendList();
            lendList.setId(Integer.parseInt(id));
            lendList.setBackDate(new Date());
            lendList.setType(0);//正常还书
            num += lendListMapper.updateLendListSubmit(lendList);
        }
        for(String bid:blist) {
//根据id查询图书记录信息
            BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(Integer.parseInt(bid));
            bookInfo.setStatus(0);//该为未借出
            bookInfoMapper.updateByPrimaryKeyWithBLOBs(bookInfo);
        }
        return num;
    }

    @Override
    public void backBook(LendList lendList) {
        LendList lend = new LendList();
        lend.setId(lendList.getId());
        lend.setType(lendList.getType());
        lend.setBackDate(new Date());
        lend.setRemarks(lendList.getRemarks());
        lend.setBookId(lendList.getBookId());
        lendListMapper.updateLendListSubmit(lend);
        //判断异常还书 如果是延期或者正常还书,需要更改书的状态
        if (lend.getType() == 0 || lend.getType() == 1) {
            BookInfo bookInfo = bookInfoMapper.selectByPrimaryKey(lend.getBookId());
            bookInfo.setStatus(0);//
            bookInfoMapper.updateByPrimaryKeyWithBLOBs(bookInfo);
        }
    }

    @Override
    public List<LendList> queryLookBookList(Integer rid, Integer bid) {
        List<LendList> list = lendListMapper.queryLookBookList(rid, bid);
        return list;
    }
}
