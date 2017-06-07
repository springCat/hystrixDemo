package org.springcat.demo.PoolVsSemaphore;

import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import org.springcat.demo.helloworld.HelloWorldCommand;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 32983 on 2017/6/6.
 */
public class HelloCommandIsolateThreadPoolTest {

    @Test
    public void helloWorldCommand() {
      HystrixRequestContext ctx =  HystrixRequestContext.initializeContext();

      for (int i = 0; i < 100; i++) {
            HelloCommandIsolateThreadPool helloWorldCommand = new HelloCommandIsolateThreadPool("klov");
            String result = helloWorldCommand.execute();
            System.out.println("【HelloWorldCommand】 result = "+result+" iscached:"+helloWorldCommand.isResponseFromCache());
            if(i == 97){
              //这里从HystrixRequestCache的getInstance静态方法中找到对应实例，并将响应值清除
              helloWorldCommand.flushCache();
            }
      }
    }


}
