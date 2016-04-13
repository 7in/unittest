package com.handsome.bean;

import org.springframework.stereotype.Repository;

/**
 * Created by jiayu.shenjy on 2016/3/14.
 */
@Repository("b")
public class B {
    public void printHelloB(){
        System.out.println("Hello ,b!");
    }

    public String getHelloWord(){
        return "HelloWord!";
    }

    public static String staticHelloWord(){
        return "staticHelloWord!";
    }
}
