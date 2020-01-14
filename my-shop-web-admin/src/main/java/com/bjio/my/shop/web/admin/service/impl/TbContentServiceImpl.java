package com.bjio.my.shop.web.admin.service.impl;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.commons.validator.BeanValidator;
import com.bjio.my.shop.domain.TbContent;
import com.bjio.my.shop.web.admin.abstacts.AbstractBaseServiceImpl;
import com.bjio.my.shop.web.admin.dao.TbContentDao;
import com.bjio.my.shop.web.admin.service.TbContentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional(readOnly = true)
@Service
public class TbContentServiceImpl extends AbstractBaseServiceImpl<TbContent,TbContentDao> implements TbContentService {

    @Transactional
    @Override
    public BaseResult save(TbContent tbContent) {
        String validator = BeanValidator.validator(tbContent);

        // 验证不通过
        if (validator!=null){
            return BaseResult.fail(validator);
        }

        // 验证通过
        else{
            tbContent.setUpdated(new Date());

            // 新增内容
            if (tbContent.getId() == null) {
                tbContent.setCreated(new Date());
                dao.insert(tbContent);
            }

            // 编辑内容
            else {
                update(tbContent);
            }
            return BaseResult.success("保存内容信息成功!");
        }

    }
}
