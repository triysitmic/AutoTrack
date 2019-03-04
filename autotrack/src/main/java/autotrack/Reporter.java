package autotrack;

import android.util.Log;

public class Reporter {

    public final static String TAG = "AutoTrack-Reporter";

    public static void report(String value) {
        Log.d(TAG, "手动上报\t value = " + value);
    }

    public static void reportExposure(String value) {
        Log.d(TAG, "上报曝光事件\t上报信息：" + value);
    }

    public static void reportPage(String value) {
        Log.d(TAG, "上报Page事件\t上报信息：" + value);
    }

    public static void reportClick(String value) {
        Log.d(TAG, "上报点击事件\t上报信息：" + value);
    }
}
