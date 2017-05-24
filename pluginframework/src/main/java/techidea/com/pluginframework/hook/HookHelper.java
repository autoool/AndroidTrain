package techidea.com.pluginframework.hook;

import android.app.Activity;
import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import techidea.com.pluginframework.MainActivity;

/**
 * Created by zchao on 2017/3/29.
 */

public class HookHelper {

    public static void attachContext() throws Exception {
        //先获取到当前activityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);

        //currentActivityThread 是一个static 函数，所以可以直接invoke,不需要带实例参数
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        //拿到原始的mInstrumentation字段
        Field instrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        instrumentationField.setAccessible(true);
        Instrumentation instrumentation = (Instrumentation) instrumentationField.get(currentActivityThread);

        //创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(instrumentation);

        //偷梁换柱
        instrumentationField.set(currentActivityThread, evilInstrumentation);
    }

    public static void attachActivityContext() throws Exception{

        Class activityClass = Class.forName("android.app.Activity");
        Field instrumentationField = activityClass.getDeclaredField("mInstrumentation");
        instrumentationField.setAccessible(true);
        Instrumentation instrumentation = (Instrumentation)(instrumentationField.get(activityClass));
        //创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(instrumentation);

        //偷梁换柱
        instrumentationField.set(activityClass, evilInstrumentation);
    }
}
