package com.hc.mall.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mall.thread")
@Data
@Component
public class ThreadPoolConfigProperties {
    private  int corePoolSize;
    private int maxPoolSize;
    private long keepAliveTime;
}
