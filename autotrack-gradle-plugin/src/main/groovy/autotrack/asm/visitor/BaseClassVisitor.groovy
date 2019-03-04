package autotrack.asm.visitor

import autotrack.api.Visitor
import autotrack.recorder.FieldRecorder
import com.android.tools.r8.org.objectweb.asm.*

class BaseClassVisitor extends ClassVisitor {

    private Visitor visitor

    BaseClassVisitor(ClassVisitor cv, Visitor visitor) {
        super(Opcodes.ASM6, cv)
        this.visitor = visitor
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        visitor.sr.setClassName(name)
    }

    @Override
    FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldVisitor fv = cv.visitField(access, name, desc, signature, value)
        FieldRecorder fr = new FieldRecorder(name, desc)
        fv = new BaseFieldVisitor(fv, fr, visitor.sr)
        return fv
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible)
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return super.visitMethod(access, name, desc, signature, exceptions)
    }

    @Override
    void visitEnd() {
        visitor.append(this)
        super.visitEnd()
    }
}
