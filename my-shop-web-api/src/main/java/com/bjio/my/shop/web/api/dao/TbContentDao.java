package com.bjio.my.shop.web.api.dao;

import com.bjio.my.shop.domain.TbContent;
import com.bjio.my.shop.domain.TbContentCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: my-shop-1
 * @description:
 * @author: Bjio
 * @create: 2020-01-07 23:03
 **/
@Repository
public interface TbContentDao {
    /**
     * 根据类别 ID 查询内容列表
     * @param tbContent
     * @return
     */
    List<TbContent> selectByCategoryId(TbContent tbContent);
}
