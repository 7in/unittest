package com.handsome.imock.configuration;

import com.handsome.imock.IMock;
import com.handsome.imock.IMockAnnotations;
import com.handsome.imock.configuration.IMockAnnotationProcessor;
import com.handsome.imock.configuration.IMockitoAnnotationsMockAnnotationProcessor;
import org.mockito.Captor;
import org.mockito.configuration.AnnotationEngine;
import org.mockito.exceptions.Reporter;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.configuration.*;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockAnnotationEngine extends InjectingAnnotationEngine implements AnnotationEngine {
    private final Map<Class<? extends Annotation>, FieldAnnotationProcessor<?>> annotationProcessorMap = new HashMap<Class<? extends Annotation>, FieldAnnotationProcessor<?>>();

    public IMockAnnotationEngine() {
        registerAnnotationProcessor(IMock.class, new IMockAnnotationProcessor());
        registerAnnotationProcessor(IMockAnnotations.IMock.class, new IMockitoAnnotationsMockAnnotationProcessor());
        registerAnnotationProcessor(Captor.class, new CaptorAnnotationProcessor());
    }

    /* (non-Javadoc)
    * @see org.mockito.AnnotationEngine#createMockFor(java.lang.annotation.Annotation, java.lang.reflect.Field)
    */
    @SuppressWarnings("deprecation")
    public Object createMockFor(Annotation annotation, Field field) {
        return forAnnotation(annotation).process(annotation, field);
    }

    private <A extends Annotation> FieldAnnotationProcessor<A> forAnnotation(A annotation) {
        if (annotationProcessorMap.containsKey(annotation.annotationType())) {
            return (FieldAnnotationProcessor<A>) annotationProcessorMap.get(annotation.annotationType());
        }
        return new FieldAnnotationProcessor<A>() {
            public Object process(A annotation, Field field) {
                return null;
            }
        };
    }

    private <A extends Annotation> void registerAnnotationProcessor(Class<A> annotationClass, FieldAnnotationProcessor<A> fieldAnnotationProcessor) {
        annotationProcessorMap.put(annotationClass, fieldAnnotationProcessor);
    }

    public void process(Class<?> clazz, Object testInstance) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean alreadyAssigned = false;
            for(Annotation annotation : field.getAnnotations()) {
                Object mock = createMockFor(annotation, field);
                if (mock != null) {
                    throwIfAlreadyAssigned(field, alreadyAssigned);
                    alreadyAssigned = true;
                    try {
                        new FieldSetter(testInstance, field).set(mock);
                    } catch (Exception e) {
                        throw new MockitoException("Problems setting field " + field.getName() + " annotated with "
                                + annotation, e);
                    }
                }
            }
        }
    }

    void throwIfAlreadyAssigned(Field field, boolean alreadyAssigned) {
        if (alreadyAssigned) {
            new Reporter().moreThanOneAnnotationNotAllowed(field.getName());
        }
    }

}