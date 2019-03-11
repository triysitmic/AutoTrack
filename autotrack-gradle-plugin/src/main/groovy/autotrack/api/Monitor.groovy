package autotrack.api

/**
 * @author zhengyanda
 */
class Monitor {

    private long lastTime

    private boolean timingStart

    private Monitor() {
    }

    static Monitor getInstance() {
        return Holder.instance
    }

    void startTiming() {
        lastTime = System.currentTimeMillis()
        timingStart = true
    }

    void stopTiming() {
        if (!timingStart) {
            return
        }
        def current = System.currentTimeMillis()
        def period = current - lastTime
        //todo Log period
        LogUtils.logDuration(period)
        timingStart = false
    }

    void outputConfigInfo() {
        LogUtils.logConfigInfo()
    }

    void outputInjectClassName(String name) {
        LogUtils.logInjectClassName(name)
    }

    private static class Holder {
        private static Monitor instance = new Monitor()
    }
}
