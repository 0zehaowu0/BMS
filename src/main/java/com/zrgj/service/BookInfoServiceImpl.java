package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.dao.BookInfoMapper;
import com.zrgj.po.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookInfoServiceImpl implements BookInfoService {
    @Autowired
    private BookInfoMapper bookInfoMapper;
    @Override
    public PageInfo<BookInfo> queryBookInfoAll(BookInfo info, Integer page, Integer limit) {
        List<BookInfo> readerCards = bookInfoMapper.queryBookInfoAll(info);
        return new PageInfo<>(readerCards);
    }

    @Override
    public void addBookSubmit(BookInfo bookInfo) {
        bookInfoMapper.insert(bookInfo);
    }

    @Override
    public BookInfo queryBookInfoById(Integer id) {
        BookInfo bookInfo=bookInfoMapper.selectByPrimaryKey(id);
        return bookInfo;
    }

    @Override
    public void updateBookSubmit(BookInfo bookInfo) {
        bookInfoMapper.updateByPrimaryKeySelective(bookInfo);
    }

    @Override
    public void deleteBookByIds(List<String> list) {
        for(String id:list){
            int id2=Integer.valueOf(id);
            bookInfoMapper.deleteByPrimaryKey(id2);
        }
    }

    @Override
    public List<BookInfo> getBookCountByType() {
        List<BookInfo> count = bookInfoMapper.getBookCountByType();
        return count;
    }
}
