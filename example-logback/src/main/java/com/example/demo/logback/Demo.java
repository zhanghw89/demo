package com.example.demo.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.example.demo.logback.jmx.LogbackJmxConfiguratorManager;
import com.example.demo.logback.jmx.LogbackJmxConfiguratorManagerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by shiwen on 2017/9/9.
 */
public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);
    private static final Logger single = LoggerFactory.getLogger("single");

    private static LogbackJmxConfiguratorManager logbackJMXManager = new LogbackJmxConfiguratorManagerImpl();

    public static void main(String[] args) throws Exception {
        logbackJMXManager.start();

        int i =0;
        while(true){
            final int j = i;
            if(j==10){
                LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
                context.getLogger("single").setLevel(Level.valueOf("INFO"));
            }

            new Thread(new Runnable() {
                public void run() {
                    logger.info("info,hello ,it's me!num={}",j);
                    logger.debug("debug,hello ,it's me!num={}",j);
                    logger.warn("warn,hello ,it's me!num={}",j);

                    single.info("single info,hello ,it's me!num={}",j);
                    single.debug("single debug,hello ,it's me!num={}",j);
                    single.warn("single warn,hello ,it's me!num={}",j);

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
