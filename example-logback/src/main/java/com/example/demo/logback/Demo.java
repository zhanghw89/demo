package com.example.demo.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by shiwen on 2017/9/9.
 */
public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);
    private static final Logger single = LoggerFactory.getLogger("single");


    public static void main(String[] args) throws IOException {
        int i =0;
        while(true){
            final int j = i;
            new Thread(new Runnable() {
                public void run() {
                    logger.info("info,hello ,it's me!num={}",j);
                    logger.debug("debug,hello ,it's me!num={}",j);
                    logger.warn("warn,hello ,it's me!num={}",j);

                    single.info("info,hello ,it's me!num={}",j);
                    single.debug("debug,hello ,it's me!num={}",j);
                    single.warn("warn,hello ,it's me!num={}",j);

                }
            }).start();
            i++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
