package autotrack.asm.visitor

import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.MethodVisitor
import com.android.tools.r8.org.objectweb.asm.commons.AdviceAdapter

class BaseMethodVisitor extends AdviceAdapter {

    protected BaseMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible)
    }

    @Override
    protected void onMethodEnter() {
        mv.visitVarInsn(ALOAD, 1)
        mv.visitMethodInsn(INVOKESTATIC, "autotrack/Tracker", "trackClick",
                "(Ljava/lang/Object;)V", false)
    }
}
