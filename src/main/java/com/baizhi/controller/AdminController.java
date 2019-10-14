package com.baizhi.controller;

import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(String username, String password, String enCode, HttpSession session){
        Map<String, Object> login = adminService.login(username, password, enCode, session);
        session.setAttribute("username",username);
        return login;
    }

    @RequestMapping("exit")
    public String exit(HttpSession session){
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
