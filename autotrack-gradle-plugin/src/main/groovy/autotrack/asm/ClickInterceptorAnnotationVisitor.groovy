package autotrack.asm

import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class ClickInterceptorAnnotationVisitor extends AnnotationVisitor {

    static final long NULL = -1
    long time

    ClickInterceptorAnnotationVisitor(AnnotationVisitor av) {
        super(Opcodes.ASM6, av)
        time = NULL
    }

    @Override
    void visit(String name, Object value) {
        if ("interceptorTime" == name) {
            long v = (long) value
            time = v <= 0 ? 0 : v
        }
        super.visit(name, value)
    }
}