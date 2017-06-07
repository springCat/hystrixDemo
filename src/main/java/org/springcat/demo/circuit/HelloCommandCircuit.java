package org.springcat.demo.circuit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by 32983 on 2017/6/6.
 */
public class HelloCommandCircuit extends HystrixCommand<String> {

//  circuitBreakerRequestVolumeThreshold;     在时间窗口（默认10s）中需要达到的访问数量 默认20
//  circuitBreakerSleepWindowInMilliseconds;  触发circuit后重试的间隔 默认5s
//  circuitBreakerEnabled;                    是否启用circuit 默认启用
//  circuitBreakerErrorThresholdPercentage;   在时间窗口中触发circuit的错误访问百分比 默认50%
  public HelloCommandCircuit(String key, int semaphoreCount) {
    super(Setter
        //设置GroupKey 用于dashboard 分组展示
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello"))
        //设置CommandKey 用于Semaphore分组，相同的CommandKey属于同一组隔离资源
        .andCommandKey(HystrixCommandKey.Factory.asKey("hello" + key))
        //设置隔离级别：Semaphore
        .andCommandPropertiesDefaults(
            HystrixCommandProperties.Setter()
            //是否开启熔断器机制
            .withCircuitBreakerEnabled(true)
            //舱壁隔离策略
            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
            //设置每组command可以申请的permit最大数
            .withExecutionIsolationSemaphoreMaxConcurrentRequests(50)

            .withCircuitBreakerRequestVolumeThreshold(3)
            .withCircuitBreakerSleepWindowInMilliseconds(5)
            .withCircuitBreakerEnabled(true)
            .withCircuitBreakerErrorThresholdPercentage(50)

        )
    );

  }


  @Override
  protected String run() throws Exception {
    System.out.println("run success " + Thread.currentThread().getName());
    Thread.sleep(1000);
    return "run success " + Thread.currentThread().getName();
  }
  @Override
  protected String getFallback() {
    return "Hello Failure !";
  }
}