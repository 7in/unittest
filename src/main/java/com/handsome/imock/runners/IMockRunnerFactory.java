package com.handsome.imock.runners;

import org.mockito.exceptions.base.MockitoException;
import org.mockito.internal.runners.RunnerFactory;
import org.mockito.internal.runners.RunnerImpl;
import org.mockito.internal.runners.util.RunnerProvider;
import org.mockito.internal.runners.util.TestMethodsFinder;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockRunnerFactory extends RunnerFactory {
    private final RunnerProvider runnerProvider;

    IMockRunnerFactory(RunnerProvider runnerProvider) {
        this.runnerProvider = runnerProvider;
    }

    public IMockRunnerFactory() {
        this(new RunnerProvider());
    }

    @Override
    public RunnerImpl create(Class<?> klass) throws InvocationTargetException {
        try {
            if (runnerProvider.isJUnit45OrHigherAvailable()) {
                return runnerProvider.newInstance("com.handsome.imock.runners.IMockJUnit45AndHigherRunnerImpl", klass);
            } else {
                return runnerProvider.newInstance("org.mockito.internal.runners.JUnit44RunnerImpl", klass);
            }
        } catch (InvocationTargetException e) {
            if (!new TestMethodsFinder().hasTestMethods(klass)) {
                throw new MockitoException(
                        "\n" +
                                "\n" +
                                "No tests found in " + klass.getSimpleName() + "\n" +
                                "Haven't you forgot @Test annotation?\n"
                        , e);
            }
            throw e;
        } catch (Throwable t) {
            throw new MockitoException(
                    "\n" +
                            "\n" +
                            "MockitoRunner can only be used with JUnit 4.4 or higher.\n" +
                            "You can upgrade your JUnit version or write your own Runner (please consider contributing your runner to the Mockito community).\n" +
                            "Bear in mind that you can still enjoy all features of the framework without using runners (they are completely optional).\n" +
                            "If you get this error despite using JUnit 4.4 or higher then please report this error to the mockito mailing list.\n"
                    , t);
        }
    }
}
