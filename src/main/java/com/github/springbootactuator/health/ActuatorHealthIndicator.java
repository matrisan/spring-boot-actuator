package com.github.springbootactuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * 自定义 actuator 需要实现{@link HealthIndicator}接口, 类名即为 http://localhost:8808/actuator/health 中 details-> ActHealth 名称
 * 1.编写一个指示器，实现 HealthIndicator 接口
 * 2.指示器的名字 xxxxHealthIndicator
 * 3.加入容器中管理
 * <p>
 * 创建时间为 15:39-2019-03-21
 * 项目名称 SpringBootActuator
 * </p>
 *
 * @author shao
 * @version 0.0.1
 * @since 0.0.1
 */


@Component("ActHealth")
public class ActuatorHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // return Health.down().withDetail("error","spring boot error").build();
        return Health.up().withDetail("up", "spring boot up").build();
    }
}
