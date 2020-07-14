package cn.isuyu.easy.pay.spring.boot.autoconfigure.utils;

import cn.isuyu.easy.pay.spring.boot.autoconfigure.properties.WxPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/29 下午2:11
 */
@Slf4j
public class HttpUtils {
    /**
     * post请求（用于请求xml格式的参数）
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/xml");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity,charSet);
                return jsonString;
            }
            else{

            }
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 带证书请求
     * @param url
     * @param data
     * @return
     * @throws Exception
     */
    public static String doRefund(String url, String data, WxPayProperties wxPayProperties) throws Exception {
        /**
         * PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
         */
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        InputStream instream = HttpUtils.class.getClassLoader().getResourceAsStream(wxPayProperties.getCertName());
        if(instream == null) {
            log.error("证书未找到，请保证项目根目录下存在名为【{}】的证书文件",wxPayProperties.getCertName());
        }
        try {
            keyStore.load(instream, wxPayProperties.getMchId().toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, wxPayProperties.getMchId().toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url);
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();
                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
