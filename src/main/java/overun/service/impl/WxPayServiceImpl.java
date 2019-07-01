package overun.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import overun.bean.BaseResponse;
import overun.bean.StatusCodeEnum;
import overun.config.properties.WXProperties;
import overun.service.WxPayService;
import overun.utils.HttpPostUtil;
import overun.utils.WXUtils;
import overun.vo.WxPayOrderVo;
import overun.vo.WxPayUnifiedorderVo;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: WxPayServiceImpl
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 12:45
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
@Service
public class WxPayServiceImpl implements WxPayService {

    @Value("${wx.loginUrl}")
    private String logUrl;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WXProperties wxProperties;

    private static Logger logger = LoggerFactory.getLogger(WxPayServiceImpl.class);


    @Override
    public BaseResponse<JSONObject> login(String code) {
        BaseResponse<JSONObject> base = new BaseResponse<>();
        String url = String.format(logUrl, appid, secret, code);
        String result = restTemplate.getForObject(url, String.class);
        logger.info("小程序登陆响应报文:{}", result);
        base.setData(JSONObject.parseObject(result));
        return base;
    }

    @Override
    public BaseResponse<JSONObject> buyOrder(WxPayOrderVo wxPayOrderVo) {
        BaseResponse<JSONObject> base = new BaseResponse<JSONObject>();
        /** 在此可通过传入的订单号查询该订单的商品信息及价格 */


        /** 进行统一下单 */
        WxPayUnifiedorderVo wxPayUnifiedorderVo = new WxPayUnifiedorderVo();
        wxPayUnifiedorderVo.setGoodsBody("商品或商家描述");
        wxPayUnifiedorderVo.setOpenId(wxPayOrderVo.getOpenId());
        wxPayUnifiedorderVo.setTotalFee("1");
        wxPayUnifiedorderVo.setTradeNo(wxPayId());
        JSONObject json = unifiedOrder(wxPayUnifiedorderVo);
        if ("SUCCESS".equals(json.getString("return_code"))) {
            if ("SUCCESS".equals(json.getString("result_code"))) {
                /** 更新订单状态信息 */

                base.setData(json);
            } else {
                base.setMessage(StatusCodeEnum.C9000.getDescription());
                base.setStatusCode(StatusCodeEnum.C9000);
            }
        } else {
            base.setMessage(StatusCodeEnum.C9000.getDescription());
            base.setStatusCode(StatusCodeEnum.C9000);
        }
        return base;
    }


    /**
     * 生成微信支付订单号
     * @return
     */
    public static String wxPayId(){
        Random random = new Random();
        SimpleDateFormat f1 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return "OVERUN" + f1.format(new Date()) + "" + (random.nextInt(9999999 - 1000000 + 1) + 1000000);
    }


    /**
     * 统一下单
     * @param wxPayUnifiedorderVo
     * @return
     */
    public JSONObject unifiedOrder(WxPayUnifiedorderVo wxPayUnifiedorderVo) {
        JSONObject jsonObject = new JSONObject();
        JSONObject result = new JSONObject();
        String orderId = wxPayUnifiedorderVo.getTradeNo();
        logger.info("订单号{}开始下单请求报文{}", orderId, JSONObject.toJSONString(wxPayUnifiedorderVo));
        String reqParam = getReqParam(wxPayUnifiedorderVo);
        String url = wxProperties.getUrl();
        String responseStr = "";
        responseStr = HttpPostUtil.getRemotePortData(url, reqParam);
        logger.info("订单号{}下单返回字符串{}", orderId, responseStr);
        if (!StringUtils.isEmpty(responseStr)) {
            try {
                org.json.JSONObject json = XML.toJSONObject(responseStr);
                org.json.JSONObject json1 = (org.json.JSONObject) json.get("xml");
                Iterator<String> iterator = json1.keys();
                while(iterator.hasNext()){
                    String str = iterator.next();
                    jsonObject.put(str, json1.getString(str));
                }
                logger.info("订单号{}下单返回字符串{}", orderId, jsonObject.toJSONString());
                String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
                String str = jsonObject.getString("nonce_str");
                String prepayId = jsonObject.getString("prepay_id");
                result.put("timeStamp", timeStamp);
                result.put("nonceStr", str);
                result.put("sign", getSign(timeStamp, str, prepayId));
                result.put("prepay_id", "prepay_id=" + prepayId);
                result.put("return_code", jsonObject.getString("return_code"));
                result.put("result_code", jsonObject.getString("result_code"));
                logger.info("订单号{}返前台字符串{}", orderId, result.toJSONString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;



    }

    /**
     * 获取下单参数
     * @param wxPayUnifiedorderVo
     * @return
     */
    private String getReqParam(WxPayUnifiedorderVo wxPayUnifiedorderVo) {

        String appid = wxProperties.getAppid();
        String mchId = wxProperties.getMchId();
        String nonceStr = UUID.randomUUID().toString().replace("-", "");
        String body = wxPayUnifiedorderVo.getGoodsBody();
        String outTradeNo = wxPayUnifiedorderVo.getTradeNo();
        String totalFee = wxPayUnifiedorderVo.getTotalFee();
        String spbillCreateIp = wxProperties.getSpbillCreateIp();
        String notifyUrl = wxProperties.getNotifyUrl();
        String tradeType = wxProperties.getTradeType();
        String openid = wxPayUnifiedorderVo.getOpenId();
        String key = wxProperties.getKey();
        logger.info("支付key{}", key);
        Map<String, String> reqParam = new HashMap<String, String>();
        reqParam.put("appid", appid);
        reqParam.put("mch_id", mchId);
        reqParam.put("nonce_str", nonceStr);
        reqParam.put("body", body);
        reqParam.put("out_trade_no", outTradeNo);
        reqParam.put("total_fee", totalFee);
        reqParam.put("spbill_create_ip", spbillCreateIp);
        reqParam.put("notify_url", notifyUrl);
        reqParam.put("trade_type", tradeType);
        reqParam.put("openid", openid);
        String sign = WXUtils.getSignNew(reqParam, key);
        reqParam.put("sign", sign);
        String reqStr = WXUtils.mapXml(reqParam);
        logger.info("订单号{}微信支付Body报文{}", outTradeNo, reqStr);
        try {
            reqStr = new String(reqStr.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  reqStr;


    }

    private String getSign(String timeStamp, String nonceStr, String prepayId){
        Map<String, String> reqParam = new HashMap<String, String>();
        reqParam.put("appId", wxProperties.getAppid());
        reqParam.put("timeStamp", timeStamp);
        reqParam.put("nonceStr", nonceStr);
        reqParam.put("package", "prepay_id=" + prepayId);
        reqParam.put("signType", "MD5");
        String sign = WXUtils.getSignNew(reqParam,  wxProperties.getKey());
        logger.info("微信支付反前台签名字段为{}", sign);
        return  sign;
    }
}
