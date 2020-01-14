package com.bjio.my.shop.web.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: my-shop
 * @description: 首页
 * @author: Seth Xiong
 * @create: 2020-01-14 22:46
 **/
@Controller
public class IndexController {

    /**
     * 跳转首页
     * @return
     */
    @RequestMapping(value = {"","index"}, method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}
