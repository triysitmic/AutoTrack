package autotrack.asm.visitor

import autotrack.recorder.SourceRecorder
import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class BaseClassAnnotationVisitor extends AnnotationVisitor {

    SourceRecorder sr

    BaseClassAnnotationVisitor(AnnotationVisitor av, SourceRecorder sr) {
        super(Opcodes.ASM6, av)
        this.sr = sr
    }

    @Override
    void visit(String name, Object value) {
        super.visit(name, value)
        sr.setValue(value)
    }
}
