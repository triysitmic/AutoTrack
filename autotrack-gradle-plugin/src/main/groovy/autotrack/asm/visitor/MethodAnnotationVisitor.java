package autotrack.asm.visitor;

import com.android.tools.r8.org.objectweb.asm.AnnotationVisitor;
import com.android.tools.r8.org.objectweb.asm.Opcodes;

/**
 * Created by zhengyanda on 2019/3/5
 */
public class MethodAnnotationVisitor extends AnnotationVisitor {

    public MethodAnnotationVisitor(AnnotationVisitor av) {
        super(Opcodes.ASM6, av);
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
    }
}
