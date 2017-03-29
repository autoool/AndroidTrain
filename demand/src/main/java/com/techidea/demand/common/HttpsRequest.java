package com.techidea.demand.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Created by zchao on 2017/3/23.
 */

public class HttpsRequest implements IServiceRequest {

    private boolean hasInit = false;
    private int socketTimeout = 10000;
    private int connectTimeout = 30000;
    private CloseableHttpClient httpClient;

    @Override
    public String sendPost(String url, Object xmlObj) {
        if (!this.hasInit) {
            init();
        }
        String result = null;
        HttpPost httpPost = new HttpPost(url);
//        XStream xStreamForRequestPostData =
        httpPost.addHeader("Content-Type", "text/xml");
//        httpPost.setEntity();
        try {
            HttpResponse response = this.httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {

        } finally {
            httpPost.abort();
        }
        return result;
    }

    private void init() {
        this.httpClient = HttpClients.createDefault();
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    private void resetRequestConfig(){

    }
}
