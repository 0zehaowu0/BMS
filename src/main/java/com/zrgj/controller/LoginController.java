package com.zrgj.controller;

import com.zrgj.codeutil.IVerifyCodeGen;
import com.zrgj.codeutil.SimpleCharVerifyCodeGenImpl;
import com.zrgj.codeutil.VerifyCode;
import com.zrgj.po.Admin;
import com.zrgj.po.ReaderCard;
import com.zrgj.service.AdminService;
import com.zrgj.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    @Autowired
    private AdminService adminService;
//    @Autowired
//    private AdminMapper adminMapper;
    @Autowired
    private ReaderService readerService;
//    @Autowired
//    private ReaderCardMapper readerCardMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 获取验证码方法
     * @param request
     * @param response
     */
    @RequestMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(80, 28);
            String code = verifyCode.getCode();
            //LOGGER.info(code);
            //将VerifyCode绑定session
            request.getSession().setAttribute("VerifyCode", code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            //LOGGER.info("", e);
            System.out.println("异常处理");
        }
    }

    @RequestMapping("/loginIn")
    public String loginIn(HttpServletRequest request, Model model) {
        //获取用户名，密码，验证码，用户类型
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String captcha = request.getParameter("captcha");
        String type = request.getParameter("type");
        //验证登录是否超时
        HttpSession session = request.getSession();
        if (session == null) {
            model.addAttribute("msg","登录超时了");
            return "login";
        }
        //验证码是否正确
        String realCode= (String) session.getAttribute("VerifyCode");
        if(!realCode.toLowerCase().equals(captcha.toLowerCase())) {
            model.addAttribute("msg", "验证码不正确");
            return "login";
        }
        if(type.equals("1")){//管理员信息
            //用户名和密码是否正确
            Admin user=adminService.queryUserByNameAndPassword(username, password);
            //Admin user=adminMapper.queryUserByNameAndPassword(username,password);
            if(user == null) {//该用户不存在
                model.addAttribute("msg", "用户名或者密码错误");
                return "login";
            }
            session.setAttribute("user", user);
            session.setAttribute("type", "admin");
        }else{
            ReaderCard readerCard=readerService.queryUserInfoByNameAndPassword(username, password);
            //ReaderCard readerCard=readerCardMapper.queryUserInfoByNameAndPassword(username, password);
            if(readerCard == null) {
                model.addAttribute("msg", "用户名或者密码错误");
                return "login";
            }
                session.setAttribute("user", readerCard);
                session.setAttribute("type", "reader");
        }
        return "index";
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();//销毁
        return "redirect:/login";//重定向
    }
}
