package overun.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: BaseResponse
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 12:31
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
public class BaseResponse<T> {

    @ApiModelProperty(value = "状态码", example = "C1000", required = true)
    protected StatusCodeEnum statusCode = StatusCodeEnum.C1000;
    @ApiModelProperty(value = "处理结果描述", example = "成功")
    protected String message = "成功";//状态信息
    @ApiModelProperty(value = "详细数据")
    protected T data;

    public BaseResponse() {
    }

    public BaseResponse(StatusCodeEnum statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public StatusCodeEnum getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCodeEnum statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
