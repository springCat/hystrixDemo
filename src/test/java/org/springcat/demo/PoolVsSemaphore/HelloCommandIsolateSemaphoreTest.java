package org.springcat.demo.PoolVsSemaphore;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by 32983 on 2017/6/6.
 */
public class HelloCommandIsolateSemaphoreTest {

    @Test
    public void helloWorldCommand() {
      HelloCommandIsolateSemaphore helloWorldCommand = new HelloCommandIsolateSemaphore("klov",10);
      String result1 = helloWorldCommand.execute();
      System.out.println("【HelloWorldCommand】 result = "+result1+" iscached:"+helloWorldCommand.isResponseFromCache());
    }

    @Test
    public void helloWorldCommandAsync() {
      HelloCommandIsolateSemaphore helloWorldCommand = new HelloCommandIsolateSemaphore("klov",10);
      Future<String> queue = helloWorldCommand.queue();
      try {
        String s = queue.get();
        System.out.println("【HelloWorldCommand】 result = "+s);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }

    }

}
