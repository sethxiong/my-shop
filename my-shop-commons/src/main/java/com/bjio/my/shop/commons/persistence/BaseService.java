package com.bjio.my.shop.commons.persistence;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.commons.dto.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @program: my-shop-1
 * @description: 业务逻辑基类
 * @author: jiofier
 * @create: 2019-12-14 00:47
 **/
public interface BaseService<T extends BaseEntity> {

    /**
     * 保存用户信息
     * @param entity
     */
    BaseResult save(T entity);

    /**,
     * 删除用户信息
     * @param id
     */
    void delete(Long id);

    /**
     * 更新用户信息
     * @param entity
     */
    void update(T entity);

    /**
     * 根据 ID 获取用户信息
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 批量删除用户信息
     * @param ids
     */
    void deleteMulti(String[] ids);

    /**
     * 分页查询
     * @param start
     * @param length
     * @return
     */
    PageInfo<T> page(int start, int length, int draw, T entity);

    /**
     * 查询总笔数
     * @return
     */
    int count(T entity);

}
