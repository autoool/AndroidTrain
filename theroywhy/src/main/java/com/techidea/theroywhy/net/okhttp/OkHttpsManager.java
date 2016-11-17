package com.techidea.theroywhy.net.okhttp;

import android.content.Context;

import com.techidea.theroywhy.net.CustomSSLSocketFactory;

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
            System.out.println(TAG + "Exception " + e.getMessage());
        }
        return result;
    }


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

            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            okHttpsOneWayClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setBothCertificates(InputStream clientStream, String clientPwd,
                                    InputStream serverStream, String serverPwd) {
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

            okHttpsBothWayClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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