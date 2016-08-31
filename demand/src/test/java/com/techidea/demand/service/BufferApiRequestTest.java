package com.techidea.demand.service;

import com.techidea.demand.entity.BufferApi;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by Administrator on 2016/8/31.
 */
public class BufferApiRequestTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testAddBufferApi() {
        String random = UUID.randomUUID().toString();
        BufferApi bufferApi = new BufferApi();
        bufferApi.setUrl("cn.bing.com" + random);
        bufferApi.setParams(random);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
        BufferApiRequest.getInstance().addBufferApi(bufferApi);
    }
}
