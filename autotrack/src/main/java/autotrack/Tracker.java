package autotrack;

import android.util.Log;
import android.view.View;

public class Tracker {

    private final static String TAG = "AutoTrack-Tracker";

    public static void setExposureTrack(Object target, String value) {
        if (!(target instanceof View)) {
            Log.e(TAG, "target field is not instant of android.view.View");
            return;
        }
        View view = ((android.view.View) target);
        autotrack.CommonPreDrawListener listener = new CommonPreDrawListener(view, value);
        view.getViewTreeObserver().addOnPreDrawListener(listener);
    }

    public static void setClickTrack(Object target, String value) {
        if (!(target instanceof View)) {
            Log.e(TAG, "target field is not instant of android.view.View");
            return;
        }
        ((android.view.View) target).setTag(new autotrack.CommonTag(value));
    }

    public static void setPageTrack(String value) {
        Reporter.reportPage(value);
    }

    public static void trackClick(Object target) {
        if (!(target instanceof android.view.View)) {
            return;
        }
        View view = (android.view.View) target;
        Object tag = view.getTag();
        if (!(tag instanceof autotrack.CommonTag)) {
            return;
        }
        Reporter.reportClick(((CommonTag) tag).getValue());
    }
}
