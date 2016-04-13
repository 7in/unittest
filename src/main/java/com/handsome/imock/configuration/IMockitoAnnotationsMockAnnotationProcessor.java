package com.handsome.imock.configuration;

import com.handsome.imock.IMockAnnotations;
import org.mockito.Mockito;
import org.mockito.internal.configuration.FieldAnnotationProcessor;

import java.lang.reflect.Field;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockitoAnnotationsMockAnnotationProcessor implements FieldAnnotationProcessor<IMockAnnotations.IMock> {

    public Object process(IMockAnnotations.IMock annotation, Field field) {
        return Mockito.mock(field.getType(), field.getName());
    }
}
