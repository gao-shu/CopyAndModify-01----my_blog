package com.gaoshu.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: PageEntity
 * @Description: 分页
 * @author: gaoshu
 * @date: 2021/12/2 11:11
 */
@Data
public class PageEntity {

    @ApiModelProperty("当前页数")
    private Integer page = 1;

    @ApiModelProperty("每页展示条数")
    private Integer limit = 15;

    @ApiModelProperty(value = "是否分页，0:不分页 1 分页", allowableValues = "0,1")
    private Integer pageType = 1;

    @ApiModelProperty(value = "分类")
    private Integer categoryId;

}
