package work.shashank.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Shashank Sharma
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Callbacks {

    /**
     * On entity Save, whether to raise application event
     * @return boolean
     */
    boolean raiseApplicationEvent() default true;

    /**
     * On entity Save, whether to audit the entity's current state in <b>ss_audit</b> table
     * @return boolean
     */
    boolean auditable() default false;
}
