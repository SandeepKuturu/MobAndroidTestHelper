package com.mob.android.test.helper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Mock implementation of an ExecutorService.
 * With Unit tests you don't want to test threaded code.
 * It's usually easier and better to test deterministic code.
 * This mock will allow you to test code that delegates to another
 * thread by intercepting that delegation. You would only need to implement
 * the interfaces you need.
 */
public class MockExecutorService implements ExecutorService {
    /**
     * Flag for if the executor is shut down.
     */
    private boolean isShutdown;
    /**
     * A Queue of Runnables.
     */
    private Queue<Runnable> runnableQueue = new ArrayDeque<>();

    /**
     * Maybe we want to run all the tasks at a time we have control over.
     */
    public void runAllTasks() {
        for (final Runnable runnable : runnableQueue) {
            runnable.run();
        }
        runnableQueue.clear();
    }

    /**
     * Get a count of the pending tasks.
     *
     * @return count of the pending tasks.
     */
    public int getPendingCount() {
        return runnableQueue.size();
    }

    @Override
    public void shutdown() {
        isShutdown = true;
    }

    @NotNull
    @Override
    public List<Runnable> shutdownNow() {
        shutdown();
        return new ArrayList<>();
    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public boolean isTerminated() {
        return isShutdown;
    }

    @Override
    public boolean awaitTermination(
            final long timeout, final TimeUnit unit) throws InterruptedException {

        return true;
    }

    @NotNull
    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        return null;
    }

    @NotNull
    @Override
    public <T> Future<T> submit(final Runnable task, final T result) {
        return null;
    }

    @NotNull
    @Override
    public Future<?> submit(final Runnable task) {
        return null;
    }

    @NotNull
    @Override
    public <T> List<Future<T>> invokeAll(
            final Collection<? extends Callable<T>> tasks) throws InterruptedException {

        return null;
    }

    @NotNull
    @Override
    public <T> List<Future<T>> invokeAll(
            final Collection<? extends Callable<T>> tasks,
            final long timeout,
            final TimeUnit unit) throws InterruptedException {

        return null;
    }

    @NotNull
    @Override
    public <T> T invokeAny(
            final Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {

        return null;
    }

    @Override
    public <T> T invokeAny(
            final Collection<? extends Callable<T>> tasks,
            final long timeout,
            final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {

        return null;
    }

    @Override
    public void execute(final Runnable task) {
        runnableQueue.add(task);
    }
}
