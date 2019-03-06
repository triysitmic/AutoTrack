package autotrack.asm.visitor

import autotrack.api.Visitor
import autotrack.constants.Constant
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
        visitor.sr.setName(name)
    }


    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (Constant.classDescs.contains(desc)) {
            AnnotationVisitor av = cv.visitAnnotation(desc, visible)
            visitor.sr.addTypeByDesc(desc)
            return new ClassAnnotationVisitor(av, visitor.sr)
        }
        return super.visitAnnotation(desc, visible)
    }

    @Override
    FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        FieldVisitor fv = cv.visitField(access, name, desc, signature, value)
        FieldRecorder fr = new FieldRecorder(name, desc)
        fv = new BaseFieldVisitor(fv, fr, visitor.sr)
        return fv
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (((access & Opcodes.ACC_PUBLIC) != 0) && ((access & Opcodes.ACC_STATIC) == 0) &&
                name == "onClick" && desc == "(Landroid/view/View;)V") {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions)
            mv = new BaseMethodVisitor(Opcodes.ASM6, mv, access, name, desc)
            return mv
        }
        return super.visitMethod(access, name, desc, signature, exceptions)
    }

    @Override
    void visitEnd() {
        visitor.append(this)
        super.visitEnd()
    }
}
