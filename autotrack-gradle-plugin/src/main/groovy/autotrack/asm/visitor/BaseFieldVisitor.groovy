package autotrack.asm.visitor

import autotrack.constants.Constant
import autotrack.recorder.FieldRecorder
import autotrack.recorder.SourceRecorder
import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor
import com.android.tools.r8.org.objectweb.asm.FieldVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class BaseFieldVisitor extends FieldVisitor {

    private SourceRecorder sr
    private FieldRecorder fr

    BaseFieldVisitor(FieldVisitor fv, FieldRecorder fr, SourceRecorder sr) {
        super(Opcodes.ASM6, fv)
        this.fr = fr
        this.sr = sr
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Constant.fieldDescs.contains(desc)) {
            AnnotationVisitor av = fv.visitAnnotation(desc, visible)
            fr.addTypeByDesc(desc)
            return new FieldAnnotationVisitor(av, fr)
        }
        return super.visitAnnotation(desc, visible)
    }

    @Override
    void visitEnd() {
        if (fr.shouldGenerateCode()) {
            sr.addField(fr)
        }
        super.visitEnd()
    }
}
