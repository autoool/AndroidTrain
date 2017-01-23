# httpclient

## 单向认证

### 使用bks
```
OkHttpsManager.getInstance().setContext(getApplicationContext()).setONECertificates(getResources().openRawResource(R.raw.zc_server));
```

```
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
```
### 使用pem
```
OkHttpsManager.getInstance().setContext(getApplicationContext()).
setONECertificates(new Buffer().writeUtf8(Contast.CER_SERVER_ZC).inputStream());
```

```
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
```

## 双向认证

### 使用bks
```
   InputStream clientStream = getResources().openRawResource(R.raw.zc_clientbks);
            String clientPwd = "123456";
            InputStream serverStream = getResources().openRawResource(R.raw.zc_serverbks);
            String serverPwd = "123456";

            OkHttpsManager.getInstance().setContext(getApplicationContext())
                    .setBothCertificates(clientStream, clientPwd, serverStream, serverPwd);
```

```
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
```

### 使用pem