package com.bjio.my.shop.web.admin.web.controller;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.commons.dto.PageInfo;
import com.bjio.my.shop.domain.TbUser;
import com.bjio.my.shop.web.admin.abstacts.AbstractBaseController;
import com.bjio.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理
 */
@Controller
@RequestMapping(value = "user")
public class UserController extends AbstractBaseController <TbUser, TbUserService> {

    /**
     * 跳转到用户列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list() {
        return "user_list";
    }

    /**
     * 跳转到用户表单
     *
     * @return
     */
    @RequestMapping(value = "form", method = RequestMethod.GET)
    public String form() {
        return "user_form";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(TbUser tbUser, Model model, RedirectAttributes redirectAttributes) {
        BaseResult baseResult = service.save(tbUser);

        //保存成功
        if (baseResult.getStatus() == 200) {
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            return "redirect:/user/list";
        }

        //保存失败
        else {
            model.addAttribute("baseResult", baseResult);
            return "user_form";
        }

    }

    /**
     * 批量或单选删除用户信息
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        BaseResult baseResult = null;

        if (StringUtils.isNotBlank(ids)) {
            String[] idArray = ids.split(",");
            service.deleteMulti(idArray);
            baseResult = BaseResult.success("删除用户成功");
        } else {
            baseResult = BaseResult.fail("删除用户失败");
        }

        return baseResult;
    }


    /**
     * 分页查询用户信息
     *
     * @param request
     * @param tbUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public PageInfo<TbUser> page(HttpServletRequest request, TbUser tbUser) {
        // 调用抽象类实现的page()
        return super.page(request, tbUser);
    }


    /**
     * 查看用户详情
     *
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(TbUser tbUser) {
        return "user_detail";
    }
}
