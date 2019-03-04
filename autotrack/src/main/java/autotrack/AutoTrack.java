package autotrack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AutoTrack {

    private final static String TAG = "AutoTrack-CoreAPI";

    public static void track(Object target) {
        Class<?> cls = target.getClass();
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
