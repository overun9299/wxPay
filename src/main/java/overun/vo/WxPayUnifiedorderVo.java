package overun.vo;

/**
 * @ClassName: WxPayUnifiedorderVo
 * @Description: 统一下单Vo
 * @author: 薏米滴答-西安-ZhangPY
 * @version: V1.0
 * @date: 2019/6/22 16:35
 * @Copyright: 2019 www.yimidida.com Inc. All rights reserved.
 */
public class WxPayUnifiedorderVo {

    /** openId */
    private String openId;
    /** 商户订单号 */
    private String tradeNo;
    /** 商品或支付单简要描述 */
    private String goodsBody;
    /** 定单金额 */
    private String totalFee;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getGoodsBody() {
        return goodsBody;
    }

    public void setGoodsBody(String goodsBody) {
        this.goodsBody = goodsBody;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
}
