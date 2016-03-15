package com.handsome;


import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by jiayu.shenjy on 2016/3/14.
 */
@Repository("a")
public class A {
    @Resource
    B b;
    public void printHelloA(){
        System.out.println("Hello ,a!");
    }

    public void printHelloB(){b.printHelloB();}
}
