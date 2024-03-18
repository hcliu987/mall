package com.hc.mall.ware.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("com.hc.mall.ware.dao")
@Configuration
public class WareMyBatisConfig {
    //引入分页插件
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        //溢出总页数后是否进行处理(默认不处理)
//        paginationInnerInterceptor.setOverflow(true);
//        //单页分页条数限制(默认无限制)
//        paginationInnerInterceptor.setMaxLimit(1000L);
//        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;

    }

}