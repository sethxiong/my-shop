package com.bjio.my.shop.web.admin.abstacts;

import com.bjio.my.shop.commons.persistence.BaseEntity;
import com.bjio.my.shop.commons.persistence.BaseTreeEntity;
import com.bjio.my.shop.commons.persistence.BaseTreeService;
import com.bjio.my.shop.domain.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractBaseTreeContoller<T extends BaseTreeEntity,S extends BaseTreeService> {

    @Autowired
    protected S service;

    /**
     * 跳转列表
     * @param model
     * @return
     */
    public abstract String list(Model model);

    /**
     * 跳转表单
     * @param entity
     * @return
     */
    public abstract String form(T entity);

    /**
     * 保存
     * @param entity
     * @param model
     * @param redirectAttributes
     * @return
     */
    public abstract String save(T entity, Model model, RedirectAttributes redirectAttributes);

    public abstract List<T> treeData(Long id);

    /**
     * 类目排序
     * @param sourceList 数据源集合
     * @param targetList 排序后的集合
     * @param parentId   父节点 ID
     */
    protected void sortList(List<T> sourceList,
                          List<T> targetList, Long parentId) {
        for (T sourceEntity : sourceList) {
            // True 是父类节点
            if (sourceEntity.getParent().getId().equals(parentId)) {
                targetList.add(sourceEntity);
                // 判断有没有子节点，如果有则继续追加    递归
                if (sourceEntity.getIsParent()) {
                    for (T targetEntity : sourceList) {
                        //
                        if (targetEntity.getParent().getId().equals(sourceEntity.getId())) {
                            sortList(sourceList, targetList, sourceEntity.getId());
                            break;
                        }
                    }
                }
            }
        }
    }
}
