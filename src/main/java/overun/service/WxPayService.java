package overun.service;

import com.alibaba.fastjson.JSONObject;
import overun.bean.BaseResponse;
import overun.vo.WxPayOrderVo;

/**
 * @ClassName: WxPayService
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 12:44
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
public interface WxPayService {


    /**
     * 微信登陆小程序
     * @param code
     * @return
     */
    BaseResponse<JSONObject> login(String code);

    /**
     * 用户购买
     * @param wxPayOrderVo
     * @return
     */
    BaseResponse<JSONObject> buyOrder(WxPayOrderVo wxPayOrderVo);
}
