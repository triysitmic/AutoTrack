package autotrack;

import android.view.ViewTreeObserver;

public class CommonPreDrawListener implements ViewTreeObserver.OnPreDrawListener {

    private ViewTreeObserver observer;
    private String value;

    public CommonPreDrawListener(ViewTreeObserver observer, String value) {
        this.observer = observer;
        this.value = value;
    }

    @Override
    public boolean onPreDraw() {
        this.observer.removeOnPreDrawListener(this);
        Reporter.reportExposure(value);
        return false;
    }
}
