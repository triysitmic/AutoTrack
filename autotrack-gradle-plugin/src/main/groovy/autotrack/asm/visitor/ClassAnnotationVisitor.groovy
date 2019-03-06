package autotrack.asm.visitor

import autotrack.recorder.SourceRecorder
import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class ClassAnnotationVisitor extends AnnotationVisitor {

    SourceRecorder sr

    ClassAnnotationVisitor(AnnotationVisitor av, SourceRecorder sr) {
        super(Opcodes.ASM6, av)
        this.sr = sr
    }

    @Override
    AnnotationVisitor visitArray(String name) {
        AnnotationVisitor visitor = av.visitArray(name)
        return new AV(visitor)
    }

    class AV extends AnnotationVisitor {

        AV(AnnotationVisitor av) {
            super(Opcodes.ASM6, av)
        }

        @Override
        void visit(String name, Object value) {
            super.visit(name, value)
            sr.addValue(value)
        }
    }
}
