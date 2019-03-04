package autotrack.plugin

import autotrack.extension.AutoTrackExtension
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoTrackPlugin implements Plugin<Project> {

    final static String TAG = "AutoTrackPlugin"
    final static String PLUGIN_NAME = "autotrack"

    void apply(Project project) {
        registerTransform(project)
        applyExtension(project)
    }

    static void registerTransform(Project project) {
        def android = project.extensions.getByType(AppExtension)
        AutoTrackTransform transform = new AutoTrackTransform()
        android.registerTransform(transform)
    }

    static void applyExtension(Project project) {
        project.extensions.add("AutoTrackExtension", AutoTrackExtension)
    }
}