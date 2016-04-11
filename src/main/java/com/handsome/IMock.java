package com.handsome;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */

import org.mockito.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mark a field as a mock.
 *@Describtion extend from Mockito#mock(Class)
 * @see Mockito#mock(Class)
 * @see Spy
 * @see InjectMocks
 * @see MockitoAnnotations#initMocks(Object)
 * @see org.mockito.runners.MockitoJUnitRunner
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface IMock {

    Answers answer() default Answers.RETURNS_DEFAULTS;

    String name() default "";

    Class<?>[] extraInterfaces() default {};
}
