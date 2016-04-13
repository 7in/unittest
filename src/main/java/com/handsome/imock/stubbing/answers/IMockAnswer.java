package com.handsome.imock.stubbing.answers;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.Serializable;

/**
 * Created by jiayu.shenjy on 2016/4/11.
 */
public class IMockAnswer implements Answer<Object>, Serializable {
    private static final long serialVersionUID = -5025846846899380426L;

//    @Resource
//    IMockService iMockService;

    public Object getAnswer(InvocationOnMock invocation) {
//        Object obj = iMockService.getAnswer();
        System.out.println("帅气的枭臣,and chongyue!");
        return null;
    }

    @Override
    public Object answer(InvocationOnMock invocation) throws Throwable {
        return getAnswer(invocation);
    }
}
