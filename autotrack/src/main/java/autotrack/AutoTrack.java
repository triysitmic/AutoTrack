package autotrack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author zhengyanda
 */
public class AutoTrack {

    private final static String TAG = "AutoTrack-CoreAPI";

    public static void track(Object target) {
        Class<?> cls = target.getClass();
        try {
            Method trackPage = cls.getMethod("trackPage");
            if (trackPage != null) {
                trackPage.invoke(target);
            }

            Method trackView = cls.getMethod("trackView");
            if (trackView != null) {
                trackView.invoke(target);
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
