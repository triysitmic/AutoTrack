package autotrack.plugin

import autotrack.constants.Constant

class AutoTrackExtension {
    boolean trackClick = true
    boolean trackExposure = true
    boolean trackPage = true
    boolean clickIntercept = true
    long clickTimeCycle = Constant.DEFAUT_CLICK_CYCLE

    @Override
    String toString() {
        return "trackClick = " + trackClick +
                "\ntrackExposure = " + trackExposure +
                "\ntrackPage = " + trackExposure +
                "\nclickIntercept = " + clickIntercept +
                (clickIntercept ? ("\nclickTimeCycle = " + clickTimeCycle) : "")
    }
}
