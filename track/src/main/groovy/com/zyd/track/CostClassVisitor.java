package com.zyd.track;

import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor;
import com.android.tools.r8.org.objectweb.asm.ClassVisitor;
import com.android.tools.r8.org.objectweb.asm.MethodVisitor;
import com.android.tools.r8.org.objectweb.asm.Opcodes;
import com.android.tools.r8.org.objectweb.asm.commons.AdviceAdapter;

import static com.android.tools.r8.org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static com.android.tools.r8.org.objectweb.asm.Opcodes.ACC_STATIC;

public class CostClassVisitor extends ClassVisitor {

    private boolean inject = false;

    public CostClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (((access & ACC_PUBLIC) != 0) && ((access & ACC_STATIC) == 0) &&
                name.equals("onClick") && desc.equals("(Landroid/view/View;)V")) {
            inject = true;
        }
        mv = new AdviceAdapter(Opcodes.ASM5, mv, access, name, desc) {
            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//                if (Type.getDescriptor(Cost.class).equals(desc)) {
//                    inject = true;
//                }
                return super.visitAnnotation(desc, visible);
            }

            @Override
            protected void onMethodEnter() {
                if (inject) {
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "getContext", "()Landroid/content/Context;", false);
                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "android/view/View", "toString", "()Ljava/lang/String;", false);
                    mv.visitInsn(ICONST_1);
                    mv.visitMethodInsn(INVOKESTATIC, "android/widget/Toast", "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "android/widget/Toast", "show", "()V", false);
//                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                    mv.visitLdcInsn("========start=========");
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//
//                    mv.visitLdcInsn("newFunc");
//                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/zyd/track/TimeCache", "setStartTime", "(Ljava/lang/String;J)V", false);
                }
            }

            @Override
            protected void onMethodExit(int opcode) {
                if (inject) {
//                    mv.visitLdcInsn(name);
//                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
//                    mv.visitMethodInsn(INVOKESTATIC, "com/zyd/track/TimeCache", "setEndTime", "(Ljava/lang/String;J)V", false);
//                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                    mv.visitLdcInsn(name);
//                    mv.visitMethodInsn(INVOKESTATIC, "main/java/TimeCache", "getCostTime", "(Ljava/lang/String;)Ljava/lang/String;", false);
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
//                    mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                    mv.visitLdcInsn("========end=========");
//                    mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                }
            }
        };
        return mv;
    }
}
