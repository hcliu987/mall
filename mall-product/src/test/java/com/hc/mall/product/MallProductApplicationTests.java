package com.hc.mall.product;

import com.hc.mall.product.config.RedissonConfig;
import com.hc.mall.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.UUID;

@SpringBootTest
class MallProductApplicationTests {


    @Autowired
    RedissonClient redissonClient;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    CategoryService categoryService;
    @Test
    void contextLoads() {
        System.out.println(categoryService.findCatelogPath(225L));
    }

    @Test
    public  void testRedis(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("hello","world_"+ UUID.randomUUID().toString());
        //查询
        String hello = ops.get("hello");
        System.out.println(hello);
    }
    @Test
    public  void  test(){
        System.out.println(redissonClient);
    }
}
