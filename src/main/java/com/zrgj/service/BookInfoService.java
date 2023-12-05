package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.BookInfo;

import java.util.List;

public interface BookInfoService {
    PageInfo<BookInfo> queryBookInfoAll(BookInfo info, Integer page, Integer limit);

    void addBookSubmit(BookInfo bookInfo);

    BookInfo queryBookInfoById(Integer id);

    void updateBookSubmit(BookInfo bookInfo);

    void deleteBookByIds(List<String> list);

    List<BookInfo> getBookCountByType();
}
