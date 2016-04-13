package com.handsome.imock.configuration;

import org.mockito.ReturnValues;
import org.mockito.configuration.AnnotationEngine;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.internal.stubbing.defaultanswers.ReturnsEmptyValues;
import org.mockito.stubbing.Answer;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockDefaultMockitoConfiguration implements IMockitoConfiguration {

    /* (non-Javadoc)
     * @see org.mockito.IMockitoConfiguration#getReturnValues()
     */
    @Deprecated
    public ReturnValues getReturnValues() {
        throw new RuntimeException("\n" + "This method should not be used by the framework because it was deprecated"
                + "\n" + "Please report the failure to the Mockito mailing list");
    }

    public Answer<Object> getDefaultAnswer() {
        return new ReturnsEmptyValues();
    }

    /* (non-Javadoc)
     * @see org.mockito.IMockitoConfiguration#getAnnotationEngine()
     */
    public AnnotationEngine getAnnotationEngine() {
        return new IMockInjectingAnnotationEngine();
    }

    /* (non-Javadoc)
     * @see org.mockito.configuration.IMockitoConfiguration#cleansStackTrace()
     */
    public boolean cleansStackTrace() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.mockito.configuration.IMockitoConfiguration#enableClassCache()
     */
    public boolean enableClassCache() {
        return true;
    }


}