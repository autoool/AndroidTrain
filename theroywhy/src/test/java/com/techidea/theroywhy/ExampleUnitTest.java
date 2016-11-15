package com.techidea.theroywhy;

import com.google.gson.Gson;
import com.techidea.theroywhy.net.Response;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testResponse() throws Exception {
        Gson gson = new Gson();
        String response = "{\"ReqTransDate\":\"140421\",\"TransIndexCode\":\"14526855\"," +
                "\"ReqTransTime\":\"100445\",\"AppVersion\":\"FG[201611081650]\"," +
                "\"AppID\":\"12498423\",\"MID\":\"102440183986056\",\"TrxID\":\"78978926123456\"," +
                "\"BatchNum\":471,\"RespCode\":\"15\",\"TransAmount\":\"00000000001\"," +
                "\"TID\":\"05031532\",\"RespDesc\":\"此卡无对应发卡方\"," +
                "\"TransType\":1,\"AppName\":\"test账户\",\"CertNum\":\"000039\"}";

        String responseR = "{\"ReqTransDate\":\"140421\",\"HasReversal\":\"1\",TransIndexCode\":\"14526855\"," +
                "\"ReqTransTime\":\"100445\",\"AppVersion\":\"FG[201611081650]\",\"AppID\":\"12498423\"," +
                "\"MID\":\"102440183986056\",\"TrxID\":\"78978926123456\",\"BatchNum\":471,\"RespCode\":\"15\"," +
                "\"TransAmount\":\"00000000001\",\"TID\":\"05031532\"," +
                "\"RespDesc\":\"此卡无对应发卡方\",\"TransType\":1,\"AppName\":\"test账户\",\"CertNum\":\"000039\"}";
        Response response1 = new Response();
        response1 = gson.fromJson(responseR, Response.class);

        System.out.println(response1.getHasReversal());

    }
}