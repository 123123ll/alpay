package cn.isuyu.easy.pay.spring.boot.autoconfigure.vos;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/5/15 下午2:23
 */
@Data
public class AlipayCloseOrderVO implements Serializable {
    /**
     * 返回的状态码
     */
    private String code;

    /**
     * 返回的信息
     */
    private String msg;

    /**
     * 交易的流水号
     */
    private String out_trade_no;

    /**
     * 支付宝交易流水号
     */
    private String trade_no;

    /**
     * 错误码
     */
    private String sub_code;

    /**
     * 错误信息
     */
    private String sub_msg;

}
