package com.github.springbootactuator;

import org.junit.Test;

import java.text.DecimalFormat;

/**
 * <p>
 * 创建时间为 16:35 2019-04-30
 * 项目名称 spring-boot-actuator
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

public class DecimalFormatTest {

    @Test
    public void test1() {
        DecimalFormat df1 = new DecimalFormat("###.00");
        System.out.println(Float.valueOf(df1.format(112.225455)));

    }

}
