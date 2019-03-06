package autotrack;

import android.view.View;
import android.view.ViewTreeObserver;

import java.util.List;

/**
 * @author zhengyanda
 */
public class CommonPreDrawListener implements ViewTreeObserver.OnPreDrawListener {

    private View view;
    private List<String> values;

    public CommonPreDrawListener(View view, List<String> values) {
        this.view = view;
        this.values = values;
    }

    @Override
    public boolean onPreDraw() {
        view.getViewTreeObserver().removeOnPreDrawListener(this);
        Reporter.reportExposure(values);
        return false;
    }
}
