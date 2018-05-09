package com.xuxue.dapp.red.packetes;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:36
 */
public class HttpsRequest implements IServiceRequest{
    private static final Logger LOG = LoggerFactory.getLogger(HttpsRequest.class);


    //表示请求器是否已经做了初始化工作
    private boolean hasInit = false;

    //连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    private void init() throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try(InputStream inputStream = this.getClass().getResourceAsStream("/apiclient_cert.p12")){
            keyStore.load(inputStream, Configure.getCertPassword().toCharArray());//设置证书密码
        }catch (CertificateException ex){
            LOG.warn("Failed load key store!",ex);
        }
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, Configure.getCertPassword().toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        hasInit = true;
    }

    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @return API回包的实际数据
     */
    public String sendPost(String url, String xml) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        if (!hasInit) {
            init();
        }
        String result;
        HttpPost httpPost = new HttpPost(url);
        StringEntity postEntity = new StringEntity(xml, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");//Content-Type: text/html; charset=gbk
        httpPost.setEntity(postEntity);
        httpPost.setConfig(requestConfig);
        try(CloseableHttpResponse response = httpClient.execute(httpPost)){
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        }
        System.out.println("==================result1:"+result);
        return result;
    }

    /**
     * 通过Https往API post &param=1&param=2 数据
     *
     * @param url    API地址
     * @param params 要提交的参数
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */

    public String sendPost(String url, List<HashMap> params)throws UnsupportedEncodingException{

        //if (!hasInit) {
         //   init();
        //}

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        //解决XStream对出现双下划线的bug
        //XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        //String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        //Util.log("API，POST过去的数据是：");
        //Util.log(params);

        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        //StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        //httpPost.addHeader("Content-Type", "text/xml");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (int i = 0 ; i < params.size(); i ++) {
        	HashMap param = params.get(i);
        	nvps.add(new BasicNameValuePair((String)param.get("key"), (String)param.get("val")));
        }

        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        //设置请求器的配置
        httpClient = HttpClients.custom().build();
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build());

        //Util.log("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            //log.e("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            //log.e("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            //log.e("http get throw SocketTimeoutException");

        } catch (Exception e) {
            //log.e("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }

    /**
     * 通过Https往API post &param=1&param=2 数据
     *
     * @param url    API地址
     * @return API回包的实际数据
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */

    public String sendGet(String url) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        //if (!hasInit) {
         //   init();
        //}

        String result = null;

        HttpGet httpGet = new HttpGet(url);

        //解决XStream对出现双下划线的bug
        //XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

        //将要提交给API的数据对象转换成XML格式数据Post给API
        //String postDataXML = xStreamForRequestPostData.toXML(xmlObj);

        //Util.log("API，POST过去的数据是：");
        //Util.log(params);



        httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");

        //设置请求器的配置
        httpClient = HttpClients.custom().build();
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build());

        //Util.log("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            //log.e("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            //log.e("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            //log.e("http get throw SocketTimeoutException");

        } catch (Exception e) {
            //log.e("http get throw Exception");

        } finally {
        	httpGet.abort();
        }

        return result;
    }
    
    public String sendJsonPost(String url, String json) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        //if (!hasInit) {
         //   init();
        //}

        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(json, "UTF-8");
        //httpPost.addHeader("Content-Type", "text/xml");
        
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpClient = HttpClients.custom().build();
        httpPost.setConfig(RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build());

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
            //log.e("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            //log.e("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            //log.e("http get throw SocketTimeoutException");

        } catch (Exception e) {
            //log.e("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }

    /**
     * 设置连接超时时间
     *
     * @param socketTimeout 连接时长，默认10秒
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        resetRequestConfig();
    }

    /**
     * 设置传输超时时间
     *
     * @param connectTimeout 传输时长，默认30秒
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        resetRequestConfig();
    }

    private void resetRequestConfig(){
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    }

    /**
     * 允许商户自己做更高级更复杂的请求器配置
     *
     * @param requestConfig 设置HttpsRequest的请求器配置
     */
    public void setRequestConfig(RequestConfig requestConfig) {
        requestConfig = requestConfig;
    }
}
