package com.techidea.theroywhy.net;

import android.provider.SyncStateContract;
import android.util.Log;

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

        client = new OkHttpClient.Builder()
                .sslSocketFactory(customSSLSocketFactory, trustManager)
//                .hostnameVerifier(hostnameVerifier)
//                .connectionSpecs(Collections.singletonList(spec))
                .build();
    }

    HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            HostnameVerifier hv =
                    HttpsURLConnection.getDefaultHostnameVerifier();
//            return hv.verify("https://www.barehuman.com:8443/", session);
            return true;
        }
    };

    public void run() {
//        Request request = new Request.Builder()
//                .url("https://publicobject.com/helloworld.txt")
//                .build();
        String param = "00 5B 60 00 03 00 00 60 31 00 31 13 12 08 00 00 20 00 00 00 c0 00" +
                " 16 00 00 01 31 30 30 30 30 31 35 39 38 38 30 32 31 30 30 31 30 32 31 30 31 " +
                "36 30 00 11 00 00 00 01 00 30 00 29 53 65 71 75 65 6e 63 65 20 4e 6f 31 36 33 " +
                "31 35 30 53 58 58 2d 34 43 33 30 34 31 31 39 00 03 30 31 20";
        MediaType str = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(str, param);
        Request request = new Request.Builder()
                .header("POST", "")
                .header("User-Agent:", "Donjin Http 0.1")
                .header("Content-Type:", "x-ISO-TPDU/x-auth")
                .header("Accept:", "*/*")
                .header("Content-Length:", String.valueOf(param.length()))
                .url("https://202.101.25.188:20141/")
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
            System.out.println(response.body().string());
            Log.v(TAG, response.body().string());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.v(TAG, e.getMessage().toString());
        }
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {

        String CER_SERVER_ZC = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIDTTCCAjWgAwIBAgIEf67gmjANBgkqhkiG9w0BAQsFADBXMQswCQYDVQQGEwJj\n" +
                "bjELMAkGA1UECBMCc2gxCzAJBgNVBAcTAnNoMQ4wDAYDVQQKEwV6aGFuZzEOMAwG\n" +
                "A1UECxMFemhhbmcxDjAMBgNVBAMTBXpoYW5nMB4XDTE2MTEwMjAzMTYyMloXDTI2\n" +
                "MDkxMTAzMTYyMlowVzELMAkGA1UEBhMCY24xCzAJBgNVBAgTAnNoMQswCQYDVQQH\n" +
                "EwJzaDEOMAwGA1UEChMFemhhbmcxDjAMBgNVBAsTBXpoYW5nMQ4wDAYDVQQDEwV6\n" +
                "aGFuZzCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMGZoFVMlGUQLow7\n" +
                "XSX0ntwoy53Tg5VuGCgHU5TOsOiHswLInHXPrPprjdkfEavTSYdoCSBloDOdyDMM\n" +
                "aY093dIsmIx2oA/e608b/xF655jd25QC3zqdTi0cArNnY9d6mStn26XcG4Hul3y+\n" +
                "e+VYa9K60+BBAWKMBwhlFx1bb/sHfInPF+9AK0VUnjbrWbDOmRjSRRYILCiyLVpo\n" +
                "t3YiR0GofBMe7xGXIOknTOfmQPnsT6N9sbUivV2qHmr1xUeR2m3fUDxqUyteJmWP\n" +
                "7UH47LETeoYq97EW7eQOjDR+ARlzd1x4HA01Rw2KrPHAYvpcEskZdR2rCLopmCmy\n" +
                "HXS7E0kCAwEAAaMhMB8wHQYDVR0OBBYEFAifYms7n5CxQNOJmejQeQla4iJeMA0G\n" +
                "CSqGSIb3DQEBCwUAA4IBAQBEayaS0GvqC7NqfA880f/V7Lq5g8h8Gm2LwCYuOVRQ\n" +
                "/M2DMAuRxVJhH7Dy6PZaYHIEEeeP0lrII4Raryym5TRSaNze51EfDWOpg6VBzNnU\n" +
                "IoxpPFPA7PI06P6YCxtElEjplFbGYX2poWyFSp6gGVtlXeXOo9gO39KhXvyGD+HX\n" +
                "jHNnDM3z3umnxAwe5JQQCBcFBiW4xjthVIWWkhJkwnUgsOmmiBG0XaZzirjnEmse\n" +
                "Y7BBQc/ysJUUdtZ6VXMHgjO94DlcFh7K0Pc0ciuZ4v+Qu0rasXzy8czx4P3RWttB\n" +
                "V6JgBFIEf09R0OrP3bidU4cLXDeLV8CVMCgKaCF8zVzj\n" +
                "-----END CERTIFICATE-----\n";

        String CER_CLIENT_ZC = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIDOzCCAiOgAwIBAgIEFFzMJzANBgkqhkiG9w0BAQsFADBOMQswCQYDVQQGEwJj\n" +
                "bjELMAkGA1UECBMCc2gxCzAJBgNVBAcTAnNoMQswCQYDVQQKEwJ6YzELMAkGA1UE\n" +
                "CxMCemMxCzAJBgNVBAMTAnpjMB4XDTE2MTEwMzAzMTAzMVoXDTI2MDkxMjAzMTAz\n" +
                "MVowTjELMAkGA1UEBhMCY24xCzAJBgNVBAgTAnNoMQswCQYDVQQHEwJzaDELMAkG\n" +
                "A1UEChMCemMxCzAJBgNVBAsTAnpjMQswCQYDVQQDEwJ6YzCCASIwDQYJKoZIhvcN\n" +
                "AQEBBQADggEPADCCAQoCggEBAMe6/rlUMq71NFeS77wHoUUysgtTjr3pm/Oeu8n5\n" +
                "LlIv5OVeTeRMIsybukpuHM3OY2iWAPtYH8xhqRZzUMXydX5/j7TZ613XHlD8gxfv\n" +
                "sZmm50kTzSTbCQeZoQ5NsHTQD5H8gx//bC9PMes4eHirwld7Ez6wfXtDsk3hCZck\n" +
                "Csm4ap1+314pre01IjJQareLsGZCKlz7lwAIBVvHZSeo6IDzSvpnl0LKEKSrJsVb\n" +
                "dVj+Qzz5jNILVvLQyPdNpM3ZoHcTLrTdaG8cgI7uxzZ2sMwrOpHlvJLMHhY0E6BA\n" +
                "Iavy0HXAdUGNMOxamQDF8VVFQ1+iBSkV7+OKIj/vG9OLhy0CAwEAAaMhMB8wHQYD\n" +
                "VR0OBBYEFLra7tm6eNSp8yOvfgrhQhlllW0IMA0GCSqGSIb3DQEBCwUAA4IBAQAr\n" +
                "tRTaIesJ/2e+dvc4lTFL5KVcV66e7t1+WIiFtbGjemeU0JHcVu7NC6NN3bf1C3AL\n" +
                "S1qnxEnsyvZSw3YsfFXmAqVX3umcbzzoyXeX2nuGr7LyQrxLE3aY3TI+m9l/idgl\n" +
                "2feHtFfoNTbowhV7eiWWe/GCe2tt8TU8Rx5vtxM99z13ooiZOjs7gqFvGoIdgwMJ\n" +
                "46xj3RQpmhTwOSPX0PmYbx16A3cFmd2Z3rC/xCwECV8P5SAld3LnTVnEsuQXpz/O\n" +
                "6XRd9U0MoNeXE4pZAF9y80s46RUGVaUovRfcHCvmnC68e/kWY3P0argEZ8N3aU+5\n" +
                "XtzhXpy0tcGgKPYWJv8c\n" +
                "-----END CERTIFICATE-----\n";

        // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
        // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
        // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
        // Typically developers will need to get a PEM file from their organization's TLS administrator.
        String comodoRsaCertificationAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n"
                + "MIIF2DCCA8CgAwIBAgIQTKr5yttjb+Af907YWwOGnTANBgkqhkiG9w0BAQwFADCB\n"
                + "hTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n"
                + "A1UEBxMHU2FsZm9yZDEaMBgGA1UEChMRQ09NT0RPIENBIExpbWl0ZWQxKzApBgNV\n"
                + "BAMTIkNPTU9ETyBSU0EgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTAwMTE5\n"
                + "MDAwMDAwWhcNMzgwMTE4MjM1OTU5WjCBhTELMAkGA1UEBhMCR0IxGzAZBgNVBAgT\n"
                + "EkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4GA1UEBxMHU2FsZm9yZDEaMBgGA1UEChMR\n"
                + "Q09NT0RPIENBIExpbWl0ZWQxKzApBgNVBAMTIkNPTU9ETyBSU0EgQ2VydGlmaWNh\n"
                + "dGlvbiBBdXRob3JpdHkwggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQCR\n"
                + "6FSS0gpWsawNJN3Fz0RndJkrN6N9I3AAcbxT38T6KhKPS38QVr2fcHK3YX/JSw8X\n"
                + "pz3jsARh7v8Rl8f0hj4K+j5c+ZPmNHrZFGvnnLOFoIJ6dq9xkNfs/Q36nGz637CC\n"
                + "9BR++b7Epi9Pf5l/tfxnQ3K9DADWietrLNPtj5gcFKt+5eNu/Nio5JIk2kNrYrhV\n"
                + "/erBvGy2i/MOjZrkm2xpmfh4SDBF1a3hDTxFYPwyllEnvGfDyi62a+pGx8cgoLEf\n"
                + "Zd5ICLqkTqnyg0Y3hOvozIFIQ2dOciqbXL1MGyiKXCJ7tKuY2e7gUYPDCUZObT6Z\n"
                + "+pUX2nwzV0E8jVHtC7ZcryxjGt9XyD+86V3Em69FmeKjWiS0uqlWPc9vqv9JWL7w\n"
                + "qP/0uK3pN/u6uPQLOvnoQ0IeidiEyxPx2bvhiWC4jChWrBQdnArncevPDt09qZah\n"
                + "SL0896+1DSJMwBGB7FY79tOi4lu3sgQiUpWAk2nojkxl8ZEDLXB0AuqLZxUpaVIC\n"
                + "u9ffUGpVRr+goyhhf3DQw6KqLCGqR84onAZFdr+CGCe01a60y1Dma/RMhnEw6abf\n"
                + "Fobg2P9A3fvQQoh/ozM6LlweQRGBY84YcWsr7KaKtzFcOmpH4MN5WdYgGq/yapiq\n"
                + "crxXStJLnbsQ/LBMQeXtHT1eKJ2czL+zUdqnR+WEUwIDAQABo0IwQDAdBgNVHQ4E\n"
                + "FgQUu69+Aj36pvE8hI6t7jiY7NkyMtQwDgYDVR0PAQH/BAQDAgEGMA8GA1UdEwEB\n"
                + "/wQFMAMBAf8wDQYJKoZIhvcNAQEMBQADggIBAArx1UaEt65Ru2yyTUEUAJNMnMvl\n"
                + "wFTPoCWOAvn9sKIN9SCYPBMtrFaisNZ+EZLpLrqeLppysb0ZRGxhNaKatBYSaVqM\n"
                + "4dc+pBroLwP0rmEdEBsqpIt6xf4FpuHA1sj+nq6PK7o9mfjYcwlYRm6mnPTXJ9OV\n"
                + "2jeDchzTc+CiR5kDOF3VSXkAKRzH7JsgHAckaVd4sjn8OoSgtZx8jb8uk2Intzna\n"
                + "FxiuvTwJaP+EmzzV1gsD41eeFPfR60/IvYcjt7ZJQ3mFXLrrkguhxuhoqEwWsRqZ\n"
                + "CuhTLJK7oQkYdQxlqHvLI7cawiiFwxv/0Cti76R7CZGYZ4wUAc1oBmpjIXUDgIiK\n"
                + "boHGhfKppC3n9KUkEEeDys30jXlYsQab5xoq2Z0B15R97QNKyvDb6KkBPvVWmcke\n"
                + "jkk9u+UJueBPSZI9FoJAzMxZxuY67RIuaTxslbH9qh17f4a+Hg4yRvv7E491f0yL\n"
                + "S0Zj/gA0QHDBw7mh3aZw4gSzQbzpgJHqZJx64SIDqZxubw5lT2yHh17zbqD5daWb\n"
                + "QOhTsiedSrnAdyGN/4fy3ryM7xfft0kL0fJuMAsaDk527RH89elWsn2/x20Kk4yl\n"
                + "0MC2Hb46TpSi125sC8KKfPog88Tk5c0NqMuRkrF8hey1FGlmDoLnzc7ILaZRfyHB\n"
                + "NVOFBkpdn627G190\n"
                + "-----END CERTIFICATE-----\n";
        String entrustRootCertificateAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n"
                + "MIIEkTCCA3mgAwIBAgIERWtQVDANBgkqhkiG9w0BAQUFADCBsDELMAkGA1UEBhMC\n"
                + "VVMxFjAUBgNVBAoTDUVudHJ1c3QsIEluYy4xOTA3BgNVBAsTMHd3dy5lbnRydXN0\n"
                + "Lm5ldC9DUFMgaXMgaW5jb3Jwb3JhdGVkIGJ5IHJlZmVyZW5jZTEfMB0GA1UECxMW\n"
                + "KGMpIDIwMDYgRW50cnVzdCwgSW5jLjEtMCsGA1UEAxMkRW50cnVzdCBSb290IENl\n"
                + "cnRpZmljYXRpb24gQXV0aG9yaXR5MB4XDTA2MTEyNzIwMjM0MloXDTI2MTEyNzIw\n"
                + "NTM0MlowgbAxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1FbnRydXN0LCBJbmMuMTkw\n"
                + "NwYDVQQLEzB3d3cuZW50cnVzdC5uZXQvQ1BTIGlzIGluY29ycG9yYXRlZCBieSBy\n"
                + "ZWZlcmVuY2UxHzAdBgNVBAsTFihjKSAyMDA2IEVudHJ1c3QsIEluYy4xLTArBgNV\n"
                + "BAMTJEVudHJ1c3QgUm9vdCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTCCASIwDQYJ\n"
                + "KoZIhvcNAQEBBQADggEPADCCAQoCggEBALaVtkNC+sZtKm9I35RMOVcF7sN5EUFo\n"
                + "Nu3s/poBj6E4KPz3EEZmLk0eGrEaTsbRwJWIsMn/MYszA9u3g3s+IIRe7bJWKKf4\n"
                + "4LlAcTfFy0cOlypowCKVYhXbR9n10Cv/gkvJrT7eTNuQgFA/CYqEAOwwCj0Yzfv9\n"
                + "KlmaI5UXLEWeH25DeW0MXJj+SKfFI0dcXv1u5x609mhF0YaDW6KKjbHjKYD+JXGI\n"
                + "rb68j6xSlkuqUY3kEzEZ6E5Nn9uss2rVvDlUccp6en+Q3X0dgNmBu1kmwhH+5pPi\n"
                + "94DkZfs0Nw4pgHBNrziGLp5/V6+eF67rHMsoIV+2HNjnogQi+dPa2MsCAwEAAaOB\n"
                + "sDCBrTAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0TAQH/BAUwAwEB/zArBgNVHRAEJDAi\n"
                + "gA8yMDA2MTEyNzIwMjM0MlqBDzIwMjYxMTI3MjA1MzQyWjAfBgNVHSMEGDAWgBRo\n"
                + "kORnpKZTgMeGZqTx90tD+4S9bTAdBgNVHQ4EFgQUaJDkZ6SmU4DHhmak8fdLQ/uE\n"
                + "vW0wHQYJKoZIhvZ9B0EABBAwDhsIVjcuMTo0LjADAgSQMA0GCSqGSIb3DQEBBQUA\n"
                + "A4IBAQCT1DCw1wMgKtD5Y+iRDAUgqV8ZyntyTtSx29CW+1RaGSwMCPeyvIWonX9t\n"
                + "O1KzKtvn1ISMY/YPyyYBkVBs9F8U4pN0wBOeMDpQ47RgxRzwIkSNcUesyBrJ6Zua\n"
                + "AGAT/3B+XxFNSRuzFVJ7yVTav52Vr2ua2J7p8eRDjeIRRDq/r72DQnNSi6q7pynP\n"
                + "9WQcCk3RvKqsnyrQ/39/2n3qse0wJcGE2jTSW3iDVuycNsMm4hH2Z0kdkquM++v/\n"
                + "eu6FSqdQgPCnXEqULl8FmTxSQeDNtGPPAUO6nIPcj2A781q0tHuu2guQOHXvgR1m\n"
                + "0vdXcDazv/wor3ElhVsT/h5/WrQ8\n"
                + "-----END CERTIFICATE-----\n";

        String tongguan = "-----BEGIN CERTIFICATE-----\n" +
                "MIIDxzCCAq+gAwIBAgIJANQeF2dWg9WEMA0GCSqGSIb3DQEBBQUAMHoxCzAJBgNV\n" +
                "BAYTAklOMQswCQYDVQQIDAJLQTEMMAoGA1UEBwwDQkFOMQ8wDQYDVQQKDAZORVdO\n" +
                "RVQxDDAKBgNVBAsMA05TVDEPMA0GA1UEAwwGdGVzdGFnMSAwHgYJKoZIhvcNAQkB\n" +
                "FhF0ZXN0YWdAbmV3bmV0LmNvbTAeFw0xNTA5MjIwNjU1MTNaFw0xODA5MjEwNjU1\n" +
                "MTNaMHoxCzAJBgNVBAYTAklOMQswCQYDVQQIDAJLQTEMMAoGA1UEBwwDQkFOMQ8w\n" +
                "DQYDVQQKDAZORVdORVQxDDAKBgNVBAsMA05TVDEPMA0GA1UEAwwGdGVzdGFnMSAw\n" +
                "HgYJKoZIhvcNAQkBFhF0ZXN0YWdAbmV3bmV0LmNvbTCCASIwDQYJKoZIhvcNAQEB\n" +
                "BQADggEPADCCAQoCggEBAMMdsjZZI//rruZ4KS2/EYxBel4USSQABLoxlkr0r92s\n" +
                "ZqQFeR+poZwedYyv/prQ5TKqYpjfSsiRLgmBc/kbzLK1dYR0KoKvXcnlnNckqkFZ\n" +
                "OUdONM2R+g4YA5yiPnM3jAh6056a+yK+I2k2JIrI/CORT60QduzqXAWZ6lG6d2GQ\n" +
                "BkNRzjOmKpIEQObnRWZAy+9pPmHeqcrsd0RC6v8zPl5XFrA2UeM9gw0eNXUY+gwb\n" +
                "sRLfuYLDejwbmKlb6PtCxrIckIHU5WgFdrqb2WNdU1cwIYaMGLhgNeZgJicwznuh\n" +
                "8wxMXaOPUB71dCSg3syzIMbHgW7PSZ8+ri15pObVotMCAwEAAaNQME4wHQYDVR0O\n" +
                "BBYEFCKN3/HDTmkrQFhqt/3QcBoJ/2W8MB8GA1UdIwQYMBaAFCKN3/HDTmkrQFhq\n" +
                "t/3QcBoJ/2W8MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADggEBABYt2GJ5\n" +
                "P0HtCfKj4E3dtk/L9OzzDHmp7TDt1265JPpJ7DfF8we9HG+ZNFETJvlar07pP0vy\n" +
                "t4/w6Q1KfA8p52Y9BSRvZltoW+SfUNRD96cHm2O77HKeiYcWOHB9CqxUqPsTv8xJ\n" +
                "GNd/i9Xw2lcbXEQeaCkOLfdCo2ZVcTReNW9gazTAg4ED+sbQWySMViJwEyhGDpPT\n" +
                "Zuu/Sip6Sux2th+s/PSMQFkFmK6Pt/NcL4HUzi51y49r1sxb8ME2kZ4JQq+qMKzn\n" +
                "X7kkLE7ECdK2bc8R7rMQ7vttxHeFuyW+P687bD8bGPa/lppZrQ5c/lkVEKm8l7Nc\n" +
                "witOy/5bPeoxTPE=\n" +
                "-----END CERTIFICATE-----\n";
//        InputStream inputStream =
//                new Buffer()
//                        .writeUtf8(CER_SERVER_ZC)
//                        .writeUtf8(CER_CLIENT_ZC)
//                        .inputStream();
        InputStream inputStream =
                new Buffer()
                        .writeUtf8(tongguan)
                        .inputStream();

//        InputStream inputStream =
//                new Buffer()
//                        .writeUtf8(comodoRsaCertificationAuthority)
//                        .writeUtf8(entrustRootCertificateAuthority)
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
        char[] password = "123456".toCharArray(); // Any password will work.
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
        customSSLSocketFactory = new CustomSSLSocketFactory(keyManagerFactory.getKeyManagers(),
                trustManagerFactory.getTrustManagers(), new SecureRandom());
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

    public static void main(String... args) throws Exception {
        new CustomTrust().run();
    }
}
