package autotrack;

import android.util.Log;
import android.view.View;

import java.util.List;

import autotrack.impl.Tagged;

/**
 * @author zhengyanda
 */
public class Tracker {

    private final static String TAG = "AutoTrack-Tracker";

    public static void setExposureTrack(Object target, List<String> values) {
        if (!(target instanceof View)) {
            Log.e(TAG, "target field is not instant of android.view.View");
            return;
        }
        View view = ((android.view.View) target);
        autotrack.CommonPreDrawListener listener = new CommonPreDrawListener(view, values);
        view.getViewTreeObserver().addOnPreDrawListener(listener);
    }

    public static void setClickTrack(Object target, List<String> values) {
        if (!(target instanceof View)) {
            Log.e(TAG, "target field is not instant of android.view.View");
            return;
        }
        ((android.view.View) target).setTag(new autotrack.CommonTag(values));
    }

    public static void trackClick(Object target) {
        if (!(target instanceof android.view.View)) {
            return;
        }
        View view = (android.view.View) target;
        Object tag = view.getTag();
        if (!(tag instanceof autotrack.impl.Tagged)) {
            return;
        }
        Reporter.reportClick(((Tagged) tag).getTags());
    }

    public static void setPageTrack(List<String> values) {
        Reporter.reportPage(values);
    }

}
