package com.techidea.theroywhy.net.httpclient;

import android.content.Context;
import android.util.Log;

import com.techidea.theroywhy.R;
import com.techidea.theroywhy.net.Contast;
import com.techidea.theroywhy.net.SSLTrustSocketFactoryEx;
import com.techidea.theroywhy.net.interf.ResponseCallBack;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okio.Buffer;


/**
 * Created by zchao on 2016/11/2.
 */

public class HttpClientManager {

    private String TAG = "HttpClientManager";
    private HttpClient httpClient;
    private HttpParams httpParams;
    private Context context;
    private ResponseCallBack responseCallBack;


    public HttpClientManager(Context context) {
        this.httpParams = new BasicHttpParams();
        this.context = context;
    }

    //
    public void httpClientGet(String url, ResponseCallBack responseCallBack) {
        this.responseCallBack = responseCallBack;
        String result = "";
        url = url + "/greeting?name=User";
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
        if (responseCallBack != null)
            responseCallBack.callBack(result);
    }

    public void httpsSingleClientGet(String url, ResponseCallBack callBack) {
        this.responseCallBack = callBack;
        String result = "";
        HttpPost httpGet = new HttpPost(url);
        try {
            HttpResponse httpResponse = getHttpsSingleStrClient(new Buffer().writeUtf8(Contast.CER_SERVER_ZC).inputStream())
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
        if (responseCallBack != null)
            responseCallBack.callBack(result);
    }

    public void httpsBothClientGet(String url, ResponseCallBack callBack) {
        this.responseCallBack = callBack;
        String result = "";
        HttpPost httpGet = new HttpPost(url);
        try {
            HttpResponse httpResponse = getHttpsBothClient()
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
        if (responseCallBack != null)
            responseCallBack.callBack(result);
    }


    private HttpClient getHttpClient() {
        httpClient = new DefaultHttpClient();
        return httpClient;
    }

    private HttpClient getHttpsSingleBksClient() {
        if (null == httpClient) {
            try {
                InputStream trustStream = context.getResources().openRawResource(R.raw.zc_serverbks);
                KeyStore trustStore = KeyStore.getInstance("bks");
                trustStore.load(trustStream, "123456".toCharArray());
                SSLSocketFactory sslSocketFactory = new SSLTrustSocketFactoryEx(trustStore);
                httpClient = new DefaultHttpClient();
                httpClient.getConnectionManager().getSchemeRegistry()
                        .register(new Scheme("https", sslSocketFactory, 8443));
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultHttpClient();
            }
        }
        return httpClient;
    }

    private HttpClient getHttpsSingleStrClient(InputStream... certificates) {
        if (null == httpClient) {
            try {

                SSLContext sslContext = SSLContext.getInstance("TLS");
                X509TrustManager x509TrustManager;
                try {
                    x509TrustManager = trustManagerForCertificates(
                            new Buffer().writeUtf8(Contast.CER_SERVER_ZC).inputStream());
                    sslContext.init(null, new TrustManager[]{x509TrustManager}, new SecureRandom());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                InputStream trustStream = new Buffer().writeUtf8(Contast.CER_SERVER_ZC).inputStream();
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(trustStream, "123456".toCharArray());
//                SSLSocketFactory sslSocketFactory = new SSLTrustSocketFactoryEx(trustStore);
                SSLSocketFactory sslSocketFactory = new SSLSocketFactory(null, "123456", trustStore);

                httpClient = new DefaultHttpClient();
                httpClient.getConnectionManager().getSchemeRegistry()
                        .register(new Scheme("https", sslSocketFactory, 8443));
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultHttpClient();
            }
        }
        return httpClient;
    }


    //https双向认证
    private HttpClient getHttpsBothClient() {
        if (null == httpClient) {
            try {
                InputStream keyStream = context.getResources().openRawResource(R.raw.zc_clientbks);
                InputStream trustStream = context.getResources().openRawResource(R.raw.zc_serverbks);
                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(trustStream, "123456".toCharArray());
                KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(keyStream, "123456".toCharArray());
                SSLSocketFactory sslSocketFactory = new SSLSocketFactory(keyStore, "123456", trustStore);

                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
                keyManagerFactory.init(keyStore, "123456".toCharArray());
                trustManagerFactory.init(trustStore);

                httpClient = new DefaultHttpClient();
                httpClient.getConnectionManager().getSchemeRegistry()
                        .register(new Scheme("https", sslSocketFactory, 8443));
            } catch (Exception e) {
                e.printStackTrace();
                return new DefaultHttpClient();
            }
        }
        return httpClient;
    }

    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "123456".toCharArray();
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
