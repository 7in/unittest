package com.handsome.imock.configuration;

import com.handsome.imock.IMock;
import com.handsome.imock.IMockAnnotations;
import org.mockito.*;
import org.mockito.configuration.AnnotationEngine;
import org.mockito.exceptions.Reporter;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.util.MockUtil;
import org.mockito.internal.util.reflection.FieldInitializationReport;
import org.mockito.internal.util.reflection.FieldInitializer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static org.mockito.Mockito.withSettings;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockSpyAnnotationEngine implements AnnotationEngine {

    public Object createMockFor(Annotation annotation, Field field) {
        return null;
    }

    @SuppressWarnings("deprecation") // for MockitoAnnotations.Mock
    public void process(Class<?> context, Object testInstance) {
        Field[] fields = context.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Spy.class) && !field.isAnnotationPresent(InjectMocks.class)) {
                assertNoIncompatibleAnnotations(Spy.class, field, IMock.class, IMockAnnotations.IMock.class, Captor.class);
                Object instance = null;
                try {
                    FieldInitializationReport report = new FieldInitializer(testInstance, field).initialize();
                    instance = report.fieldInstance();
                } catch (MockitoException e) {
                    new Reporter().cannotInitializeForSpyAnnotation(field.getName(), e);
                }
                try {
                    if (new MockUtil().isMock(instance)) {
                        // instance has been spied earlier
                        // for example happens when MockitoAnnotations.initMocks is called two times.
                        Mockito.reset(instance);
                    } else {
                        field.setAccessible(true);
                        field.set(testInstance, Mockito.mock(instance.getClass(), withSettings()
                                .spiedInstance(instance)
                                .defaultAnswer(Mockito.CALLS_REAL_METHODS)
                                .name(field.getName())));
                    }
                } catch (IllegalAccessException e) {
                    throw new MockitoException("Problems initiating spied field " + field.getName(), e);
                }
            }
        }
    }

    //TODO duplicated elsewhere
    void assertNoIncompatibleAnnotations(Class annotation, Field field, Class... undesiredAnnotations) {
        for (Class u : undesiredAnnotations) {
            if (field.isAnnotationPresent(u)) {
                new Reporter().unsupportedCombinationOfAnnotations(annotation.getSimpleName(), annotation.getClass().getSimpleName());
            }
        }
    }
}
