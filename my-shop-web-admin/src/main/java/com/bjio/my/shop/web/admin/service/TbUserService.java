package com.bjio.my.shop.web.admin.service;

import com.bjio.my.shop.commons.persistence.BaseService;
import com.bjio.my.shop.domain.TbUser;

public interface TbUserService extends BaseService<TbUser> {
    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    TbUser login(String email,String password);
}
