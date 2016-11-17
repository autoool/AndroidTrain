package com.techidea.theroywhy.net;

import android.provider.SyncStateContract;
import android.util.Log;

import com.techidea.theroywhy.net.httpclient.HttpClientManager;
import com.techidea.theroywhy.net.interf.ResponseCallBack;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CipherSuite;
import okhttp3.Connection;
import okhttp3.ConnectionSpec;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okio.Buffer;

/**
 * Created by zchao on 2016/11/2.
 */

public class CustomTrust {

    private String TAG = "CustomTrust";

    private final OkHttpClient client;
    private CustomSSLSocketFactory customSSLSocketFactory;

    private ResponseCallBack responseCallBack;

    public CustomTrust() {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
//                .hostnameVerifier(hostnameVerifier)
//                .connectionSpecs(Collections.singletonList(spec))
                .build();
    }

//    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
//        @Override
//        public boolean verify(String hostname, SSLSession session) {
//            HostnameVerifier hv =
//                    HttpsURLConnection.getDefaultHostnameVerifier();
////            return hv.verify("https://www.barehuman.com:8443/", session);
//            return true;
//        }
//    };

    public void run(ResponseCallBack responseCallBack) {
        this.responseCallBack = responseCallBack;
        String result = "no data";
        Request request = new Request.Builder()
                .url(Contast.URL_ALI)
                .build();
//        String param = "00 5B 60 00 03 00 00 60 31 00 31 13 12 08 00 00 20 00 00 00 c0 00" +
//                " 16 00 00 01 31 30 30 30 30 31 35 39 38 38 30 32 31 30 30 31 30 32 31 30 31 " +
//                "36 30 00 11 00 00 00 01 00 30 00 29 53 65 71 75 65 6e 63 65 20 4e 6f 31 36 33 " +
//                "31 35 30 53 58 58 2d 34 43 33 30 34 31 31 39 00 03 30 31 20";
//        MediaType str = MediaType.parse("text/x-markdown; charset=utf-8");
//        RequestBody requestBody = RequestBody.create(str, param);
//        Request request = new Request.Builder()
//                .header("POST", "/mjc/webtrans/VPB_lb HTTP/1.1")
//                .header("HOST:","202.101.25.188:20141")
//                .header("User-Agent:", "Donjin Http 0.1")
//                .header("Content-Type:", "x-ISO-TPDU/x-auth")
//                .header("Accept:", "*/*")
//                .header("Content-Length:", String.valueOf(param.length()))
//                .url("https://202.101.25.188:20141/")
//                .post(requestBody)
//                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            result = response.body().string();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Log.v(TAG, e.getMessage().toString());
            result = e.getMessage().toString();
        }
        if (responseCallBack != null) {
            responseCallBack.callBack(result);
        }
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {

        InputStream inputStream =
                new Buffer()
                        .writeUtf8(Contast.CER_SERVER_ZC)
                        .inputStream();

//        InputStream inputStream =
//                new Buffer()
//                        .writeUtf8(Contast.CER_SERVER_ZC)
//                        .writeUtf8(Contast.CER_CLIENT_ZC)
//                        .inputStream();
        return inputStream;
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p>
     * <p>See also {@link }, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     * <p>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
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
