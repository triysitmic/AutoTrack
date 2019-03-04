package autotrack;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AutoTrack {

    private final static String TAG = "AutoTrack-CoreAPI";

    public static void track(Object target) {
        Class<?> cls = target.getClass();
        Log.d(TAG, cls.toString());
        try {
            Method method = cls.getMethod("track", cls);
            if (method != null) {
                method.invoke(target, target);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
