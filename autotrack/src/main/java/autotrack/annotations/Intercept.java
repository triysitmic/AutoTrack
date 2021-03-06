package autotrack.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhengyanda
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Intercept {
    long time() default 500L;
}
