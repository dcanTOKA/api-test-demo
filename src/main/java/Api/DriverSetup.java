package Api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.LOCAL_VARIABLE, ElementType.METHOD , ElementType.TYPE , ElementType.FIELD})
public @interface DriverSetup {
    String browserType();
    boolean headlessMode();
}
