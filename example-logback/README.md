**一.新增一个appender的步骤：**

0.配置logback配置文件
pom中增加依赖。增加logback.xml
    
    <properties>
        <slf4j.version>1.7.25</slf4j.version>
    </properties>


    <dependencies>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.0.13</version>
        </dependency>


        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-jdk14</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
    </dependencies>

1.在java中实现：
private static final Logger single = LoggerFactory.getLogger("single");

2.在logback.xml中增加如下配置
 <!--增加singleAppender的依赖-->
    <appender name="singleAppender"
              class="ch.qos.logback.core.rolling.RollingFileAppender">
        <File>logs/single.log</File>
        <rollingPolicy
                class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>
                logs/single.%d{yyyy-MM-dd_HH-mm}.log.zip
            </FileNamePattern>
        </rollingPolicy>

        <layout class="ch.qos.logback.classic.PatternLayout">
            <Pattern>
                %d{HH:mm:ss,SSS} [%thread] %-5level %logger{32} - %msg%n
            </Pattern>
        </layout>
    </appender>


    <!--增加single的logger-->
    <logger name="single" additivity="false">
        <level value="WARN"/>
        <appender-ref ref="singleAppender"/>
    </logger>

验证完成

**二.logbaJMX动态修改日志级别：**
官网介绍地址：http://mcs.une.edu.au/doc/logback/manual/jmxConfig.html
有几种实现方式：

Reload logback configuration using the default configuration file.
Reload the configuration with the specified URL.
Reload the configuration with the specified file.
Set the level of a specified logger. To set to null, pass the string "null" as value.
Get the level of a specified logger. The returned value can be null.
Get the effective level of a specified logger.

翻译入下：

使用默认配置文件重新加载logback配置。
使用指定的URL重新加载配置。
使用指定的文件重新加载配置。
设置指定记录器的级别。要设置为null，将字符串“null”作为值传递。
获取指定记录器的级别。返回的值可以为null。
获取指定记录器的有效级别。


我们使用配置文件加载的方式开启：
1.添加配置，开启jmx： 
//开启扫描，每3秒钟扫描一次
<configuration scan="true" scanPeriod="3 seconds">
    <!-- 动态加载配置文件 -->
    <jmxConfigurator />
    
    
   stackOverFlow上这么说：（需要在运行时改变日志级别）
   
    LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
    context.getLogger("single").setLevel(Level.valueOf("INFO"));
2.




