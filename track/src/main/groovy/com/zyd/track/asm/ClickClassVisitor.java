package com.zyd.track.asm;

import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor;
import com.android.tools.r8.org.objectweb.asm.ClassVisitor;
import com.android.tools.r8.org.objectweb.asm.Label;
import com.android.tools.r8.org.objectweb.asm.MethodVisitor;
import com.android.tools.r8.org.objectweb.asm.Opcodes;
import com.android.tools.r8.org.objectweb.asm.commons.AdviceAdapter;

public class ClickClassVisitor extends ClassVisitor {

    public ClickClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        mv = new AdviceAdapter(Opcodes.ASM6, mv, access, name, desc) {

            ClickInterceptorAnnotationVisitor ciav = null;

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                if (desc.equals("Lcom/zyd/interceptor/Interceptor;")) {
                    ciav = new ClickInterceptorAnnotationVisitor(super.visitAnnotation(desc, visible));
                    return ciav;
                }
                return super.visitAnnotation(desc, visible);
            }

            @Override
            protected void onMethodEnter() {
                if (((access & ACC_PUBLIC) != 0) && ((access & ACC_STATIC) == 0) &&
                        name.equals("onClick") && desc.equals("(Landroid/view/View;)V")) {

                    mv.visitVarInsn(ALOAD, 1);
                    if (ciav == null || ciav.time == ClickInterceptorAnnotationVisitor.NULL) {
                        mv.visitMethodInsn(INVOKESTATIC, "com/zyd/interceptor/ClickInterceptor",
                                "getDefaultInterceptTime", "()J", false);
                    } else {
                        mv.visitLdcInsn(ciav.time);
                    }
                    mv.visitMethodInsn(INVOKESTATIC, "com/zyd/interceptor/ClickInterceptor",
                            "canClick", "(Landroid/view/View;J)Z", false);
                    Label label = new Label();
                    mv.visitJumpInsn(IFNE, label);
                    mv.visitInsn(RETURN);
                    mv.visitLabel(label);
                }
            }

            @Override
            protected void onMethodExit(int opcode) {
                super.onMethodExit(opcode);
            }
        };
        return mv;
    }

}