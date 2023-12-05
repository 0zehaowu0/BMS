package com.zrgj.service;

import com.github.pagehelper.PageInfo;
import com.zrgj.dao.NoticeMapper;
import com.zrgj.po.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public PageInfo<Notice> noticeAll(String content,Integer page,Integer limit) {
        List<Notice> notices=noticeMapper.queryNoticeAll(content);
        return new PageInfo<>(notices);
    }

    @Override
    public void addNoticeSubmit(Notice notice) {
        noticeMapper.insert(notice);
    }

    @Override
    public Notice queryNotice(Integer id) {
        Notice notice = noticeMapper.queryNoticeById(id);
        return notice;
    }

    @Override
    public void deleteNoticeByIds(List list) {
        noticeMapper.deleteNoticeByIds(list);
    }
}
