package autotrack;

import android.util.Log;
import android.view.View;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author zhengyanda
 */
public class ClickInterceptor {
    private static final String TAG = "ClickInterceptor";
    private static Map<View, Long> map;

    private ClickInterceptor() {
    }

    public static boolean canClick(View view, long interceptTime) {
        if (interceptTime <= 0) {
            Log.d(TAG, "不拦截事件\t" + "设定拦截时间间隔:" + interceptTime + "ms");
            return true;
        }

        if (map == null) {
            map = new WeakHashMap<>();
        }

        Long lastClickTime = map.get(view);
        long current = currentTime();

        if (lastClickTime == null) {
            map.put(view, current);
            Log.d(TAG, "首次点击,不拦截");
            return true;
        } else if (current >= lastClickTime + interceptTime) {
            map.put(view, current);
            Log.d(TAG, "不拦截事件\t" + "设定拦截时间间隔:" + interceptTime + "ms" +
                    "\t距上次点击间隔:" + (current - lastClickTime) + "ms");
            return true;
        } else {
            Log.d(TAG, "拦截事件\t" + "设定拦截时间间隔:" + interceptTime + "ms" +
                    "\t距上次点击间隔:" + (current - lastClickTime) + "ms");
            return false;
        }
    }

    private static long currentTime() {
        return System.currentTimeMillis();
    }
}