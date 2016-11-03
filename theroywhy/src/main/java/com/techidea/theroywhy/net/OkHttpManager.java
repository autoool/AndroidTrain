package com.techidea.theroywhy.net;

import android.content.Context;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;

/**
 * Created by zchao on 2016/11/1.
 */

public class OkHttpManager {

    private String TAG = "OkHttpManager";

    private OkHttpClient okHttpsOneWayClient;
    private OkHttpClient okHttpsBothWayClient;
    private Context context;

    private static OkHttpManager Instance;


    public OkHttpManager() {
    }

    public static OkHttpManager getInstance() {
        if (Instance == null)
            Instance = new OkHttpManager();
        return Instance;
    }

    public OkHttpManager setContext(Context context) {
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

            Response response = getOkHttpsOneWayClient()
                    .newCall(request).execute();
            result = response.body().string();
            System.out.println(TAG + " " + result);
        } catch (IOException e) {
            System.out.println(TAG + "Exception " + e.getMessage().toString());
        }
        return result;
    }


    public void setCertificates(InputStream clientStream, String clientPwd,
                                InputStream serverStream, String serverPwd) {
//trust server
        KeyStore clientStore;
        KeyStore serverStore;
        final String DEFAULT_KEY_TYPE = "bks";
        final String DEFAULT_KEY_MANAGER_TYPE = "X509";

        try {
            clientStore = KeyStore.getInstance(DEFAULT_KEY_TYPE);
            serverStore = KeyStore.getInstance(DEFAULT_KEY_TYPE);
            clientStore.load(clientStream, clientPwd.toCharArray());
            serverStore.load(serverStream, serverPwd.toCharArray());


            KeyManagerFactory keyManagerFactory;
            final TrustManagerFactory trustManagerFactory;

            keyManagerFactory = KeyManagerFactory.getInstance(DEFAULT_KEY_MANAGER_TYPE);
            trustManagerFactory = TrustManagerFactory.getInstance(DEFAULT_KEY_MANAGER_TYPE);

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

//            CustomSSLSocketFactory customSSLSocketFactory = new CustomSSLSocketFactory(keyManagerFactory.getKeyManagers(),
//                    trustManagerFactory.getTrustManagers(), new SecureRandom());
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_2)
                    .cipherSuites(
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                            CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                            CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
                    .build();
            okHttpsOneWayClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(),
                            (X509TrustManager) trustManagers[0]).build();
//            okHttpsOneWayClient = new OkHttpClient.Builder()
//                    .sslSocketFactory(sslContext.getSocketFactory())
//                    .build();


//            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            keyStore.load(null);
//            int index = 0;
//            for (InputStream certificate : certificates) {
//                String certificateAlias = Integer.toString(index++);
//                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
//
//                try {
//                    if (certificate != null)
//                        certificate.close();
//                } catch (IOException e) {
//                }
//            }
//
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//
//            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
//            clientKeyStore.load(context.getAssets().open("zchao_client.bks"), "123456".toCharArray());
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory
// .getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(clientKeyStore, "123456".toCharArray());
//
//
//            trustManagerFactory.init(keyStore);
//            sslContext.init(keyManagerFactory.getKeyManagers(),
// trustManagerFactory.getTrustManagers(),new SecureRandom());
////            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
//            okHttpsOneWayClient = new OkHttpClient.Builder().sslSocketFactory(sslContext.getSocketFactory())
//                    .build();
//            okHttpClient = new OkHttpClient.Builder().sslSocketFactory(null, sslContext.getSocketFactory())
//                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setBothWayCertificates(InputStream... certificates) {
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
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0] )
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //单向认证
    public OkHttpClient getOkHttpsOneWayClient() {
        return okHttpsOneWayClient;
    }

    public OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        return okHttpClient;
    }
}