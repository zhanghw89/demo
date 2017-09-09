package com.example.demo.logback.jmx;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.jmx.JMXConfigurator;
import ch.qos.logback.classic.jmx.MBeanUtil;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class LogbackJmxConfiguratorManagerImpl implements LogbackJmxConfiguratorManager {
    private ObjectName objectName;

    @Override
    public void start() throws Exception {
        Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        LoggerContext context = root.getLoggerContext();
        String contextName = context.getName();
        String objectNameAsStr = MBeanUtil.getObjectNameFor(contextName, JMXConfigurator.class);
        objectName = MBeanUtil.string2ObjectName(context, this, objectNameAsStr);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        if (!MBeanUtil.isRegistered(mbs, objectName)) {
            JMXConfigurator jmxConfigurator = new JMXConfigurator(context, mbs, objectName);
            try {
                mbs.registerMBean(jmxConfigurator, objectName);
            } catch (Exception e) {
                root.error("Failed to create mbean", e);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        mbs.unregisterMBean(objectName);
    }
}