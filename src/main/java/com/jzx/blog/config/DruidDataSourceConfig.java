package com.jzx.blog.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration //配置类必须加此注解
@Primary //当一个接口有2个不同实现时,使用@Autowired注解时会报异常  使用此注解会默认优先选择实现
public class DruidDataSourceConfig{

    @Bean(name = "springDruidDataSource")
    @ConfigurationProperties("spring.datasource") // 该注解可以自动注入对象的属性(对应配置文件spring.datasource下的属性)
    public DruidDataSource newsPoiDataSource() {
        return new DruidDataSource();
    }
}