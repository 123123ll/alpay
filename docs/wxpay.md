### 微信支付调用文档(sdk version:3.2.0)

- [微信二维码生成方法](https://github.com/easy-pay/easy-pay/blob/master/doc/wxpay.md#%E4%BA%8C%E7%BB%B4%E7%A0%81%E7%94%9F%E6%88%90)

- [微信H5支付方法](https://github.com/easy-pay/easy-pay/blob/master/doc/wxpay.md#%E5%BE%AE%E4%BF%A1h5%E6%94%AF%E4%BB%98%E4%B8%8B%E5%8D%95)

- [微信支付回调方法](https://github.com/easy-pay/easy-pay/blob/master/doc/wxpay.md#%E5%BE%AE%E4%BF%A1%E6%94%AF%E4%BB%98%E5%9B%9E%E8%B0%83)

- [微信订单关闭方法](https://github.com/easy-pay/easy-pay/blob/master/doc/wxpay.md#%E8%AE%A2%E5%8D%95%E5%85%B3%E9%97%AD)

- [微信退款方法](https://github.com/easy-pay/easy-pay/blob/master/doc/wxpay.md#%E9%80%80%E6%AC%BE)

- [微信退款查询方法](https://github.com/easy-pay/easy-pay/blob/master/doc/wxpay.md#%E9%80%80%E6%AC%BE%E6%9F%A5%E8%AF%A2)

### 使用

```java
    @Autowired
    private WxPayService wxPayService;

```


#### 二维码生成

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| body  |String|  必须  |   订单描述
| totalFee  |String|  必须  |   订单金额

##### 调用示例

```java
    /**
     * 微信二维码支付
     * @param qrcodeDTO
     * @return
     */
    @RequestMapping(value = "qrcode")
    public WxpayQrcodeVO wxQrcode(WxpayQrcodeDTO qrcodeDTO) {

        return wxPayService.qrcode(qrcodeDTO);
    }
```
##### 浏览器访问示例
```html
127.0.0.1:9999/wx/qrcode?totalFee=0.01&body=测试微信二维码支付&outTradeNo=999999
```

##### Easy-Pay微信二维码响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| subAppId  |String|  否  |  
| subMchId  |String|  否  |  
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果
| prepayId  |String|  必须  |  预支付交易会话标识
| tradeType  |String|  必须  |  支付类型
| codeUrl  |String|  必须  |  生成的支付二维码地址



##### Easy-Pay微信二维码响应示例
```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "xxxxx", 
    "mchId": "xxxxxx", 
    "nonceStr": "oCuq5OPXTyulNaIK", 
    "sign": "396591213376A190D880C5B834C85DC9", 
    "xmlString": "", 
    "prepayId": "wx1110210690920174040601bd2188238263", 
    "tradeType": "NATIVE", 
    "codeUrl": "weixin://wxpay/bizpayurl?pr=Wj2oCm7"
}
```

#### 微信H5支付下单

##### 描述

拿到`mwebUrl`后直接通过location.href=${mwebUrl}，spbillCreateIp必须是用户的真实ip，否则会报`商家参数格式有误，请联系商家解决`
就能直接跳到微信支付的界面，这里需要注意一点的是配置的回调地址和微信支付商户中心H5支付配置的回调地址要一样，
否则会报`商家存在未配置的参数，请联系商家解决`这个异常。如果链接唤起了微信支付的话，表示成功啦，支付回调和二维码支付的一样，直接调用easy-pay
的回调策略就好。


###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| body  |String|  必须  |   订单描述
| totalFee  |String|  必须  |   订单金额
| spbillCreateIp  |String|  必须  |  当前支付用户的ip地址

##### 调用示例

```java
    /**
     * h5支付
     * @param wxQrcodeDTO
     * @return
     * @throws Exception
     */
    public WxpayH5CreateOrderVO h5pay(WxpayQrcodeDTO wxQrcodeDTO) {
        wxQrcodeDTO.setSpbillCreateIp(IpUtils.getIpAddress(request));
        return wxPayService.h5Pay(wxQrcodeDTO);
    }
```
##### 浏览器访问示例
```html
127.0.0.1:9999/wx/h5pay?totalFee=0.01&body=测试微信二维码支付&outTradeNo=888888
```

##### Easy-Pay微信H5支付下单响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| subAppId  |String|  否  |  
| subMchId  |String|  否  |  
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果
| prepayId  |String|  必须  |  预支付交易会话标识
| tradeType  |String|  必须  |  支付类型
| mweb_url  |String|  必须  |  微信WAP支付跳转url

##### Easy-Pay微信H5支付下单响应示例
```json
{
    "returnCode": "SUCCESS",
    "returnMsg": "OK",
    "resultCode": "SUCCESS",
    "appid": "xxxxx",
    "mchId": "xxxxx",
    "nonceStr": "6DcWkt7S6MdeKmhH",
    "sign": "93F90EA69ABE3954E14D067D061C21CF",
    "xmlString":"",
    "prepayId": "wx2316363383065080d63201171221141900",
    "tradeType": "MWEB",
    "mwebUrl": "https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx2316363383065080d63201171221141900&package=1137871333"
}
```


#### 微信支付回调

##### 描述

支付回调是买家支付成功之后，微信用来异步通知商家用户的当前支付状态，用户在接收到微信回调信息后，如果支付成功后必须返回一个`SUCCESS`告诉
微信方，我已经成功接收到了回调信息，不用再发送了，要是不给微信返回`SUCCESS`，微信会一直调用我们提供的回调接口，只是调用的时间间隔可能会
越来越长而已。总之，如果接收到了回调信息一定要响应`SUCCESS`给微信。

##### 调用示例

```java
    /**
     * 微信支付回调
     * @return
     */
    @RequestMapping(value = "callback")
    public String payCallBack() {

        WxpayCallBackVO wxpayCallBackVO = wxPayService.callback();
        log.info(wxpayCallBackVO.getOutTradeNo() +"-----"+ wxpayCallBackVO.getResultCode());
        //判断验签是否通过并且支付结果是不是成功
        if (wxpayCallBackVO.getSignResult() && wxpayCallBackVO.getResultCode().equals("SUCCESS")) {
            WebSocketService.sendMessage(JSON.toJSONString(wxpayCallBackVO),wxpayCallBackVO.getOutTradeNo());
            return "SUCCESS";
        }
        return "FAILER";
    }
```

##### Easy-Pay微信支付回调响应参数
| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| appId  |String|  必须  |  公众号appid
| mchId  |String|  必须  |  商户的Id
| nonceStr  |String|  必须  |  随机字符串
| outTradeNo  |String|  必须  |  商户交易的流水号
| resultCode  |String|  必须  |  交易结果
| signResult  |String|  必须  |  验签结果(Easy-Pay会对微信返回的结果进行验签)
| timeEnd  |String|  必须  |  支付时间
| totalFee  |String|  必须  |  订单总金额
| transactionId  |String|  必须  |  微信方交易流水号


##### Easy-Pay微信支付回调响应示例
```json
{
    "appId": "wx9c2707e2093dee43", 
    "mchId": "1495387912", 
    "nonceStr": "999999999", 
    "outTradeNo": "999999999", 
    "resultCode": "SUCCESS", 
    "signResult": true, 
    "timeEnd": "20190511104301", 
    "totalFee": "0.01", 
    "transactionId": "4200000338201905113432771893"
}
```

#### 订单关闭

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号

##### 调用示例

```java
    /**
     * 微信关闭订单
     * @return
     */
    @RequestMapping(value = "closeOrder")
    public WxpayCloseOrderVO closeOrder(WxpayCloseOrderDTO closeOrderDTO) {
        return wxPayService.closeOrder(closeOrderDTO);
    }
```
##### 浏览器访问示例
```html
127.0.0.1:9999/wx/closeOrder?outTradeNo=999999
```

##### Easy-Pay微信关闭订单响应参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果

```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "111111111111", 
    "mchId": "1111111111", 
    "nonceStr": "aBIpVLvtHg2c5A0F", 
    "sign": "34A3AB4D484CABC6B15CADA8976EF77A", 
    "xmlString": ""
}
```


#### 退款

> 该api必须使用证书，必须放在项目根目录下，证书名称必须和配置文件中配置的一致

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号
| outRefundNo  |String|  必须  |   商户退款单号
| totalFee  |String|  必须  |   订单金额
| refundFee  |String|  必须  |   退款金额

##### 调用示例

```java
    /**
     * 微信退款
     * @param wxpayRefundDTO
     * @return
     */
    @RequestMapping(value = "refund")
    public WxpayRefundVO wxRefund(WxpayRefundDTO wxpayRefundDTO) {
        return wxPayService.refund(wxpayRefundDTO);
    }
```
##### 浏览器访问示例
```html
127.0.0.1:9999/wx/refund?totalFee=0.01&refundFee=0.01&outTradeNo=999999999&outRefundNo=999999999
```
##### Easy-Pay微信退款响应参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果
| outTradeNo  |String|  必须  | 商户交易流水号
| transactionId  |String|  必须  | 微信方交易流水号
| outRefundNo  |String|  必须  | 商户交易退款流水号
| refundId  |String|  必须  | 微信方交易退款流水号
| refundFee  |String|  必须  | 退款金额
| totalFee  |String|  必须  | 订单总金额

```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "xxxxx", 
    "mchId": "xxxxx", 
    "nonceStr": "H5NcN0nv4FpByk2R", 
    "sign": "B1670926D1C9D819060DD1E502E4B131", 
    "xmlString": "", 
    "outTradeNo": "999999999", 
    "transactionId": "4200000338201905113432771893", 
    "outRefundNo": "999999999", 
    "refundId": "50000500372019051109519075276", 
    "refundFee": "0.01", 
    "totalFee": "0.01"
}
```


#### 退款查询

###### 请求参数

| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| outTradeNo  |String|  必须  |   商户订单号

##### 调用示例

```java
    /**
     * 微信退款查询
     * @param refundQueryDTO
     * @return
     */
    @RequestMapping(value = "refundQuery")
    public WxpayRefundQueryVO refundQuery(WxpayRefundQueryDTO refundQueryDTO) {
        return wxPayService.refundQuery(refundQueryDTO);
    }
```
##### 浏览器访问示例
```html
127.0.0.1:9999/wx/refundQuery?outTradeNo=999999999
```
##### Easy-Pay微信退款查询响应参数


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| returnCode  |String|  必须  |  返回状态码
| returnMsg  |String|  必须  |   结果响应信息
| resultCode  |String|  必须  |   业务结果状态码
| errCode  |String|  否  |  错误状态码
| errCodeDes  |String|  否  |  错误描述
| appid  |String|  必须  |  公众账号ID
| mchId  |String|  必须  |  商户ID
| nonceStr  |String|  必须  |  随机字符串
| sign  |String|  必须  |  对返回结果签名
| xmlString  |String|  必须  | 微信响应未处理的xml结果
| outTradeNo  |String|  必须  | 商户交易流水号
| refundFee  |String|  必须  | 退款金额
| outRefundNo  |String|  必须  | 商户交易退款流水号
| refundCount  |String|  必须  | 退款次数
| cashFee  |String|  必须  | 退回金额

```json
{
    "returnCode": "SUCCESS", 
    "returnMsg": "OK", 
    "resultCode": "SUCCESS", 
    "appid": "xxxxx", 
    "mchId": "xxxxx", 
    "nonceStr": "uX6L4XoVaage5SQc", 
    "sign": "24A379FE304D8ED3FF7E755C6F07846D", 
    "xmlString": "", 
    "outTradeNo": "999999999", 
    "refundFee": 0.01, 
    "refundCount": 1, 
    "cashFee": 0.01
}
```
