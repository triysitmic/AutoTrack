package autotrack;

import android.util.Log;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author zhengyanda
 */
public class Interceptor {
    private static final String TAG = "AutoTrack-Interceptor";
    private static Map<View, Long> map;

    private Interceptor() {
    }

    public static boolean canClick(View view, long interceptTime) {
        String viewMsg = view.getClass().getName();

        if (interceptTime <= 0) {
            log(false, interceptTime, 0, viewMsg);
            return true;
        }

        if (map == null) {
            map = new WeakHashMap<>();
        }

        Long lastClickTime = map.get(view);
        long current = currentTime();

        if (lastClickTime == null) {
            map.put(view, current);
            log(viewMsg);
            return true;
        } else if (current >= lastClickTime + interceptTime) {
            map.put(view, current);
            log(false, interceptTime, (current - lastClickTime), viewMsg);
            return true;
        } else {
            log(true, interceptTime, (current - lastClickTime), viewMsg);
            return false;
        }
    }

    private static void log(String viewMsg) {
        String msg = "\n首次点击,不拦截" +
                "\n被点击View信息 : " + viewMsg;
        Log.d(TAG, msg);
    }

    private static void log(boolean intercept, long presetTime, long period, String viewMsg) {
        String msg = (intercept ? "\n拦截点击事件" : "\n不拦截点击事件") +
                "\n被点击View信息 : " + viewMsg +
                "\n距上次点击间隔 : " + period + "ms" +
                "\n设定点击周期 : " + presetTime + "ms";
        Log.d(TAG, msg);
    }

    private static long currentTime() {
        return System.currentTimeMillis();
    }
}