package com.techidea.theroywhy.net;

import android.content.Context;
import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import com.techidea.theroywhy.R;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


/**
 * Created by zchao on 2016/11/2.
 */

public class HttpClientManager {
//    HttpClient

    private String TAG = "HttpClientManager";
    private HttpClient httpClient;
    private HttpParams httpParams;
    private Context context;

    public HttpClientManager(Context context) {
        this.httpParams = new BasicHttpParams();
        this.context = context;
    }

    //
    public String httpClientGet(String url) {
        String result = "";
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = getHttpClient().execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = "Error Response" + httpResponse.getStatusLine().toString();
            }
        } catch (Exception e) {
            result = e.getMessage().toString();
        }
        System.out.println(result);
        Log.v(TAG, result);
        return result;
    }

    public String httpClientPost(String url, List<NameValuePair> params) {
        HttpPost httpPost = new HttpPost(url);
        String result = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = "Error Response: " +
                        httpResponse.getStatusLine().toString();
            }
        } catch (Exception e) {
            result = e.getMessage().toString();
        }
        System.out.println(result);
        Log.v(TAG, result);
        return result;
    }

    public String httpsClientGet(String url) {
        String result = "";
        HttpPost httpGet = new HttpPost(url);
        try {
            HttpResponse httpResponse = getHttpsClient()
                    .execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = "Error Response" + httpResponse.getStatusLine().toString();
            }
        } catch (Exception e) {
            result = e.getMessage().toString();
        }
        System.out.println(result);
        Log.v(TAG, result);
        return result;
    }

    public void httpsClientPost(String url, List<NameValuePair> params) {
        HttpPost httpPost = new HttpPost(url);
        String result = "";
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse httpResponse = getHttpsClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(httpResponse.getEntity());
            } else {
                result = "Error Response: " +
                        httpResponse.getStatusLine().toString();
            }
        } catch (Exception e) {
            result = e.getMessage().toString();
        }
        System.out.println(result);
        Log.v(TAG, result);
    }


    private HttpClient getHttpClient() {

//        设置连接超时和 Socket 超时，以及 Socket 缓存大小
        HttpConnectionParams.setConnectionTimeout(httpParams, 30 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        //设置重定向，缺省为 true
        HttpClientParams.setRedirecting(httpParams, true);
        //user agent
        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams, userAgent);
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//        schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager connMgr = new ThreadSafeClientConnManager(httpParams, schReg);
//        httpClient = new DefaultHttpClient();
        httpClient = new DefaultHttpClient(connMgr, httpParams);
        return httpClient;
    }

    //https双向认证

    private HttpClient getHttpsClient() {
        if (null == httpClient) {
            try {
                InputStream keyStream = context.getResources().openRawResource(R.raw.zc_clientbks);
                InputStream trustStream = context.getResources().openRawResource(R.raw.zc_serverbks);
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(trustStream, "123456".toCharArray());
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(keyStream, "123456".toCharArray());
                SSLSocketFactory sslSocketFactory = new SSLSocketFactory(keyStore, "123456", trustStore);
//                sslSocketFactory.setHostnameVerifier(SSLSocketFactoryEx.ALLOW_ALL_HOSTNAME_VERIFIER);
//                SSLSocketFactoryEx sslSocketFactory = new SSLSocketFactoryEx(keyStore);
//                SSLCertificateSocketFactory

                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                keyManagerFactory.init(keyStore, "123456".toCharArray());
                trustManagerFactory.init(trustStore);
                TLSSocketFactory tlsSocketFactory = new TLSSocketFactory(
                        keyManagerFactory.getKeyManagers(),
                        trustManagerFactory.getTrustManagers(),
                        new SecureRandom());

                HttpParams params = new BasicHttpParams();
                HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
                HttpProtocolParams.setUseExpectContinue(params, true);
                ConnManagerParams.setTimeout(params, 10000);
                HttpConnectionParams.setConnectionTimeout(params, 10000);
                HttpConnectionParams.setSoTimeout(params, 10000);
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("https", sslSocketFactory, 8443));
                ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);
                httpClient = new DefaultHttpClient(clientConnectionManager, params);
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultHttpClient();
            }
        }
        return httpClient;
    }

    private HttpClient getHttpsBothWayClient() {
        httpClient = new DefaultHttpClient();
        return httpClient;
    }

    private class SSLSocketFactoryEx extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public SSLSocketFactoryEx(KeyStore keyStore)
                throws NoSuchAlgorithmException, KeyManagementException,
                KeyStoreException, UnrecoverableKeyException {
            super(keyStore);

            TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{trustManager}, null);
        }

        @Override
        public Socket createSocket() throws IOException {
            return sslContext.getSocketFactory().createSocket();
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
            return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }
    }
}
