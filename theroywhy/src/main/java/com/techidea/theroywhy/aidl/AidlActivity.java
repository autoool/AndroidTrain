package com.techidea.theroywhy.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.techidea.demand.aidl.IPerson;
import com.techidea.theroywhy.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zchao on 2017/2/7.
 */

public class AidlActivity extends AppCompatActivity {

    @Bind(R.id.imagebutton_back)
    ImageButton imageButtonBack;

    private IPerson person;
    boolean isBind = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            person = IPerson.Stub.asInterface(service);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);

        imageButtonBack.setImageResource(R.mipmap.scan_back);
//        imageButtonBack.setBackgroundResource(R.mipmap.scan_back);
    }

    @OnClick(R.id.button_bind)
    void onBind() {
        Intent intent = createExplicitFromImplicitIntent(this, new Intent("android.intent.action.AIDLPersonService"));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.button_unbind)
    void unBind() {
        if (isBind) {
            unbindService(connection);
            isBind = false;
        }
    }

    @OnClick(R.id.button_greet)
    void greet() {
        if (isBind) {
            try {
                String response = person.greet("client");
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "unbind", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }


}
