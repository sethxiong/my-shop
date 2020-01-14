package com.bjio.my.shop.web.admin.web.interceptor;

import com.bjio.my.shop.commons.constant.ConstantUtils;
import com.bjio.my.shop.domain.TbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bjio.my.shop.web.admin.web.interceptor.LoginInterceptor.logger;

public class PermissionInterceptor implements HandlerInterceptor {
    public static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        logger.info("Per - preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        logger.info("Per - postHandle");

        // 如果请求来自登录页
        if (modelAndView !=null && modelAndView.getViewName() != null && modelAndView.getViewName().endsWith("login")) {
            TbUser user = (TbUser) httpServletRequest.getSession().getAttribute(ConstantUtils.SESSION_USER);
            // 用户已登录，重定向到首页
            if (user != null){
                httpServletResponse.sendRedirect("/main");
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        logger.info("Per - afterCompletion");
    }
}
