package autotrack.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengyanda
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Click {
    String value();
}
