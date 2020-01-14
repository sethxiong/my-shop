package com.bjio.my.shop.web.admin.service.impl;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.commons.validator.BeanValidator;
import com.bjio.my.shop.domain.TbContentCategory;
import com.bjio.my.shop.web.admin.abstacts.AbstractBaseTreeServiceImpl;
import com.bjio.my.shop.web.admin.dao.TbContentCategoryDao;
import com.bjio.my.shop.web.admin.service.TbContentCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional(readOnly = true)
@Service
public class TbContentCategoryServiceImpl extends AbstractBaseTreeServiceImpl<TbContentCategory
        , TbContentCategoryDao> implements TbContentCategoryService {

    @Override
    @Transactional
    public BaseResult save(TbContentCategory entity) {
        String validator = BeanValidator.validator(entity);

        // 验证不通过
        if (validator != null) {
            return BaseResult.fail(validator);
        }

        // 验证通过。。。只能新建根目录，无法更改为/
        else {
            entity.setUpdated(new Date());

            Long id = entity.getParent().getId();
            TbContentCategory parent = getById(id);

            // 为 / 节点
            if(parent == null){
                entity.getParent().setId(0L);
            }
            else{
                parent.setIsParent(true);
                update(parent);
            }


            // 新建
            if(entity.getId()==null){
                // 新增
                entity.setStatus(1);
                entity.setIsParent(false);
                entity.setCreated(new Date());
                dao.insert(entity);

            }

            // 编辑
            else{
                update(entity);
            }



            return BaseResult.success("保存分类信息成功!");
        }
    }
}
