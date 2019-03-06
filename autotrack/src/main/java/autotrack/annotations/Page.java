package autotrack.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengyanda
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Page {
    String[] value();
}
