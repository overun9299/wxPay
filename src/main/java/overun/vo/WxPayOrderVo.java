package overun.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @ClassName: WxPayOrderVo
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 16:28
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */

@ApiModel
public class WxPayOrderVo {

    @ApiModelProperty(value = "订单ID或账单ID", required = true, example = "1")
    @NotNull(message = "ID不可以为空")
    private Integer id;

    @ApiModelProperty(value = "客户openId", required = true, example = "12312312312")
    @NotBlank(message = "客户openID不可以为空")
    private String openId;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
