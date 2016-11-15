package com.techidea.theroywhy.net;

import android.content.Context;

import com.techidea.theroywhy.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zchao on 2016/11/1.
 */

public class OkHttpsManager {

    private String TAG = "OkHttpsManager";

    private OkHttpClient okHttpsOneWayClient;
    private OkHttpClient okHttpsBothWayClient;
    private Context context;

    private static OkHttpsManager Instance;


    public OkHttpsManager() {
    }

    public static OkHttpsManager getInstance() {
        if (Instance == null)
            Instance = new OkHttpsManager();
        return Instance;
    }

    public OkHttpsManager setContext(Context context) {
        this.context = context;
        return Instance;
    }

    public String runHttp(String url) {
        String result = "";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = getOkHttpClient()
                    .newCall(request).execute();
            result = response.body().string();
            System.out.println(TAG + " " + result);
        } catch (IOException e) {
            System.out.println(TAG + "Exception " + e.getMessage().toString());
            result = e.getMessage().toString();
        }
        return result;
    }

    public String runHttpOneWay(String url) {
        String result = "";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = getOkHttpsOneWayClient()
                    .newCall(request).execute();
            result = response.body().string();
            System.out.println(TAG + " " + result);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(TAG + "Exception " + e.getMessage().toString());
            result = e.getMessage().toString();
        }
        return result;
    }

    public String runHttpBothWay(String url) {
        String result = "";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = getOkHttpsBothWayClient()
                    .newCall(request).execute();
            result = response.body().string();
            System.out.println(TAG + " " + result);
        } catch (IOException e) {
            System.out.println(TAG + "Exception " + e.getMessage().toString());
        }
        return result;
    }


    public void setBothCertificates(InputStream clientStream, String clientPwd,
                                    InputStream serverStream, String serverPwd) {
//trust server
        KeyStore clientStore;
        KeyStore serverStore;
        final String DEFAULT_KEY_TYPE = "BKS";
        final String DEFAULT_KEY_MANAGER_TYPE = "X509";

        try {
            clientStore = KeyStore.getInstance(DEFAULT_KEY_TYPE);
            serverStore = KeyStore.getInstance(DEFAULT_KEY_TYPE);
            clientStore.load(clientStream, clientPwd.toCharArray());
            serverStore.load(serverStream, serverPwd.toCharArray());


            KeyManagerFactory keyManagerFactory;
            final TrustManagerFactory trustManagerFactory;

            keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            keyManagerFactory.init(clientStore, clientPwd.toCharArray());
            trustManagerFactory.init(serverStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(), new SecureRandom());
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }

            /*
            该方法不行
            okHttpsBothWayClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(),(X509TrustManager)trustManagers[0])
                    .build();*/
            okHttpsBothWayClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //单项认证增加verify
    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            HostnameVerifier hv =
                    HttpsURLConnection.getDefaultHostnameVerifier();
//            return hv.verify("https://www.barehuman.com", session);
            return true;
        }
    };

    //单向认证是可以的
    public void setONECertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            clientKeyStore.load(context.getResources().openRawResource(R.raw.zc_clientbks), "123456".toCharArray());
            ;

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, "123456".toCharArray());


            sslContext.init(
                    keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            okHttpsOneWayClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  /*  public void setBothWayCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore serverKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            serverKeyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                serverKeyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }


            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(serverKeyStore);

            //初始化keystore
            KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            clientKeyStore.load(context.getAssets().open("zhy_client.bks"), "123456".toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, "123456".toCharArray());

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }

            sslContext.init(keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            okHttpsBothWayClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0])
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    //单向认证
    public OkHttpClient getOkHttpsOneWayClient() {
        return okHttpsOneWayClient;
    }

    //单向认证
    public OkHttpClient getOkHttpsBothWayClient() {
        return okHttpsBothWayClient;
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        return okHttpClient;
    }
}