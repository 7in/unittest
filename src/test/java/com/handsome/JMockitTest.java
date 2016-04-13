package com.handsome;

import com.handsome.bean.A;
import com.handsome.bean.B;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by jiayu.shenjy on 2016/4/13.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(JMockit.class)
@ContextConfiguration(locations = "classpath:bean.xml")
public class JMockitTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Tested
    @Resource
    A a;
    //    @Mocked
    @Injectable
    B b;

    @Test
    public void jMockTest() {
        //1. mock对象
        MockUp<B> mockUp = new MockUp<B>() {
            @Mock
            public String getHelloWord() {
                return "帅气的冲跃";
            }
        };

        //2. 获取实例
//        b = mockUp.getMockInstance();

        a.printHelloWord();
    }

    @Test
    public void jMockStaticTest() {
        //1. mock对象
        MockUp<B> mockUp = new MockUp<B>() {
            @Mock
            public String staticHelloWord() {
                return "帅气的冲跃";
            }
        };

        //2. 获取实例
//        b = mockUp.getMockInstance();

        a.printStaticHelloWord();
    }
}
