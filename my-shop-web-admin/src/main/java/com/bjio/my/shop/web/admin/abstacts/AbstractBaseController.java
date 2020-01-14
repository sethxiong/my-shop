package com.bjio.my.shop.web.admin.abstacts;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.commons.dto.PageInfo;
import com.bjio.my.shop.commons.persistence.BaseEntity;
import com.bjio.my.shop.commons.persistence.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.Enumeration;

public abstract class AbstractBaseController<T extends BaseEntity,S extends BaseService<T>> {

    @Autowired
    protected S service;

    /**
     * 前端：SpringMVC 表单标签，优化
     * 目的：requestMapping 请求前作统一初始化
     * 说明：所有 requestMapping 之前都会执行 ModelAttribute，AOP
     *
     * @param id
     * @return
     */
    @ModelAttribute
    protected T getEntity(Long id) {
        T entity = null;
        // id不为空，从数据库获取
        if (id != null) {
            entity = service.getById(id);
        }
        // id为空，新建用户
        else {
            // 自动填充数据，密码不放
            try {
                // 通过反射获取model的真实类型
                ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
                Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
                // 通过反射创建model的实例
                entity = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return entity;
    }

    /**
     * 跳转列表
     *
     * @return
     */
    public abstract String list();

    /**
     * 跳转表单
     *
     * @return
     */
    public abstract String form();

    public abstract String save(T entity, Model model, RedirectAttributes redirectAttributes);

    /**
     * 批量或单选删除信息
     *
     * @param ids
     * @return
     */
    public abstract BaseResult delete(String ids);

    /**
     * 分页查询信息
     *
     * @param request
     * @param entity
     * @return
     */
    protected PageInfo<T> page(HttpServletRequest request, T entity){
        Enumeration<String> parameterNames = request.getParameterNames();

        String strDraw = request.getParameter("draw");
        String strStart = request.getParameter("start");
        String strLength = request.getParameter("length");

        int draw = strDraw == null ? 0 : Integer.parseInt(strDraw);
        int start = strStart == null ? 0 : Integer.parseInt(strStart);
        int length = strLength == null ? 10 : Integer.parseInt(strLength);

        // 封装Datatables需要的结果
        PageInfo<T> pageInfo = service.page(start, length, draw, entity);

        return pageInfo;
    }

    /**
     * 查看详情
     *
     * @param entity
     * @return
     */
    public abstract String detail(T entity);
}