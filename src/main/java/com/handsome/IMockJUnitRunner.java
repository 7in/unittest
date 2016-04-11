package com.handsome;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.Filterable;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.mockito.internal.runners.RunnerImpl;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockJUnitRunner  extends Runner implements Filterable {
    private final RunnerImpl runner;

    public IMockJUnitRunner(Class<?> klass) throws InvocationTargetException {
        runner = new IMockRunnerFactory().create(klass);
    }
    @Override
    public void run(final RunNotifier notifier) {
        runner.run(notifier);
    }

    @Override
    public Description getDescription() {
        return runner.getDescription();
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        //filter is required because without it UnrootedTests show up in Eclipse
        runner.filter(filter);
    }
}
