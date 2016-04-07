package com.handsome;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.mockito.Mockito.when;

/**
 * Created by jiayu.shenjy on 2016/3/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class MockTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Spy
    @InjectMocks
    @Resource
    A a;
    @Mock
    B b;

    @Test
    public void test() {
        logger.error("com.handsome.Unit4Test.test start！！！");
        try {
            a.printHelloB();
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(b.getHelloWord()).thenReturn("mock words");
    }
}
