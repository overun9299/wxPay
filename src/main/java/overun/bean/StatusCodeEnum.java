package overun.bean;

/**
 * @ClassName: StatusCodeEnum
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 12:34
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
public enum StatusCodeEnum {

    C1000("成功"),
    C9000("系统异常"),
    C9001("参数错误");
    String description;

    StatusCodeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static StatusCodeEnum[] baseResponseCodeEnums() {
        return new StatusCodeEnum[]{StatusCodeEnum.C1000, StatusCodeEnum.C9000, StatusCodeEnum.C9001};
    }
}
