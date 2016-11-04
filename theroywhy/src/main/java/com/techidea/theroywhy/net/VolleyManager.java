package com.techidea.theroywhy.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techidea.theroywhy.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by zchao on 2016/11/4.
 */

public class VolleyManager {

    private Context context;
    private RequestQueue requestQueue;
    private RequestQueue requestQueueHttps;
    private String response = "no data";

    public VolleyManager(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
        HttpStack httpStack = new HurlStack(
                null, createSslSocketFactory(context)
        );
        this.requestQueueHttps = Volley.newRequestQueue(context, httpStack);
    }

    public String requestGet(String url) {

        StringRequest stringRequest = new
                StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        System.out.println(s);
                        response = s;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        volleyError.printStackTrace();
                        System.out.println(volleyError.getMessage().toString());
                        response = volleyError.getMessage().toString();
                    }
                });
        this.requestQueue.add(stringRequest);
        return response;
    }

    public String requestHttpsGet(String url) {
        StringRequest stringRequest = new
                StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        System.out.println(s);
                        response = s;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        System.out.println(volleyError.getMessage().toString());
                        response = volleyError.getMessage().toString();
                    }
                });
        this.requestQueueHttps.add(stringRequest);
        return response;
    }

    private static SSLSocketFactory createSslSocketFactory(Context context) {

        InputStream keyInputStream = null;
        String keyStorePassword = "";
        InputStream trustInputStream = null;
        String trustStorePassword = "";
        try {
            keyInputStream = context.getResources().openRawResource(R.raw.zchao_clientbks);
            keyStorePassword = "123456";
            trustInputStream = context.getResources().openRawResource(R.raw.zchao_serverbks);
            trustStorePassword = "123456";
        } catch (Exception e) {
            throw new IllegalStateException("Failure initializing KeyStore", e);
        }
        KeyStore trustStore;
        KeyStore keyStore;

        final String DEFAULT_KEY_TYPE = "bks";
        final String DEFAULT_KEY_MANAGER_TYPE = "X509";

        try {
            trustStore = KeyStore.getInstance(DEFAULT_KEY_TYPE);
            keyStore = KeyStore.getInstance(DEFAULT_KEY_TYPE);

            keyStore.load(keyInputStream, keyStorePassword.toCharArray());
            trustStore.load(trustInputStream, trustStorePassword.toCharArray());
        } catch (Exception e) {
            throw new IllegalStateException("Failure initializing KeyStore", e);
        } finally {
            try {
                keyInputStream.close();
                trustInputStream.close();
            } catch (Exception ignore) {
            }
        }

        KeyManagerFactory keyManagerFactory;
        TrustManagerFactory trustManagerFactory;
        try {
            keyManagerFactory = KeyManagerFactory.getInstance(DEFAULT_KEY_MANAGER_TYPE);
            trustManagerFactory = TrustManagerFactory.getInstance(DEFAULT_KEY_MANAGER_TYPE);

            keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
            trustManagerFactory.init(trustStore);
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e) {
            throw new IllegalStateException("Failure initializing KeyManagerFactory", e);
        }

        TLSSocketFactory tlsSocketFactory;
        try {
            tlsSocketFactory = new TLSSocketFactory(keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(), new SecureRandom());

        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            throw new IllegalStateException("Failure initializing TLSSocketFactory", e);
        }

        return tlsSocketFactory;
    }
}
