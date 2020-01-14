package com.bjio.my.shop.domain;

import com.bjio.my.shop.commons.persistence.BaseEntity;
import com.bjio.my.shop.commons.persistence.BaseTreeEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class TbContentCategory extends BaseTreeEntity {
    @Length(min=1,max=20,message = "分类名称必须介于 1 - 20 之间")
    private String name;

    private Integer status;

    @NotNull(message = "排序不能为空")
    private Integer sortOrder;

//    @JsonProperty(value = "isParent")
    private Boolean isParent;
    private TbContentCategory parent;
}


