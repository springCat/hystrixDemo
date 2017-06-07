package org.springcat.demo.PoolVsSemaphore;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by 32983 on 2017/6/6.
 */
public class HelloCommandIsolateSemaphore extends HystrixCommand<String> {

  public HelloCommandIsolateSemaphore(String key, int semaphoreCount) {
    super(HystrixCommand.Setter
        //设置GroupKey 用于dashboard 分组展示
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello"))
        //设置CommandKey 用于Semaphore分组，相同的CommandKey属于同一组隔离资源
        .andCommandKey(HystrixCommandKey.Factory.asKey("hello" + key))
        //设置隔离级别：Semaphore
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            //是否开启熔断器机制
            .withCircuitBreakerEnabled(true)
            //舱壁隔离策略
            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
            //设置每组command可以申请的permit最大数
            .withExecutionIsolationSemaphoreMaxConcurrentRequests(50)
            //circuitBreaker打开后多久关闭
            .withCircuitBreakerSleepWindowInMilliseconds(5000)));

  }


  @Override
  protected String run() throws Exception {
    System.out.println("run success " + Thread.currentThread().getName());
    return "run success " + Thread.currentThread().getName();
  }

}