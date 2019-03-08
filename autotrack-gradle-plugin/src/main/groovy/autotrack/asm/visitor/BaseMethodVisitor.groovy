package autotrack.asm.visitor

import autotrack.api.PluginApi
import autotrack.constants.Constant
import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.Label
import com.android.tools.r8.org.objectweb.asm.MethodVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes
import com.android.tools.r8.org.objectweb.asm.commons.AdviceAdapter

class BaseMethodVisitor extends AdviceAdapter {

    private MethodAV mav

    protected BaseMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc == Constant.desc.ANNOTATION_INTERCEPT) {
            mav = new MethodAV(super.visitAnnotation(desc, visible))
            return mav
        }
        return super.visitAnnotation(desc, visible)
    }

    @Override
    protected void onMethodEnter() {

        //inject clickIntercept
        if (PluginApi.getInstance().clickIntercept) {
            if (mav == null) {
                injectClickInterceptCode(mv, PluginApi.getInstance().clickTimeCycle)
            } else {
                injectClickInterceptCode(mv, mav.timeCycle)
            }
        }

        //inject click track
        if (PluginApi.getInstance().trackClick) {
            mv.visitVarInsn(ALOAD, 1)
            mv.visitMethodInsn(INVOKESTATIC, "autotrack/Tracker", "trackClick",
                    "(Ljava/lang/Object;)V", false)
        }
    }

    private static void injectClickInterceptCode(MethodVisitor mv, long timeCycle) {
        mv.visitVarInsn(ALOAD, 1)
        mv.visitLdcInsn(timeCycle)
        mv.visitMethodInsn(INVOKESTATIC, "autotrack/Interceptor",
                "canClick", "(Landroid/view/View;J)Z", false)
        Label label = new Label()
        mv.visitJumpInsn(IFNE, label)
        mv.visitInsn(RETURN)
        mv.visitLabel(label)
    }

    class MethodAV extends AnnotationVisitor {

        long timeCycle

        MethodAV(AnnotationVisitor av) {
            super(Opcodes.ASM6, av)
            timeCycle = PluginApi.getInstance().clickTimeCycle
        }

        @Override
        void visit(String name, Object value) {
            super.visit(name, value)
            timeCycle = value < 0 ? 0 : value
        }
    }
}
