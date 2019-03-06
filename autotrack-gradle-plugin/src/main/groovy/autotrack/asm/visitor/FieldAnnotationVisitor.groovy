package autotrack.asm.visitor

import autotrack.recorder.FieldRecorder
import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class FieldAnnotationVisitor extends AnnotationVisitor {

    private FieldRecorder fr
    private String desc
    private List<String> values = new ArrayList<>()

    FieldAnnotationVisitor(AnnotationVisitor av, FieldRecorder fr, String desc) {
        super(Opcodes.ASM6, av)
        this.fr = fr
        this.desc = desc
    }

    @Override
    AnnotationVisitor visitArray(String name) {
        AnnotationVisitor visitor = av.visitArray(name)
        return new AV(visitor)
    }

    @Override
    void visitEnd() {
        super.visitEnd()
        fr.addValues(desc, values)
    }

    class AV extends AnnotationVisitor {

        AV(AnnotationVisitor av) {
            super(Opcodes.ASM6, av)
        }

        @Override
        void visit(String name, Object value) {
            super.visit(name, value)
            values.add(value)
        }
    }
}
