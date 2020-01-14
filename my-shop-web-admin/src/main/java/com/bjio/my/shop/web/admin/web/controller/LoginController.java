package com.bjio.my.shop.web.admin.web.controller;

import com.bjio.my.shop.commons.constant.ConstantUtils;
import com.bjio.my.shop.commons.utils.CookieUtils;
import com.bjio.my.shop.domain.TbUser;
import com.bjio.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController{

    @Autowired
    private TbUserService tbUserService;

    /**
     * 首页跳转
     *
     * @return
     */
    @RequestMapping(value = {"", "login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest httpServletRequest) {
        //获取Cookies
        String userInfo = CookieUtils.getCookieValue(httpServletRequest, ConstantUtils.SESSION_USER);

        //记住我
        if (!StringUtils.isBlank(userInfo)) {
            String[] user = userInfo.split(":");
            httpServletRequest.setAttribute("email", user[0]);
            httpServletRequest.setAttribute("password", user[1]);
            httpServletRequest.setAttribute("isRemember", true);
        }

        return "login";
    }

    /**
     * 登录
     * @param email
     * @param password
     * @param httpServletRequest
     * @param httpServletResponse
     * @param model
     * @return
     */
    @RequestMapping(value = {"login"}, method = RequestMethod.POST)
    public String login(@RequestParam(required = true) String email, @RequestParam(required = true) String password,
                        HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model
    ) {

        TbUser tbUser = tbUserService.login(email, password);

        boolean isRemember = httpServletRequest.getParameter("isRemember") == null ? false : true;

        // 登录成功的处理
        if (tbUser != null) {
            //记住我
            if (isRemember) {
                CookieUtils.setCookie(httpServletRequest, httpServletResponse, ConstantUtils.SESSION_USER,
                        String.format("%s:%s", email, password), 7 * 24 * 60 * 60);
            }

            // 不记住我
            else {
                CookieUtils.deleteCookie(httpServletRequest, httpServletResponse, ConstantUtils.SESSION_USER);
            }
            httpServletRequest.setAttribute("message", "false");

            // 记录登录信息，放入会话
            httpServletRequest.getSession().setAttribute(ConstantUtils.SESSION_USER, tbUser);
            // 重定向到首页
            return "redirect:/main";
        }

        // 登录失败的处理
        else {
            httpServletRequest.setAttribute("message", "true");

            //model.addAttribute...
            return "login";

        }
    }

    /**
     * 注销
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getSession().getAttribute(ConstantUtils.SESSION_USER)!=null) {
            httpServletRequest.getSession().removeAttribute(ConstantUtils.SESSION_USER);
        }
        return login(httpServletRequest);
    }
}
