package com.techidea.demand.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.techidea.demand.aidl.IPerson;

/**
 * Created by zchao on 2017/2/7.
 */

public class AIDLPersonService extends Service {

    private static final String TAG = "AIDLPersonService";

    IPerson.Stub stub = new IPerson.Stub() {
        @Override
        public String greet(String content) throws RemoteException {
            return "hello," + content;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
