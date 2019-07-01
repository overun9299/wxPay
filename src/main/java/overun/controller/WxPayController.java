package overun.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import overun.bean.BaseResponse;
import overun.service.WxPayService;
import overun.vo.WxPayOrderVo;
import overun.vo.WxPayUnifiedorderVo;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @ClassName: WxPayController
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/20 20:48
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
@RestController
@Api(description = "微信接口", tags = "微信接口")
public class WxPayController {

    @Autowired
    private WxPayService wxPayService;


    private static Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @ApiImplicitParam(name = "code", value = "043NadsE0EToPj2bhZuE0Hs8sE0NadsU",defaultValue = "043NadsE0EToPj2bhZuE0Hs8sE0NadsU", paramType = "query", required = true)
    @ApiOperation(value = "微信登陆小程序", notes = "微信登陆小程序")
    @RequestMapping(value = "/login",method = {RequestMethod.GET}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<String> login(String code) {
        logger.info("微信登陆小程序请求报文{}", code);
        BaseResponse<String> base = new BaseResponse<String>();
        JSONObject json = wxPayService.login(code).getData();
        String openId = json.getString("openid");
        base.setData(openId);
        return base;
    }


    @ApiOperation(value = "点击订单付款", notes = "点击订单付款")
    @RequestMapping(value = "/buy",method = {RequestMethod.POST})
    @ResponseBody
    public BaseResponse<JSONObject> buyOrder(@RequestBody @Valid WxPayOrderVo wxPayOrderVo) {
        logger.info("用户{}点击订单付款请求报文{}", wxPayOrderVo.getOpenId(), JSONObject.toJSONString(wxPayOrderVo));

        return wxPayService.buyOrder(wxPayOrderVo);

    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "notify" )
    public String notify(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /** 为微信返回的xml */
        String notityXml = sb.toString();
        logger.info("支付完成后回掉请求报文{}", notityXml);
        /** 回调逻辑处理 wxPayService.paySuccess(notityXml)*/
        String str = "";
        return str;
    }

}
