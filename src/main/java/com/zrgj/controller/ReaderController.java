package com.zrgj.controller;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.ReaderCard;
import com.zrgj.service.ReaderService;
import com.zrgj.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReaderController {

    //第一步显示界面
    @GetMapping("readerIndex")
    public String type() {
        return "reader/readerIndex";
    }

    @GetMapping("readerIndex2")
    public String type2() {
        return "reader/readerIndex2";
    }

    @Autowired
    private ReaderService readerService;

    @ResponseBody
    @RequestMapping(value = "queryReaderAll")
    public R typeAll(ReaderCard readerCard,
                     @RequestParam(defaultValue = "1") Integer page,
                     @RequestParam(defaultValue = "5") Integer limit) {
        PageInfo<ReaderCard> pageInfo = readerService.queryReaderListAll(readerCard, page, limit);
        return R.ok("成功", pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping(value = "queryReaderAll2", method = RequestMethod.GET)
    @ResponseBody
    public R queryReaderAll2(HttpServletRequest httpServletRequest,
                             @RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, defaultValue = "5") int limit
    ) {
        HttpSession session = httpServletRequest.getSession();
        if (session == null || session.getAttribute("user") == null || !session.getAttribute("type").equals("reader")) {
            return R.fail("登录失败");
        }
        ReaderCard readerCard = (ReaderCard) session.getAttribute("user");
        PageInfo<ReaderCard> pageInfo = readerService.queryReaderListAll(readerCard, page, limit);
        return R.ok("成功", pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping("queryReaderById")
    public String queryReaderById(Integer id, Model model) {
        ReaderCard readerCard = readerService.queryReaderById(id);
        model.addAttribute("id", id);
        return "reader/updatePwd";
    }

    @RequestMapping("updateReaderPwdSubmit")
    @ResponseBody
    public R updateReaderPwdSubmit(Integer id, String newPwd) {

        ReaderCard info = readerService.queryReaderById(id);

        ReaderCard readerCard = new ReaderCard();
        readerCard.setPassword(newPwd);
        readerCard.setUsername(info.getUsername());
        readerCard.setId(id);
        readerService.updateReaderPwdSubmit(readerCard);
        return R.ok("修改密码成功");
    }

    @GetMapping("addReader")
    public String type1() {
        return "reader/addReader";
    }

    @ResponseBody
    @RequestMapping(value = "addReaderSubmit", method = RequestMethod.POST)
    public R addReaderSubmit(@RequestBody ReaderCard readerCard) {
        readerCard.setPassword("123456");
        readerService.addReaderSubmit(readerCard);
        return R.ok();
    }

    //数据回显
    @RequestMapping("queryReaderInfoById")
    public String queryReaderInfoById(Integer id, Model model) {
        ReaderCard readerCard = readerService.queryReaderById(id);
        model.addAttribute("info", readerCard);
        return "reader/updateReader";
    }

    @RequestMapping("updateReaderSubmit")
    @ResponseBody
    public R updateReaderSubmit(@RequestBody ReaderCard readerCard) {
        readerService.updateReaderSumbit(readerCard);
        return R.ok();
    }

    @RequestMapping("deleteReader")
    @ResponseBody
    public R deleteReader(String ids) {
        List list = Arrays.asList(ids.split(","));
        readerService.deleteReader(list);
        return R.ok();
    }
}
