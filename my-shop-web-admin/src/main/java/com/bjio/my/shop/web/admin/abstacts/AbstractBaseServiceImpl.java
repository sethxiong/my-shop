package com.bjio.my.shop.web.admin.abstacts;


import com.bjio.my.shop.commons.dto.PageInfo;
import com.bjio.my.shop.commons.persistence.BaseDao;
import com.bjio.my.shop.commons.persistence.BaseEntity;
import com.bjio.my.shop.commons.persistence.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBaseServiceImpl<T extends BaseEntity,D extends BaseDao<T>> implements BaseService<T> {

    @Autowired
    protected D dao;

    @Override
    @Transactional
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public T getById(Long id) {
        return dao.getById(id);
    }

    @Override
    @Transactional
    public void update(T entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public void deleteMulti(String[] ids) {
        dao.deleteMulti(ids);
    }

    @Override
    public PageInfo<T> page(int start, int length, int draw, T entity) {
        int count = dao.count(entity);

        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        params.put("pageParams", entity);

        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setData(dao.page(params));

        return pageInfo;
    }

    @Override
    public int count(T entity) {
        return dao.count(entity);
    }
}
