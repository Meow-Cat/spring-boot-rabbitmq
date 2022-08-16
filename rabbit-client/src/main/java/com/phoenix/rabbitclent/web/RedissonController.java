package com.phoenix.rabbitclent.web;

import com.phoenix.rabbitclent.service.SkillService;
import com.phoenix.rabbitcommon.Thread.ThreadPool.ThreadPoolUtil;
import com.phoenix.rabbitcommon.utils.redis.RedisConstant;
import com.phoenix.rabbitcommon.utils.redis.RedisService;
import com.phoenix.rabbitcommon.utils.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("")
public class RedissonController {

    @Autowired
    RedisService redisService;

    @RequestMapping("/seckillInit")
    public void seckillInit(){

        redisService.setCacheMapValue(RedisConstant.MERCHANDISE_KEY,"no1004",500);
        int num = redisService.getCacheMapValue(RedisConstant.MERCHANDISE_KEY,"no1004");
        System.out.println(num);
    }

    @RequestMapping("/testSkill")
    public String TestSkillService() throws InterruptedException {
        // 初始化库存
        long startTime = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(600);
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPoolExecutor();
        //开50个线程
        for (int i = 0; i < 600; i++) {
            threadPoolExecutor.execute(new SkillThread(latch,"skillThread->" + i));
        }
        latch.await();
        return System.currentTimeMillis() - startTime+"ms";
    }

}

class SkillThread extends Thread {

    CountDownLatch latch;

    public SkillThread(CountDownLatch latch,String skillThreadName) {
        super(skillThreadName);
        this.latch = latch;
    }

    @Override
    public void run() {
        SpringUtils.getBean(SkillService.class).seckill();
        latch.countDown();
    }
}