package com.bjio.my.shop.commons.persistence;

import java.util.List;
import java.util.Map;

/**
 * @program: my-shop-1
 * @description: 数据访问基类
 * @author: jiofier
 **/
public interface BaseDao<T extends BaseEntity> {

    /**
     * 插入数据
     */
    void insert(T entity);

    /**
     * 根据 ID 删除数据
     * @param id
     */
    void delete(Long id);

    /**
     * 更新数据
     * @param entity
     */
    void update(T entity);

    /**
     * 根据 ID 查询数据
     * @param id
     * @return
     */
    T getById(Long id);

    /**
     * 批量删除
     * @param ids
     */
    void deleteMulti(String[] ids);

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<T> page(Map<String, Object> params);

    /**
     * 查询总笔数
     * @return
     */
    int count(T entity);
}
