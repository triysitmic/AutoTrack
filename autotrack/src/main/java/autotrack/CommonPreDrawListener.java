package autotrack;

import android.view.View;
import android.view.ViewTreeObserver;

public class CommonPreDrawListener implements ViewTreeObserver.OnPreDrawListener {

    private View view;
    private String value;

    public CommonPreDrawListener(View view, String value) {
        this.view = view;
        this.value = value;
    }

    @Override
    public boolean onPreDraw() {
        view.getViewTreeObserver().removeOnPreDrawListener(this);
        Reporter.reportExposure(value);
        return false;
    }
}
