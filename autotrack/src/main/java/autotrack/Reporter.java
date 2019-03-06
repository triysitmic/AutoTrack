package autotrack;

import android.util.Log;

import java.util.List;

/**
 * @author zhengyanda
 */
public class Reporter {

    public final static String TAG = "AutoTrack-Reporter";

    public static void report(List<String> values) {
        Log.d(TAG, "手动上报\t value = " + values.toString());
    }

    public static void reportExposure(List<String> values) {
        Log.d(TAG, "上报曝光事件\t上报信息：" + values.toString());
    }

    public static void reportPage(List<String> values) {
        Log.d(TAG, "上报Page事件\t上报信息：" + values.toString());
    }

    public static void reportClick(List<String> values) {
        Log.d(TAG, "上报点击事件\t上报信息：" + values.toString());
    }
}
