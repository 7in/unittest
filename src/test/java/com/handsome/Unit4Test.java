package com.handsome;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by jiayu.shenjy on 2016/3/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:bean.xml")
public class Unit4Test {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    A a;

    @Test
    public void test() {
        logger.error("com.handsome.Unit4Test.test start！！！");
        try {
            a.printHelloB();
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}
