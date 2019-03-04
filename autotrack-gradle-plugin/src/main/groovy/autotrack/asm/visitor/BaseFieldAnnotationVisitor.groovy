package autotrack.asm.visitor

import autotrack.recorder.FieldRecorder
import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class BaseFieldAnnotationVisitor extends AnnotationVisitor {

    private FieldRecorder fr

    BaseFieldAnnotationVisitor(AnnotationVisitor av, FieldRecorder fr) {
        super(Opcodes.ASM6, av)
        this.fr = fr
    }

    @Override
    void visit(String name, Object value) {
        fr.setValue(value)
        super.visit(name, value)
    }
}
