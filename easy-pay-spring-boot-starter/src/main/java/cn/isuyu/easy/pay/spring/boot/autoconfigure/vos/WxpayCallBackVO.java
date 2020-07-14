package cn.isuyu.easy.pay.spring.boot.autoconfigure.vos;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/7 下午2:37
 */
@Data
public class WxpayCallBackVO implements Serializable {

    /**
     * 商户的订单号
     */
    private String outTradeNo;

    /**
     * 微信支付订单号
     */
    private String transactionId;

    /**
     * 支付随机字符串
     */
    private String nonceStr;

    /**
     * 公众账号ID
     */
    private String appId;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 支付结果
     */
    private String resultCode;

    /**
     * 订单金额
     */
    private String totalFee;

    /**
     * 签名结果，true表示签名通过
     */
    private Boolean signResult;

    /**
     * 支付完成时间
     */
    private String timeEnd;
}
