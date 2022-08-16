package com.phoenix.rabbitcommon.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedissonConfig {

    @Value("${spring.redis.nodesswitch}")
    private boolean nodesswitch;

    @Autowired
    private RedisProperties redisProperties;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        // 设置watchdog 锁自动续期间隔时间 即 每隔30s检查是否需要对锁续期
        config.setLockWatchdogTimeout(30000);
        if (nodesswitch) {
            // 哨兵模式
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers()
                    .setMasterName(redisProperties.getSentinel().getMaster())
                    .setPassword(redisProperties.getPassword())
                    .setDatabase(redisProperties.getDatabase());
            for (String node : redisProperties.getSentinel().getNodes()) {
                String ip = "redis://" + node.split(":")[0] + ":" + node.split(":")[1];
                sentinelServersConfig.addSentinelAddress(ip);
            }
        }else{
            //  单机模式
            config.useReplicatedServers()
                    .addNodeAddress("redis://"+redisProperties.getHost()+":"+redisProperties.getPort())
                    .setPassword(redisProperties.getPassword())
                    .setDatabase(redisProperties.getDatabase());
        }
        return Redisson.create(config);
    }
}
