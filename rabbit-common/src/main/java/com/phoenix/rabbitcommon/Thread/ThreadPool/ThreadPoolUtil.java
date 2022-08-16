package com.phoenix.rabbitcommon.Thread.ThreadPool;

import com.phoenix.rabbitcommon.handler.CustomRejectedExecutionHandler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolUtil {

    private static Integer threadCount =30;

    private static Integer queueSize = 2000;

    private static ReentrantLock lock = new ReentrantLock();

    private static ThreadPoolExecutor threadPoolExecutor = null;

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        lock.lock();
        try {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = new ThreadPoolExecutor(
                        5,
                        threadCount,
                        200,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(queueSize),
                        new CustomRejectedExecutionHandler());
            }
        } finally {
            lock.unlock();
        }
        return threadPoolExecutor;
    }
}
