package autotrack;

import android.util.Log;

public class Tracker {

    public final static String TAG = "AutoTrack-Tracker";

    public static void setExposureTrack(Object target, String value) {
        if (target == null || !(target instanceof android.view.View)) {
            Log.d(TAG, "target field is not instant of android.view.View");
            return;
        }
        final android.view.ViewTreeObserver observer =
                ((android.view.View) target).getViewTreeObserver();
        autotrack.CommonPreDrawListener listener = new CommonPreDrawListener(observer, value);
        observer.addOnPreDrawListener(listener);
        Log.d(TAG, "setExposureTrack success");
    }

    public static void setClickTrack(Object target, String value) {
        if (target == null || !(target instanceof android.view.View)) {
            Log.d(TAG, "target field is not instant of android.view.View");
            return;
        }
        ((android.view.View) target).setTag(new autotrack.CommonTag(value));
        Log.d(TAG, "setClickTrack success");
    }
}
