package com.bjio.my.shop.web.admin.web.controller;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.commons.dto.PageInfo;
import com.bjio.my.shop.domain.TbContent;
import com.bjio.my.shop.web.admin.abstacts.AbstractBaseController;
import com.bjio.my.shop.web.admin.service.TbContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "content")
public class ContentController extends AbstractBaseController<TbContent,TbContentService> {

    /**
     * 跳转到列表
     *
     * @return
     */
    @Override
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return "content_list";
    }

    /**
     * 跳转到表单
     *
     * @return
     */
    @Override
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form() {
        return "content_form";
    }

    @Override
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(TbContent tbContent, Model model, RedirectAttributes redirectAttributes) {

        BaseResult baseResult = service.save(tbContent);

        //保存成功
        if (baseResult.getStatus() == BaseResult.STATUS_SUCCESS) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            return "redirect:/content/list";
        }

        //保存失败
        else {
            model.addAttribute("baseResult", baseResult);
            return "content_form";
        }
    }

    /**
     * 批量或单选删除信息
     *
     * @param ids
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        BaseResult baseResult = null;

        if (StringUtils.isNotBlank(ids)) {
            String[] idArray = ids.split(",");
            service.deleteMulti(idArray);
            baseResult = BaseResult.success("删除成功");
        } else {
            baseResult = BaseResult.fail("删除失败");
        }

        return baseResult;
    }

    /**
     * 分页查询信息
     *
     * @param request
     * @param tbContent
     * @return
     */
    @Override
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public PageInfo<TbContent> page(HttpServletRequest request, TbContent tbContent) {
        // 调用抽象类实现的page()
        return super.page(request, tbContent);
    }

    /**
     * 查看详情
     *
     * @param tbContent
     * @return
     */
    @Override
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(TbContent tbContent) {
        return "content_detail";
    }
}
