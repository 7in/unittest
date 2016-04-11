package com.handsome;

import org.mockito.MockitoAnnotations;
import org.mockito.configuration.AnnotationEngine;
import org.mockito.internal.configuration.DefaultAnnotationEngine;
import org.mockito.internal.configuration.DefaultInjectionEngine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.internal.util.collections.Sets.newMockSafeHashSet;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockInjectingAnnotationEngine implements AnnotationEngine {
    private AnnotationEngine delegate = new IMockAnnotationEngine();
    private AnnotationEngine spyAnnotationEngine = new IMockSpyAnnotationEngine();

    /***
     * Create a mock using {@link DefaultAnnotationEngine}
     *
     * @see org.mockito.internal.configuration.DefaultAnnotationEngine
     * @see org.mockito.configuration.AnnotationEngine#createMockFor(java.lang.annotation.Annotation, java.lang.reflect.Field)
     */
    @Deprecated
    public Object createMockFor(Annotation annotation, Field field) {
        return delegate.createMockFor(annotation, field);
    }

    /**
     * Process the fields of the test instance and create Mocks, Spies, Captors and inject them on fields
     * annotated &#64;InjectMocks.
     *
     * <p>
     * This code process the test class and the super classes.
     * <ol>
     * <li>First create Mocks, Spies, Captors.</li>
     * <li>Then try to inject them.</li>
     * </ol>
     *
     * @param clazz Not used
     * @param testInstance The instance of the test, should not be null.
     *
     * @see org.mockito.configuration.AnnotationEngine#process(Class, Object)
     */
    public void process(Class<?> clazz, Object testInstance) {
        processIndependentAnnotations(testInstance.getClass(), testInstance);
        processInjectMocks(testInstance.getClass(), testInstance);
    }

    private void processInjectMocks(final Class<?> clazz, final Object testInstance) {
        Class<?> classContext = clazz;
        while (classContext != Object.class) {
            injectMocks(testInstance);
            classContext = classContext.getSuperclass();
        }
    }

    private void processIndependentAnnotations(final Class<?> clazz, final Object testInstance) {
        Class<?> classContext = clazz;
        while (classContext != Object.class) {
            //this will create @Mocks, @Captors, etc:
            delegate.process(classContext, testInstance);
            //this will create @Spies:
            spyAnnotationEngine.process(classContext, testInstance);

            classContext = classContext.getSuperclass();
        }
    }


    /**
     * Initializes mock/spies dependencies for objects annotated with
     * &#064;InjectMocks for given testClassInstance.
     * <p>
     * See examples in javadoc for {@link MockitoAnnotations} class.
     *
     * @param testClassInstance
     *            Test class, usually <code>this</code>
     */
    public void injectMocks(final Object testClassInstance) {
        Class<?> clazz = testClassInstance.getClass();
        Set<Field> mockDependentFields = new HashSet<Field>();
        Set<Object> mocks = newMockSafeHashSet();

        while (clazz != Object.class) {
            new IMockInjectMocksScanner(clazz).addTo(mockDependentFields);
            new IMockScanner(testClassInstance, clazz).addPreparedMocks(mocks);
            clazz = clazz.getSuperclass();
        }

        new DefaultInjectionEngine().injectMocksOnFields(mockDependentFields, mocks, testClassInstance);
    }

}
