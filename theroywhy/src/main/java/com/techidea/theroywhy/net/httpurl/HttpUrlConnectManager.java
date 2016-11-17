package com.techidea.theroywhy.net.httpurl;

import com.techidea.theroywhy.net.Contast;
import com.techidea.theroywhy.net.interf.ResponseCallBack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import okio.Buffer;

/**
 * Created by zchao on 2016/11/2.
 */

public class HttpUrlConnectManager {

    private static HttpUrlConnectManager Instance;

    private ResponseCallBack responseCallBack;


    public HttpUrlConnectManager() {
    }

    public static HttpUrlConnectManager getInstance() {
        if (Instance == null)
            Instance = new HttpUrlConnectManager();
        return Instance;
    }

    public void get(String urlStr, ResponseCallBack responseCallBack) {
        this.responseCallBack = responseCallBack;
        HttpURLConnection connection = null;
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        URL serverAddress = null;
        try {
            serverAddress = new URL(urlStr);
            //1) open connection
            connection = (HttpURLConnection) serverAddress.openConnection();


            //2) connection settings

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

//3)connect

            connection.connect();

            //4)write data

            // get the output stream writer and write the output to the server

            // not needed in this example

            // wr = new OutputStreamWriter(connection.getOutputStream());

            // wr.write("....");

            // wr.flush();


            //5)read the result from the server

            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            String result = sb.toString();
            System.out.println(result);
            if (responseCallBack != null)
                responseCallBack.callBack(result);
            //6)close
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void httpsSingleGet(String urlStr, ResponseCallBack responseCallBack) {
        this.responseCallBack = responseCallBack;
        HttpsURLConnection connection = null;
        OutputStreamWriter wr = null;
        BufferedReader rd = null;
        StringBuilder sb = new StringBuilder();
        String line = null;
        URL serverAddress = null;
        try {
            serverAddress = new URL(urlStr);
            //1) open connection
            connection = (HttpsURLConnection) serverAddress.openConnection();
            connection.setSSLSocketFactory(getSSLSocketFactory(
                    new Buffer().writeUtf8(Contast.CER_SERVER_ZC).inputStream()));

            //2) connection settings

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setReadTimeout(10000);

//3)connect

            connection.connect();

            //4)write data

            // get the output stream writer and write the output to the server

            // not needed in this example

            // wr = new OutputStreamWriter(connection.getOutputStream());

            // wr.write("....");

            // wr.flush();


            //5)read the result from the server

            rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            String result = sb.toString();
            System.out.println(result);
            if (responseCallBack != null)
                responseCallBack.callBack(result);
            //6)close
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private SSLSocketFactory getSSLSocketFactory(InputStream inputStream) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            KeyStore trustKeyStore = KeyStore.getInstance("BKS");

//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            TrustManagerFactory trustManagerFactory =
//                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//
//            sslContext.init(
//                    null,
//                    trustManagerFactory.getTrustManagers(),
//                    new SecureRandom());
//
//            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}
