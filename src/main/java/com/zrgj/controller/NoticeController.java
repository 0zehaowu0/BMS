package com.zrgj.controller;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.Notice;
import com.zrgj.service.NoticeService;
import com.zrgj.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class NoticeController {
    @GetMapping("noticeIndex")
    public String type(){
        return "notice/noticeIndex";
    }
    @GetMapping("noticeIndex2")
    public String type2() {
        return "notice/noticeIndex2";
    }

    @Autowired
    private NoticeService noticeService;
    @ResponseBody
    @RequestMapping(value = "noticeAll")
    public R typeAll(String content, @RequestParam(defaultValue = "1") Integer page,
                     @RequestParam(defaultValue = "5") Integer limit){
        PageInfo<Notice> pageInfo = noticeService.noticeAll(content,page,limit);
        return R.ok("成功",pageInfo.getTotal(),pageInfo.getList());
    }
    @GetMapping("addNotice")
    public String type1(){
        return "notice/addNotice";
    }

    @ResponseBody
    @RequestMapping("addNoticeSubmit")
    public R addNoticeSubmit( Notice notice) {
        notice.setCreateDate(new Date());
        notice.setAuthor(2);
        noticeService.addNoticeSubmit(notice);
        return R.ok();
    }
    @RequestMapping("queryNotice")
    public  String queryNotice(Integer id, Model model){
        Notice info=noticeService.queryNotice(id);
        model.addAttribute("info",info);
        return "notice/queryNotice";
    }
    @RequestMapping("deleteNoticeByIds")
    @ResponseBody
    public R deleteNoticeByIds(String ids){
        List list= Arrays.asList(ids.split(","));
        noticeService.deleteNoticeByIds(list);
        return R.ok();
    }
}
