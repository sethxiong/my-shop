package com.bjio.my.shop.web.admin.web.controller;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.domain.TbContentCategory;
import com.bjio.my.shop.web.admin.abstacts.AbstractBaseTreeContoller;
import com.bjio.my.shop.web.admin.service.TbContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "content/category")
public class ContentCategoryController extends AbstractBaseTreeContoller<TbContentCategory,TbContentCategoryService> {

    @ModelAttribute
    public TbContentCategory getTbContentCategory(Long id) {
        TbContentCategory tbContentCategory = null;
        // id不为空，从数据库获取
        if (id != null) {
            tbContentCategory = service.getById(id);
        }
        // id为空，新建
        else {
            // 自动填充数据，密码不放
            tbContentCategory = new TbContentCategory();
        }

        return tbContentCategory;
    }

    /**
     * 查询类目列表
     * @param model
     * @return
     */
    @Override
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        List<TbContentCategory> targetList = new ArrayList<TbContentCategory>();
        List<TbContentCategory> sourceList = service.selectAll();

        sortList(sourceList, targetList, 0L);

        model.addAttribute("tbContentCategories", targetList);
        return "content_category_list";
    }

    /**
     * 跳转新增类目表单
     * @return
     */
    @Override
    @RequestMapping(value = "form",method = RequestMethod.GET)
    public String form(TbContentCategory tbContentCategory){
        return "content_category_form";
    }

    @Override
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(TbContentCategory tbContentCategory, Model model, RedirectAttributes redirectAttributes) {

        BaseResult baseResult = service.save(tbContentCategory);

        //保存成功
        if (baseResult.getStatus() == 200) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            return "redirect:/content/category/list";
        }

        //保存失败
        else {
            model.addAttribute("baseResult", baseResult);
            return "content_category_form";
        }

    }

    /**
     * 树形结构
     * @param id
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "tree/data", method = RequestMethod.POST)
    public List<TbContentCategory> treeData(Long id) {
        if (id == null) {
            id = 0L;
        }
        return service.selectByPid(id);
    }
}
