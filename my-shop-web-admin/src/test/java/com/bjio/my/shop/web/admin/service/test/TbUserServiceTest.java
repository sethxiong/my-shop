package com.bjio.my.shop.web.admin.service.test;

import com.bjio.my.shop.domain.TbUser;
import com.bjio.my.shop.web.admin.service.TbUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TbUserServiceTest {

    @Autowired
    private TbUserService tbUserService;

    @Test
    public void testInsert() {
        TbUser tbUser = new TbUser();
        tbUser.setUsername("bjio");
        tbUser.setPhone("15715715715");
        tbUser.setEmail("admin@qq.com");
        tbUser.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());

        tbUserService.save(tbUser);
    }
}
