package com.wxd.spread.core.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池工具类
 * @author wangxiaodan
 *
 */
public class ExecutorServiceUtil {
	private static final int DEFAULT_THREAD_POOL_SIZE = 5;
	private static ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
	
	/**
	 * 提交任务并获取任务返回的结果
	 * @param task
	 * @return
	 */
	public static <T> Future<T> submit(Callable<T> task) {
		return executorService.submit(task);
	}
	
	/**
	 * 提交任务并返回可以判断任务是否执行完毕结果的future
	 * @param runnable
	 * @return Future
	 */
	public static Future<?> submit(Runnable runnable) {
		return executorService.submit(runnable);
	}
	
	/**
	 * 异步执行并在遇到异常时直接抛出
	 * @param runnable
	 */
	public static void execute(Runnable runnable) {
		executorService.execute(runnable);
	}
}
