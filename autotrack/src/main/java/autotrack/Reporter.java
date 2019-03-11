package autotrack;

import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import autotrack.impl.IReport;

/**
 * @author zhengyanda
 */
public class Reporter {

    public final static String TAG = "AutoTrack-Reporter";

    private IReport mReporter;

    private ExecutorService mExecutorService = Executors.newSingleThreadExecutor();

    public static Reporter getInstance() {
        return Holder.INSTANCE;
    }

    public void report(Object values) {
        Log.d(TAG, "手动上报\t value = " + values.toString());
        if (mReporter != null && mExecutorService != null) {
            mExecutorService.execute(() -> mReporter.report(values));
        }
    }

    public void reportExposure(List<String> values) {
        Log.d(TAG, "上报曝光事件\t上报信息：" + values.toString());
        if (mReporter != null && mExecutorService != null) {
            mExecutorService.execute(() -> mReporter.report(values));
        }
    }

    public void reportPage(List<String> values) {
        Log.d(TAG, "上报Page事件\t上报信息：" + values.toString());
        if (mReporter != null && mExecutorService != null) {
            mExecutorService.execute(() -> mReporter.report(values));
        }
    }

    public void reportClick(List<String> values) {
        Log.d(TAG, "上报点击事件\t上报信息：" + values.toString());
        if (mReporter != null && mExecutorService != null) {
            mExecutorService.execute(() -> mReporter.report(values));
        }
    }

    public void registerReporter(IReport reporter) {
        mReporter = reporter;
    }

    public void unregisterReporter() {
        mReporter = null;
    }

    private static class Holder {
        private static Reporter INSTANCE = new Reporter();
    }
}
