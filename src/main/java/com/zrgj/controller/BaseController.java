package com.zrgj.controller;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.BookInfo;
import com.zrgj.po.Notice;
import com.zrgj.service.BookInfoService;
import com.zrgj.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BaseController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private BookInfoService bookInfoService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/welcome")
    public String welcome(Model model){
        PageInfo<Notice> pageInfo=noticeService.noticeAll(null,1,1);
        if (pageInfo != null) {
            if (pageInfo.getList().size() > 0) {
                Notice notice = pageInfo.getList().get(0);
                model.addAttribute("info", notice);
            }
        }
        List<BookInfo> list = bookInfoService.getBookCountByType();
        model.addAttribute("list", list);
        return "welcome";
    }
}
