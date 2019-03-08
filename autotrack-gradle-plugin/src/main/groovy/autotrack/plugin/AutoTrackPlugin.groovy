package autotrack.plugin

import autotrack.api.PluginApi
import autotrack.constants.Constant
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class AutoTrackPlugin implements Plugin<Project> {

    void apply(Project project) {
        def android = project.extensions.getByType(AppExtension)

        AutoTrackExtension extension = project.extensions.create(Constant.EXTENSION_NAME,
                AutoTrackExtension)
        PluginApi.getInstance().initExtension(extension)
        AutoTrackTransform transform = new AutoTrackTransform()

        android.registerTransform(transform)
    }
}