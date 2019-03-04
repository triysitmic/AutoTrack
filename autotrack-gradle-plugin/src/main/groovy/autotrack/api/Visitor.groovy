package autotrack.api

import autotrack.asm.visitor.BaseClassVisitor
import autotrack.recorder.FieldRecorder
import autotrack.recorder.SourceRecorder
import com.android.tools.r8.org.objectweb.asm.ClassReader
import com.android.tools.r8.org.objectweb.asm.ClassVisitor
import com.android.tools.r8.org.objectweb.asm.ClassWriter
import com.android.tools.r8.org.objectweb.asm.MethodVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

class Visitor {

    SourceRecorder sr

    ClassReader cr
    ClassWriter cw

    Visitor() {
        sr = new SourceRecorder()
    }

    void visit(byte[] bytes) {
        cr = new ClassReader(bytes)
        cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS)
        ClassVisitor cv = new BaseClassVisitor(cw, this)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
    }

    void append(ClassVisitor cv) {
        if (!sr.shouldGenerateCode()) {
            return
        }
        MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "track",
                "(L" + sr.getClassName() + ";)V", null, null)
        mv.visitCode()

        if (sr.trackPage()) {
            mv.visitLdcInsn(sr.getValue())
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "autotrack/Tracker", "setPageTrack",
                    "(Ljava/lang/String;)V", false)
        }

        List<FieldRecorder> fields = sr.getFields()
        for (FieldRecorder fr : fields) {
            if (fr.trackClick()) {
                mv.visitVarInsn(Opcodes.ALOAD, 1)
                mv.visitFieldInsn(Opcodes.GETFIELD, sr.getClassName(), fr.getName(), fr.getDesc())
                mv.visitLdcInsn(fr.getValue())
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "autotrack/Tracker", "setClickTrack",
                        "(Ljava/lang/Object;Ljava/lang/String;)V", false)
            }
            if (fr.trackExposure()) {
                mv.visitVarInsn(Opcodes.ALOAD, 1)
                mv.visitFieldInsn(Opcodes.GETFIELD, sr.getClassName(), fr.getName(), fr.getDesc())
                mv.visitLdcInsn(fr.getValue())
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "autotrack/Tracker", "setExposureTrack",
                        "(Ljava/lang/Object;Ljava/lang/String;)V", false)
            }
        }
        mv.visitInsn(Opcodes.RETURN)
        mv.visitEnd()
    }

    byte[] dump() {
        return sr.shouldGenerateCode() ? cw.toByteArray() : null
    }
}
