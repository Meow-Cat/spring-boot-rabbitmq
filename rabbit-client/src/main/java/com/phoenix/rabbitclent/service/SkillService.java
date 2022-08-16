package com.phoenix.rabbitclent.service;

import com.phoenix.rabbitcommon.utils.redis.RedisConstant;
import com.phoenix.rabbitcommon.utils.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * redisson watch dog机制
 * <p>
 *     watch dog在tryLock()时 leaseTime 不设置时才生效
 * </p>
 */
@Slf4j
@Service
public class SkillService {
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    RedisService redisService;

    public void seckill()
    {
        //定义锁
        RLock lock = redissonClient.getLock(RedisConstant.LOCK_KEY);
        try
        {
            // 尝试加锁,最大等待时间300毫秒，
            // 不设置leaseTime开启watch dog机制，leaseTime为watchDog的LockWatchdogTimeout时间
            if (lock.tryLock(300, TimeUnit.SECONDS))
            {
                log.info("线程:" + Thread.currentThread().getName() + "获得了锁");
                int num = redisService.getCacheMapValue(RedisConstant.MERCHANDISE_KEY,"no1004");
                if (num == 0)
                {
                    System.out.println("库存数量不足");
                }else
                {
                    redisService.setCacheMapValue(RedisConstant.MERCHANDISE_KEY,"no1004" ,num-1);
                }
            }
        } catch (Exception e)
        {
            log.error("程序执行异常", e);
        } finally
        {
            // 释放锁
            // 是否还是锁定状态
            if(lock.isLocked())
            {
                // 时候是当前执行线程的锁
                if(lock.isHeldByCurrentThread())
                {
                    log.info("线程:" + Thread.currentThread().getName() + "准备释放锁");
                    lock.unlock();
                }
            }
        }
    }



}
