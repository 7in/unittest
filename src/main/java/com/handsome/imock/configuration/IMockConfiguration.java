package com.handsome.imock.configuration;

import org.mockito.ReturnValues;
import org.mockito.configuration.AnnotationEngine;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.internal.configuration.ClassPathLoader;
import org.mockito.stubbing.Answer;

import java.io.Serializable;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockConfiguration implements IMockitoConfiguration, Serializable {
    private static final long serialVersionUID = -4585716085145436241L;

    private static ThreadLocal<IMockitoConfiguration> globalConfiguration = new ThreadLocal<IMockitoConfiguration>();

    //back door for testing
    IMockitoConfiguration getIt() {
        return globalConfiguration.get();
    }

    public IMockConfiguration() {
        //Configuration should be loaded only once but I cannot really test it
        if (globalConfiguration.get() == null) {
            globalConfiguration.set(createConfig());
        }
    }

    private IMockitoConfiguration createConfig() {
        IMockitoConfiguration defaultConfiguration = new IMockDefaultMockitoConfiguration();
        IMockitoConfiguration config = new ClassPathLoader().loadConfiguration();
        if (config != null) {
            return config;
        } else {
            return defaultConfiguration;
        }
    }

    public static void validate() {
        new IMockConfiguration();
    }

    public ReturnValues getReturnValues() {
        return globalConfiguration.get().getReturnValues();
    }

    public AnnotationEngine getAnnotationEngine() {
        return globalConfiguration.get().getAnnotationEngine();
    }

    public boolean cleansStackTrace() {
        return globalConfiguration.get().cleansStackTrace();
    }

    public boolean enableClassCache() {
        return globalConfiguration.get().enableClassCache();
    }

    public Answer<Object> getDefaultAnswer() {
        return globalConfiguration.get().getDefaultAnswer();
    }
}