package autotrack.api

import autotrack.plugin.AutoTrackExtension
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.compress.utils.IOUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class PluginApi {

    AutoTrackExtension mExtension

    private static class Holder {
        static PluginApi instance = new PluginApi()
    }

    static PluginApi getInstance() {
        return Holder.instance
    }

    private PluginApi() {
    }

    void injectFile(File file) {
        def name = file.name
        if (name.endsWith(".class") && !name.startsWith("R\$") &&
                "R.class" != name && "BuildConfig.class" != name) {
            Visitor visitor = new Visitor()
            visitor.visit(file.bytes)

            byte[] codes = visitor.dump()
            if (codes != null) {
                FileOutputStream fos = new FileOutputStream(
                        file.parentFile.absolutePath + File.separator + name)
                fos.write(codes)
                fos.close()
            }
        }
    }

    File injectJar(File jarFile, File tempDir) {
        def file = new JarFile(jarFile)
        def md5name = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8)
        def outputJar = new File(tempDir, md5name + jarFile.name)
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outputJar))
        Enumeration enumeration = file.entries()
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = (JarEntry) enumeration.nextElement()
            InputStream inputStream = file.getInputStream(jarEntry)

            String entryName = jarEntry.getName()
            ZipEntry zipEntry = new ZipEntry(entryName)

            jarOutputStream.putNextEntry(zipEntry)
            byte[] modifiedClassBytes = null
            byte[] sourceClassBytes = IOUtils.toByteArray(inputStream)
            if (!entryName.startsWith("android") && !entryName.startsWith("META-INF") &&
                    !entryName.startsWith("java") && !entryName.startsWith("autotrack") &&
                    entryName.endsWith(".class") && !entryName.contains("BuildConfig.class")) {
                Visitor visitor = new Visitor()
                visitor.visit(sourceClassBytes)
                modifiedClassBytes = visitor.dump()
            }
            if (modifiedClassBytes == null) {
                jarOutputStream.write(sourceClassBytes)
            } else {
                jarOutputStream.write(modifiedClassBytes)
            }
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        file.close()
        return outputJar
    }

    void initExtension(AutoTrackExtension extension) {
        mExtension = extension
    }

    boolean isTrackClick() {
        return mExtension.trackClick
    }

    boolean isTrackExposure() {
        return mExtension.trackExposure
    }

    boolean isTrackPage() {
        return mExtension.trackPage
    }

    boolean isClickIntercept() {
        return mExtension.clickIntercept
    }

    long getClickTimeCycle() {
        return mExtension.clickTimeCycle
    }
}
