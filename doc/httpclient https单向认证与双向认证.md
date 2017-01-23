# httpclient

## 单向认证

### 使用bks
```
 private HttpClient getHttpsSingleBksClient() {
        if (null == httpClient) {
            try {
                InputStream trustStream = context.getResources().openRawResource(R.raw.zc_serverbks);
                KeyStore trustStore = KeyStore.getInstance("bks");
                trustStore.load(trustStream, "123456".toCharArray());
                SSLSocketFactory sslSocketFactory = new SSLTrustSocketFactoryEx(trustStore);
                HttpParams params = new BasicHttpParams();

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
```
### 使用pem


## 双向认证

### 使用bks
```
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
```

### 使用pem