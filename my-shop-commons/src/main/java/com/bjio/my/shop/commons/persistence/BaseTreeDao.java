package com.bjio.my.shop.commons.persistence;

import java.util.List;

/**
 * @program: my-shop-1
 * @description: 通用树型结构接口
 * @author: Bjio
 * @create: 2019-12-22 18:58
 **/
public interface BaseTreeDao<T extends BaseEntity> {

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
     * 查询所有类目
     * @return
     */
    List<T> selectAll();

    /**
     * 根据父级节点 ID 查询所有子节点
     * @param pid
     * @return
     */
    List<T> selectByPid(Long pid);
}
