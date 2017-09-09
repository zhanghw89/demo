package com.example.demo.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by shiwen on 2017/9/9.
 */
public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);


    public static void main(String[] args) {
        logger.info("hello ,it's me!");
    }
}
