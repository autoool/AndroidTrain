package com.techidea.demand.common;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

/**
 * Created by zchao on 2017/3/23.
 */

public abstract interface IServiceRequest {

    public abstract String sendPost(String paramString, Object paramObject)
            throws UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, IOException;

}
