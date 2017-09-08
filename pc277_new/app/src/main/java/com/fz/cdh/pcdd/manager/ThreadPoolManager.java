package com.fz.cdh.pcdd.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hang on 2017/2/8.
 */

public class ThreadPoolManager {

    public static ExecutorService service = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable) {
        service.execute(runnable);
    }
}
