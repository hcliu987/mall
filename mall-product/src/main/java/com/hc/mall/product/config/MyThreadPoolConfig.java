package com.hc.mall.product.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(ThreadPoolConfigProperties.class)
public class MyThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfigProperties threadPoolConfigProperties){

        return  new ThreadPoolExecutor(
                threadPoolConfigProperties.getCorePoolSize(),
                threadPoolConfigProperties.getMaxPoolSize(),
                threadPoolConfigProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10000),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}