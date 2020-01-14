package com.bjio.my.shop.web.admin.service.impl;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.commons.validator.BeanValidator;
import com.bjio.my.shop.domain.TbUser;
import com.bjio.my.shop.web.admin.abstacts.AbstractBaseServiceImpl;
import com.bjio.my.shop.web.admin.dao.TbUserDao;
import com.bjio.my.shop.web.admin.service.TbUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Transactional(readOnly = true)
@Service
public class TbUserServiceImpl extends AbstractBaseServiceImpl<TbUser,TbUserDao> implements TbUserService {

    @Override
    public TbUser login(String email, String password) {
        // 根据邮箱通过 MyBatis 获取用户信息
        TbUser tbUser = dao.getByEmail(email);

        // 用户存在
        if (tbUser != null) {
            //明文密码加密
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

            //判断加密后的密码和数据库中存放的密码是否匹配，匹配则表示允许登录
            if (md5Password.equals(tbUser.getPassword())) {
                return tbUser;

            }
        }
        // 用户不存在
        else {

        }

        return null;
    }

    @Override
    @Transactional
    public BaseResult save(TbUser tbUser) {
        String validator = BeanValidator.validator(tbUser);

        // 验证不通过
        if (validator != null) {
            return BaseResult.fail(validator);
        }

        // 验证通过
        else {
            tbUser.setUpdated(new Date());
            //新增用户
            if (tbUser.getId() == null) {
                //邮箱不存在
                if (dao.getByEmail(tbUser.getEmail()) == null) {
                    //密码加密
                    tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
                    tbUser.setCreated(new Date());
                    dao.insert(tbUser);
                    return BaseResult.success("新增用户成功!");
                }
                //邮箱存在
                else {
                    return BaseResult.fail(500, "新增用户失败,该用户已存在!");
                }
            }

            //编辑用户
            else {
                update(tbUser);
                return BaseResult.success("保存用户信息成功!");
            }
        }

    }

}
