package com.techidea.demand.service;

import android.util.Log;

import com.techidea.demand.entity.BufferApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class BufferApiRequest {

    private List<BufferApi> bufferApiList;

    private static BufferApiRequest Instance;
    private Thread thread;
    private boolean isRuning;

    public static BufferApiRequest getInstance() {
        if (Instance == null)
            Instance = new BufferApiRequest();
        return Instance;
    }

    private BufferApiRequest() {
        bufferApiList = new ArrayList<>();
    }

    public synchronized void addBufferApi(BufferApi bufferApi) {
        if (bufferApi == null)
            return;
        if (bufferApiList == null)
            bufferApiList = new ArrayList<>();
        bufferApiList.add(bufferApi);
        Log.d("bufferapi  add  ", bufferApi.getUrl() + ":" + bufferApi.getParams());
        startBufferApi();
    }

    private synchronized void removeBufferApi(BufferApi bufferApi) {
        if (bufferApi == null)
            return;
        if (bufferApiList == null)
            bufferApiList = new ArrayList<>();
        for (BufferApi item : bufferApiList) {
            if (item.equals(bufferApi)) {
                bufferApiList.remove(item);
                Log.d("bufferapi  remove  ", item.getUrl() + ":" + item.getParams());
                break;
            }
        }
    }

    public synchronized void startBufferApi() {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    isRuning = true;
                    if (bufferApiList == null)
                        bufferApiList = new ArrayList<>();
                    while (isRuning) {
                        if (bufferApiList.size() != 0) {
                            BufferApi bufferApi = bufferApiList.get(0);
                            System.out.println(bufferApi.getUrl() + ":" + bufferApi.getParams());
                            Log.d("bufferapi operator", bufferApi.getUrl() + ":" + bufferApi.getParams());
                            removeBufferApi(bufferApi);
                            try {
                                Thread.sleep(1000);
                                Log.d("bufferapi sleep ", "1s");
                                System.out.println("bufferapi sleep" + "1s");
                            } catch (InterruptedException e) {

                            }
                        } else {
                            thread.interrupt();
                            thread = null;
                            isRuning = false;
                        }
                    }
                }
            });
            thread.start();
        }
    }

}
