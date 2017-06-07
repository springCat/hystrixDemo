package org.springcat.demo.PoolVsSemaphore;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

public class HelloCommandIsolateThreadPool extends HystrixCommand<String> {

  private String name;

  public HelloCommandIsolateThreadPool(String name) {
    super(HystrixCommand.Setter.
        //设置GroupKey 用于dashboard 分组展示
            withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello"))
        //设置commandKey 用户隔离线程池，不同的commandKey会使用不同的线程池
        .andCommandKey(HystrixCommandKey.Factory.asKey("hello" + name))
        //设置线程池名字的前缀，默认使用commandKey
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("hello$Pool" + name))
        //设置线程池相关参数
        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
            .withCoreSize(15)
            .withMaxQueueSize(10)
            .withQueueSizeRejectionThreshold(2))
        //设置command相关参数
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            //是否开启熔断器机制
            .withCircuitBreakerEnabled(true)
            //舱壁隔离策略
            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
            //circuitBreaker打开后多久关闭
            .withCircuitBreakerSleepWindowInMilliseconds(5000)));
      this.name = name;

  }

  @Override
  protected String run() throws Exception {
    System.out.println("run success " + Thread.currentThread().getName());
    return "run success " + Thread.currentThread().getName();
  }

  protected String getCacheKey() {
    return this.getClass().getSimpleName()+"_"+this.name;
  }

  /**
   * Allow the cache to be flushed for this object.
   */
  public  void flushCache() {
    //这里从HystrixRequestCache的getInstance静态方法中找到对应实例，并将响应值清除
    HystrixRequestCache.getInstance(this.getCommandKey(),
        HystrixConcurrencyStrategyDefault.getInstance()).clear(getCacheKey());
  }
}