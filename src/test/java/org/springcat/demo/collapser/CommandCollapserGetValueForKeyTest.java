package org.springcat.demo.collapser;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

/**
 * Created by 32983 on 2017/6/7.
 */
public class CommandCollapserGetValueForKeyTest {

  @Test
  public void testCollapser() throws Exception {
    //初始化request
    HystrixRequestContext context = HystrixRequestContext.initializeContext();
    try {
      //直接调用collapser
      Future<String> f1 = new CommandCollapserGetValueForKey(1).queue();
      Future<String> f2 = new CommandCollapserGetValueForKey(2).queue();
      Future<String> f3 = new CommandCollapserGetValueForKey(3).queue();
      Future<String> f4 = new CommandCollapserGetValueForKey(4).queue();

      assertEquals("ValueForKey: 1", f1.get());
      assertEquals("ValueForKey: 2", f2.get());
      assertEquals("ValueForKey: 3", f3.get());
      assertEquals("ValueForKey: 4", f4.get());

      //只调用了一次command
      // assert that the batch command 'GetValueForKey' was in fact
      // executed and that it executed only once
      assertEquals(1, HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().size());
      HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getAllExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
      // assert the command is the one we're expecting
      assertEquals("GetValueForKey", command.getCommandKey().name());
      // confirm that it was a COLLAPSED command execution
      assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
      // and that it was successful
      assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
    } finally {
      //关闭request
      context.shutdown();
    }
  }
}