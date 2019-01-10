package com.zyd.track;

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

            MyAnnotationVisitor myAnnotationVisitor = null;

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                if (desc.equals("Lcom/zyd/interceptor/Interceptor;")) {
                    myAnnotationVisitor = new MyAnnotationVisitor(super.visitAnnotation(desc, visible));
                    return myAnnotationVisitor;
                }
                return super.visitAnnotation(desc, visible);
            }

            @Override
            protected void onMethodEnter() {
                if (((access & ACC_PUBLIC) != 0) && ((access & ACC_STATIC) == 0) &&
                        name.equals("onClick") && desc.equals("(Landroid/view/View;)V")) {

                    long value;
                    if (myAnnotationVisitor == null) {
                        value = 1000L;
                    } else {
                        value = myAnnotationVisitor.time == -1 ? 1000L : myAnnotationVisitor.time;
                    }

                    mv.visitVarInsn(ALOAD, 1);
                    mv.visitLdcInsn(value);
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

    class MyAnnotationVisitor extends AnnotationVisitor {

        long time = -1;

        MyAnnotationVisitor(AnnotationVisitor av) {
            super(Opcodes.ASM6, av);
        }

        @Override
        public void visit(String name, Object value) {
            if ("interceptorTime".equals(name)) {
                long v = (long) value;
                time = v <= 0 ? 0 : v;
            }
            super.visit(name, value);
        }
    }
}
