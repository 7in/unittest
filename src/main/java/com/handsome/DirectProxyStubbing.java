package com.handsome;

import org.mockito.internal.stubbing.BaseStubbing;
import org.mockito.stubbing.DeprecatedOngoingStubbing;
import org.mockito.stubbing.OngoingStubbing;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public abstract class DirectProxyStubbing<T> extends BaseStubbing<T> implements OngoingStubbing<T>, DeprecatedOngoingStubbing<T> {
    public OngoingStubbing<T> thenAsk(T value) {
        return thenAnswer(new DirectForAnswer());
    }
}
