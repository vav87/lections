package ru.digitalhabbits.sb;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = TYPE)
public @interface DeprecatedClass {
    Class<?> newImpl();
}
