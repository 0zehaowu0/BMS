package com.zrgj.controller;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.BookInfo;
import com.zrgj.service.BookInfoService;
import com.zrgj.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {
    //第一步显示界面
    @GetMapping("bookIndex")
    public String type(){
        return "book/bookIndex";
    }

    /**
     * 查询所有
     */
    @Autowired
    private BookInfoService bookInfoService;
    @ResponseBody
    @RequestMapping("bookAll")
    public R bookAll(BookInfo info, @RequestParam(defaultValue = "1") Integer page,
                     @RequestParam(defaultValue = "5") Integer limit){
        PageInfo<BookInfo> pageInfo = bookInfoService.queryBookInfoAll(info,page,limit);
        return R.ok("成功",pageInfo.getTotal(),pageInfo.getList());
    }

    @GetMapping("addBook")
    public String type1(){
        return "book/addBook";
    }

    @ResponseBody
    @RequestMapping(value = "addBookSubmit")
    public R addBookSubmit(@RequestBody BookInfo bookInfo) {
        bookInfoService.addBookSubmit(bookInfo);
        return R.ok();
    }

    //数据回显
    @RequestMapping("queryBookInfoById")
    public String queryBookInfoById(Integer id, Model model){
        BookInfo info=bookInfoService.queryBookInfoById(id);
        model.addAttribute("info",info);
        return "book/updateBook";
    }

    @RequestMapping("updateBookInfoSubmit")
    @ResponseBody
    public R updateBookInfoSubmit(@RequestBody BookInfo bookInfo){
        bookInfoService.updateBookSubmit(bookInfo);
        return R.ok();
    }

    @RequestMapping("deleteBookByIds")
    @ResponseBody
    public R deleteBookByIds(String ids){
        List list= Arrays.asList(ids.split(","));
        bookInfoService.deleteBookByIds(list);
        return R.ok();
    }
}
