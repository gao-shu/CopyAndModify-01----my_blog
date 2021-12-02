package com.gaoshu.entity.PO;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gaoshu_config_dic")
@ApiModel(value="ConfigDic对象", description="")
public class ConfigDic implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    @ApiModelProperty(value = "分类")
    private String type;

    @ApiModelProperty(value = "内容")
    private String value;

    @ApiModelProperty(value = "0,弃用 1，启用")
    private Boolean status;


}
