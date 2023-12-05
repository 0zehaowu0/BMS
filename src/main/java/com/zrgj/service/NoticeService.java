package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.Notice;

import java.util.List;

public interface NoticeService {
    PageInfo<Notice> noticeAll(String content,Integer page,Integer limit);

    void addNoticeSubmit(Notice notice);

    Notice queryNotice(Integer id);

    void deleteNoticeByIds(List list);
}
