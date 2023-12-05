package com.zrgj.controller;

import com.github.pagehelper.PageInfo;
import com.zrgj.po.BookInfo;
import com.zrgj.po.LendList;
import com.zrgj.po.ReaderCard;
import com.zrgj.service.BookInfoService;
import com.zrgj.service.LendListService;
import com.zrgj.service.ReaderService;
import com.zrgj.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class LendListController {
    @GetMapping("lendListIndex")
    public String type() {
        return "lend/lendListIndex";
    }

    @Autowired
    private LendListService lendListService;
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookInfoService bookInfoService;

    @ResponseBody
    @RequestMapping("/lendListAll")
    public R lendListAll(Integer type,
                         String cardnumber,
                         String name,
                         Integer status,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue = "15") Integer limit) {
        LendList info = new LendList();
        info.setType(type);
        //创建读者对象
        ReaderCard reader = new ReaderCard();
        reader.setCardnumber(cardnumber);
        //把以上对象交给info
        info.setReader(reader);

        //图书对象
        BookInfo book = new BookInfo();
        book.setName(name);
        book.setStatus(status);
        info.setBookInfo(book);

        //分页查询所有的记录信息
        PageInfo pageInfo = lendListService.queryLendListAll(info, page, limit);
        return R.ok("ok", pageInfo.getTotal(), pageInfo.getList());
    }

    @GetMapping("/addLendList")
    public String addLendList() {
        return "lend/addLendList";
    }

    @ResponseBody
    @RequestMapping("/addLend")
    public R addLend(String cardnumber, String ids) {
        //获取图书id的集合
        List<String> list = Arrays.asList(ids.split(","));
        //判断卡号是否存在
        ReaderCard readerCard = new ReaderCard();
        readerCard.setCardnumber(cardnumber);
        PageInfo<ReaderCard> pageInfo = readerService.queryReaderListAll(readerCard, 1, 1);
        if (pageInfo.getList().size() == 0) {
            return R.fail("卡号信息不存在");
        } else {
            ReaderCard readerCard1 = pageInfo.getList().get(0);
            int num = readerCard1.getNumber();//获取限制书数据
            //获取当前读者已经借了基本
            int number = lendListService.queryBookNum(readerCard1.getId());
            if (num - number >= list.size()) {
                for (String bid : list) {
                    LendList lendList = new LendList();
                    lendList.setReaderId(readerCard1.getId());
                    lendList.setBookId(Integer.valueOf(bid));
                    lendList.setLendDate(new Date());
                    lendListService.addLendListSubmit(lendList);
                    BookInfo info = bookInfoService.queryBookInfoById(Integer.valueOf(bid));
                    info.setStatus(1);
                    bookInfoService.updateBookSubmit(info);
                }
            } else {
                return R.fail("目前借书数量大于可借数量...");
            }
        }
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/deleteLendListByIds")
    public R deleteLendListByIds(String ids, String bookIds) {
        List<String> list = Arrays.asList(ids.split(","));//借阅记录的id
        List<String> blist = Arrays.asList(bookIds.split(","));//图书信息的id
        int num = lendListService.deleteLendListById(list, blist);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("/backLendListByIds")
    public R backLendListByIds(String ids, String bookIds) {
        List<String> list = Arrays.asList(ids.split(","));//借阅记录的id
        List<String> blist = Arrays.asList(bookIds.split(","));//图书信息的id
        int num = lendListService.updateLendListSubmit(list, blist);
        return R.ok();
    }

    @GetMapping("/excBackBook")
    public String excBackBook(HttpServletRequest request, Model model) {
        //获取借阅记录id
        String id = request.getParameter("id");
        String bId = request.getParameter("bookId");
        model.addAttribute("id", id);
        model.addAttribute("bid", bId);
        return "lend/excBackBook";
    }

    @ResponseBody
    @RequestMapping("/updateLendInfoSubmit")
    public R updateLendInfoSubmit(LendList lendList) {
        lendListService.backBook(lendList);
        return R.ok();
    }

    @RequestMapping("/queryLookBookList")
    public String queryLookBookList(String flag, Integer id, Model model) {
        List<LendList> list = null;
        if (flag.equals("book")) {
            list = lendListService.queryLookBookList(null, id);
        } else {
            list = lendListService.queryLookBookList(id, null);
        }
        model.addAttribute("info", list);
        return "lend/lookBookList";
    }

    @RequestMapping("/queryLookBookList2")
    public String queryLookBookList2(HttpServletRequest request, Model model) {
        ReaderCard readerCard = (ReaderCard) request.getSession().getAttribute("user");
        List<LendList> list = lendListService.queryLookBookList(readerCard.getId(), null);
        model.addAttribute("info", list);
        return "lend/lookBookList";
    }
}