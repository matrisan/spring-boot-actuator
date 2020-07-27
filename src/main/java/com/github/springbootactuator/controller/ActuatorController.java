package com.github.springbootactuator.controller;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.redis.RedisHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 创建时间为 15:38-2019-03-21
 * 项目名称 SpringBootActuator
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */


@RestController
public class ActuatorController {

    @Resource
    private Map<String, HealthIndicator> map;

//    @Resource
//    private AbstractHealthIndicator indicator;

    @GetMapping("/hello")
    public String getHello() {
//        System.out.println(indicator.health().toString());
        return "hello";
    }

//    @Bean
//    public AbstractHealthIndicator redisHealthIndicator(RedisConnectionFactory connectionFactory){
//        return new RedisHealthIndicator(connectionFactory);
//    }

}
