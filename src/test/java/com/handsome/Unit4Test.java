package com.handsome;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jiayu.shenjy on 2016/3/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:bean.xml")
public class Unit4Test {
    Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Resource
//    A a;

    @Test
    public void test() {
        try {
            A a =(A) BeanUtils.instantiateClass(A.class);
            a.printHelloB();
//        String hierarchy = ClassLoaderUtils.showClassLoaderHierarchy(this.getClass().getClassLoader());
//        System.out.println(hierarchy);
        } catch (Exception e) {
            logger.error("hehe", e);
        }
    }
}
