package com.hackday.play.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by victor on 11/11/17.
 * email: chengyiwang@hustunique.com
 * blog: www.victorwang.science                                            #
 */
public class AppExecutors {

    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    private static AppExecutors instance;

    private AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors(Executors.newSingleThreadExecutor(), Executors
                    .newFixedThreadPool(3)
                    , new MainThreadExecutor());
        }
        return instance;
    }

    /**
     * Disk io executor.
     *
     * @return the executor
     */
    public Executor diskIO() {
        return diskIO;
    }

    /**
     * Network io executor.
     *
     * @return the executor
     */
    public Executor networkIO() {
        return networkIO;
    }

    /**
     * Main thread executor.
     *
     * @return the executor
     */
    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
