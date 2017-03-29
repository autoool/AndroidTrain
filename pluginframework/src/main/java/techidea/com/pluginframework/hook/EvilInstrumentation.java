package techidea.com.pluginframework.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by zchao on 2017/3/29.
 */

public class EvilInstrumentation extends Instrumentation {

    private static final String TAG = "EvilInstrumentation";

    Instrumentation base;

    public EvilInstrumentation(Instrumentation base) {
        this.base = base;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Log.d(TAG, "参数如下：who=[ " + who + " ] " +
                "\ncontextThread=[ " + contextThread + " ]" +
                "\n token=[ " + token + " ] " +
                "\n target=[ " + target + " ] " +
                "\n requestCode=[ " + token + " ] " +
                "\n intent=[ " + intent + " ] " +
                "\n options = [ " + options + " ]");

        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            execStartActivity.setAccessible(true);
            return (ActivityResult) execStartActivity.invoke(base, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("do not support!!! pls adapt it");
        }
    }
}
