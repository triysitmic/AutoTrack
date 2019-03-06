package autotrack.asm.visitor

import autotrack.recorder.FieldRecorder
import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class FieldAnnotationVisitor extends AnnotationVisitor {

    private FieldRecorder fr

    FieldAnnotationVisitor(AnnotationVisitor av, FieldRecorder fr) {
        super(Opcodes.ASM6, av)
        this.fr = fr
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
            fr.addValue(value)
        }
    }
}
