package com.handsome;

import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.configuration.FieldAnnotationProcessor;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.reflect.Field;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockAnnotationProcessor implements FieldAnnotationProcessor<IMock>{
    public Object process(IMock annotation, Field field) {
        MockSettings mockSettings = Mockito.withSettings();
        if (annotation.extraInterfaces().length > 0) { // never null
            mockSettings.extraInterfaces(annotation.extraInterfaces());
        }
        if ("".equals(annotation.name())) {
            mockSettings.name(field.getName());
        } else {
            mockSettings.name(annotation.name());
        }

        // see @Mock answer default value
        mockSettings.defaultAnswer(annotation.answer().get());
        Object obj=Mockito.mock(field.getType(), mockSettings);
        try {
            IMockUtil.assembleMockAsk(field.getType(), obj);
        } catch (Exception e) {
            throw new MockitoException("Problems assembleMockAsk field " + field.getName() + " annotated with "
                    + annotation, e);
        }
        return obj;
    }
}
