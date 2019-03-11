package autotrack.api

/**
 * @author zhengyanda
 */
class LogUtils {

    static void logDuration(period) {
        println("\nAutoTrack/Inject Success : " + period + "ms\n\n")
    }

    static void logConfigInfo() {
        println("\n\nAutoTrack/Configuration : ")
        println(PluginApi.instance.mExtension.toString())
    }

    static void logInjectClassName(String name) {
        println("AutoTrack/Inject Class : " + name)
    }
}
