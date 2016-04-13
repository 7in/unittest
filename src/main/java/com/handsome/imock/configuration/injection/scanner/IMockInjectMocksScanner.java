package com.handsome.imock.configuration.injection.scanner;

import com.handsome.imock.IMock;
import com.handsome.imock.IMockAnnotations;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.exceptions.Reporter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockInjectMocksScanner {
    private final Class<?> clazz;

    /**
     * Create a new InjectMocksScanner for the given clazz on the given instance
     *
     * @param clazz    Current class in the hierarchy of the test
     */
    public IMockInjectMocksScanner(Class<?> clazz) {
        this.clazz = clazz;
    }


    /**
     * Add the fields annotated by @{@link InjectMocks}
     *
     * @param mockDependentFields Set of fields annotated by  @{@link InjectMocks}
     */
    public void addTo(Set<Field> mockDependentFields) {
        mockDependentFields.addAll(scan());
    }

    /**
     * Scan fields annotated by &#064;InjectMocks
     *
     * @return Fields that depends on Mock
     */
    private Set<Field> scan() {
        Set<Field> mockDependentFields = new HashSet<Field>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (null != field.getAnnotation(InjectMocks.class)) {
                assertNoAnnotations(field, IMock.class, IMockAnnotations.IMock.class, Captor.class);
                mockDependentFields.add(field);
            }
        }

        return mockDependentFields;
    }

    void assertNoAnnotations(final Field field, final Class... annotations) {
        for (Class annotation : annotations) {
            if (field.isAnnotationPresent(annotation)) {
                new Reporter().unsupportedCombinationOfAnnotations(annotation.getSimpleName(), InjectMocks.class.getSimpleName());
            }
        }
    }
}
