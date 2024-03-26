package com.hc.mall.seckill.schedule;

import com.hc.mall.seckill.service.SecKillService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SecKillScheduled {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private SecKillService secKillService;
    //秒杀商品上架功能的锁
    private final String upload_lock = "seckill:upload:lock";

    /**
     * 定时任务
     * 每天三点上架最近三天的秒杀商品
     */
    @Async
    @Scheduled(cron = "0 0 3 * * ?")
    public void uploadSeckillSkuLatest3Days(){
        RLock lock = redissonClient.getLock(upload_lock);
        try {
            lock.lock(10, TimeUnit.SECONDS);
            secKillService.uploadSeckillSkuLatest3Days();;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();;
        }
    }
}
