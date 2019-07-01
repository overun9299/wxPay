package overun.config;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

/**
 * @ClassName: WxPayConfig
 * @Description:
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/20 20:13
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
public class WxPayConfig implements WXPayConfig {


    /** appid */
    @Override
    public String getAppID() {
        return null;
    }

    /** 商户id */
    @Override
    public String getMchID() {
        return null;
    }

    /** 商户api安全key，在商户平台api安全目录下可设置 */
    @Override
    public String getKey() {
        return null;
    }

    /**  */
    @Override
    public InputStream getCertStream() {
        return null;
    }

    /**  */
    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    /**  */
    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
