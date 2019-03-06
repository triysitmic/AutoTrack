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
        println sr.toString()

        appendClass(cv)
        appendField(cv)
    }

    void appendClass(ClassVisitor cv) {
        MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "trackPage",
                "()V", null, null)
        mv.visitCode()
        if (sr.trackPage()) {
            List<String> values = sr.getValues()

            mv.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList",
                    "<init>", "()V", false)
            mv.visitVarInsn(Opcodes.ASTORE, 1)

            for (String value : values) {
                mv.visitVarInsn(Opcodes.ALOAD, 1)
                mv.visitLdcInsn(value)
                mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add",
                        "(Ljava/lang/Object;)Z", true)
                mv.visitInsn(Opcodes.POP)
            }
            mv.visitVarInsn(Opcodes.ALOAD, 1)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "autotrack/Tracker", "setPageTrack",
                    "(Ljava/util/List;)V", false)
        }
        mv.visitInsn(Opcodes.RETURN)
        mv.visitEnd()
    }

    void appendField(ClassVisitor cv) {
        MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC, "trackView",
                "(L" + sr.getName() + ";)V", null, null)
        mv.visitCode()

        List<FieldRecorder> fields = sr.getFields()
        for (int i = 0; i < fields.size(); i++) {
            int position = i + 2
            FieldRecorder fr = fields.get(i)

            //init ArrayList
            mv.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList")
            mv.visitInsn(Opcodes.DUP)
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList",
                    "<init>", "()V", false)
            mv.visitVarInsn(Opcodes.ASTORE, position)

            //add items
            for (String value : fr.getValues()) {
                mv.visitVarInsn(Opcodes.ALOAD, 2)
                mv.visitLdcInsn(value)
                mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List",
                        "add", "(Ljava/lang/Object;)Z", true)
                mv.visitInsn(Opcodes.POP)
            }

            //track
            if (fr.trackClick()) {
                mv.visitVarInsn(Opcodes.ALOAD, 1)
                mv.visitFieldInsn(Opcodes.GETFIELD, sr.getName(), fr.getName(), fr.getDesc())
                mv.visitVarInsn(Opcodes.ALOAD, position)
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "autotrack/Tracker", "setClickTrack",
                        "(Ljava/lang/Object;Ljava/util/List;)V", false)
            }
            if (fr.trackExposure()) {
                mv.visitVarInsn(Opcodes.ALOAD, 1)
                mv.visitFieldInsn(Opcodes.GETFIELD, sr.getName(), fr.getName(), fr.getDesc())
                mv.visitVarInsn(Opcodes.ALOAD, position)
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "autotrack/Tracker", "setExposureTrack",
                        "(Ljava/lang/Object;Ljava/util/List;)V", false)
            }
        }

        mv.visitInsn(Opcodes.RETURN)
        mv.visitEnd()
    }

    byte[] dump() {
        return sr.shouldGenerateCode() ? cw.toByteArray() : null
    }
}