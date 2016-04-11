package com.handsome;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.mockito.internal.runners.RunnerImpl;
import org.mockito.internal.runners.util.FrameworkUsageValidator;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockJUnit45AndHigherRunnerImpl implements RunnerImpl {

    private BlockJUnit4ClassRunner runner;

    public IMockJUnit45AndHigherRunnerImpl(Class<?> klass) throws InitializationError {
        runner = new BlockJUnit4ClassRunner(klass) {
            protected Statement withBefores(FrameworkMethod method, Object target,
                                            Statement statement) {
                // init annotated mocks before tests
                IMockAnnotations.initMocks(target);
                return super.withBefores(method, target, statement);
            }
        };
    }

    public void run(final RunNotifier notifier) {
        // add listener that validates framework usage at the end of each test
        notifier.addListener(new FrameworkUsageValidator(notifier));

        runner.run(notifier);
    }

    public Description getDescription() {
        return runner.getDescription();
    }

    public void filter(Filter filter) throws NoTestsRemainException {
        runner.filter(filter);
    }
}