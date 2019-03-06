package autotrack.api

import autotrack.constants.Constant
import autotrack.recorder.FieldRecorder
import autotrack.recorder.SourceRecorder

import com.android.tools.r8.org.objectweb.asm.ClassWriter
import com.android.tools.r8.org.objectweb.asm.Label
import com.android.tools.r8.org.objectweb.asm.MethodVisitor
import com.android.tools.r8.org.objectweb.asm.Opcodes

@Deprecated
class Generator implements Opcodes {

    public final static String METHOD_TRACK = "track"

    private SourceRecorder sr
    private String mClassName

    Generator(SourceRecorder sr) {
        this.sr = sr
        mClassName = sr.getClassName() + Constant.POSTFIX
    }

    //对sourceRecorder进行扫描，调用ASM生成一个中间类的字节码文件
    byte[] dump() {
        ClassWriter cw = new ClassWriter(0)

        //accept class header
        generateHeader(cw)

        //accept class constructor
        generateConstructor(cw)

        //accept method track
        generateMethodTrack(cw)
        return cw.toByteArray()
    }

    private void generateHeader(ClassWriter cw) {
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER,
                mClassName, null, "java/lang/Object", null)
    }

    private void generateConstructor(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>",
                "()V", null, null)
        mv.visitCode()
        Label l0 = new Label()
        mv.visitLabel(l0)
        mv.visitVarInsn(ALOAD, 0)
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object",
                "<init>", "()V", false)
        mv.visitInsn(RETURN)
        Label l1 = new Label()
        mv.visitLabel(l1)
        mv.visitLocalVariable("this", "L" + mClassName + ";", null, l0, l1, 0)
        mv.visitMaxs(1, 1)
        mv.visitEnd()
    }

    private void generateMethodTrack(ClassWriter cw) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, METHOD_TRACK,
                "(L" + sr.getClassName() + ";)V", null, null)
        mv.visitCode()

        List<FieldRecorder> fields = sr.getFields()
        for (int i = 0; i < fields.size(); i++) {
            FieldRecorder fr = fields.get(i)
            String name = fr.getName()
            String desc = fr.getDesc()

            mv.visitTypeInsn(NEW, "java/util/ArrayList")
            mv.visitInsn(DUP)
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false)
            mv.visitVarInsn(ASTORE, i + 1)


            Label l1 = new Label()
            mv.visitLabel(l1)
            mv.visitLineNumber(18, l1)
            mv.visitVarInsn(ALOAD, 1)
            mv.visitLdcInsn("123")
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z", true)
            mv.visitInsn(POP)
            Label l2 = new Label()
            mv.visitLabel(l2)
            mv.visitLineNumber(19, l2)
            mv.visitVarInsn(ALOAD, 1)
            mv.visitMethodInsn(INVOKESTATIC, "part4/TT", "sss", "(Ljava/util/List;)V", false)


        }
        for (FieldRecorder fr : fields) {
            if (fr.trackClick()) {
                mv.visitVarInsn(ALOAD, 1)
                mv.visitFieldInsn(GETFIELD, sr.getClassName(), fr.getName(), fr.getDesc())
                mv.visitLdcInsn(fr.getValue())
                mv.visitMethodInsn(INVOKESTATIC, "autotrack/Tracker", "setClickTrack",
                        "(Ljava/lang/Object;Ljava/lang/String;)V", false)
            }
            if (fr.trackExposure()) {
                mv.visitVarInsn(ALOAD, 1)
                mv.visitFieldInsn(GETFIELD, sr.getClassName(), fr.getName(), fr.getDesc())
                mv.visitLdcInsn(fr.getValue())
                mv.visitMethodInsn(INVOKESTATIC, "autotrack/Tracker", "setExposureTrack",
                        "(Ljava/lang/Object;Ljava/lang/String;)V", false)
            }
        }
        mv.visitInsn(RETURN)
        mv.visitEnd()
    }

}
