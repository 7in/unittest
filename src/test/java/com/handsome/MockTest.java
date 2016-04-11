package com.handsome;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.mockito.Mockito.when;

/**
 * Created by jiayu.shenjy on 2016/3/14.
 */
@RunWith(IMockJUnitRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class MockTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Spy
    @InjectMocks
    @Resource
    protected A a;
    @IMock
    protected B b;

    @Test
    public void test() {
        logger.error("com.handsome.Unit4Test.test start！！！");
        try {
            a.printHelloWord();
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

    @Before
    public void setUp() {
//        IMockAnnotations.initMocks(this);
//        when(b.getHelloWord()).thenReturn("mock words");
//        when(b.getHelloWord()).then(new DirectForAnswer());
    }
}
