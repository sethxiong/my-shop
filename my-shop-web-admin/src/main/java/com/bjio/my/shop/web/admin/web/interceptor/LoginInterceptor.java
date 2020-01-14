package com.bjio.my.shop.web.admin.web.interceptor;

import com.bjio.my.shop.commons.constant.ConstantUtils;
import com.bjio.my.shop.domain.TbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    public static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        logger.info("Login - preHandle");
        TbUser user = (TbUser) httpServletRequest.getSession().getAttribute(ConstantUtils.SESSION_USER);
        // 判断用户是否登录
        if (user == null) {
            // 用户未登录，重定向到登录页
            httpServletResponse.sendRedirect("/login");
        }

        // 放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        logger.info("Login - postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//        logger.info("Login - afterCompletion");
    }
}
