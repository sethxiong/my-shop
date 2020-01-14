package com.bjio.my.shop.web.api.web.controller.v1;

import com.bjio.my.shop.commons.dto.BaseResult;
import com.bjio.my.shop.domain.TbContent;
import com.bjio.my.shop.web.api.service.TbContentService;
import com.bjio.my.shop.web.api.web.dto.TbContentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "${api.path.v1}/contents")
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    @ModelAttribute
    public TbContent getTbContent(Long id) {
        TbContent tbContent = null;
        if (id == null) {
            tbContent = new TbContent();
        }

        return tbContent;
    }

    /**
     * 根据类别 ID 查询内容列表
     * 测试地址：http://localhost:8081/api/v1/contents/89
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "{category_id}", method = RequestMethod.GET)
    public BaseResult findContentByCategoryId(@PathVariable(value = "category_id") Long categoryId) {
        List<TbContentDTO> tbContentDtos = null;
        List<TbContent> tbContents = tbContentService.selectByCategoryId(categoryId);

        /**
         * 将 entity 转换为 dto
         */
        if (tbContents != null && tbContents.size() > 0) {
            tbContentDtos = new ArrayList<>();
            for (TbContent tbContent : tbContents) {
                TbContentDTO dto = new TbContentDTO();
                BeanUtils.copyProperties(tbContent, dto);
                tbContentDtos.add(dto);
            }
        }

        return BaseResult.success("成功",tbContentDtos);
    }

}
