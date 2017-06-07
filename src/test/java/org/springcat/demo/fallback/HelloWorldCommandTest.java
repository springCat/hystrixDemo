package org.springcat.demo.fallback;

import org.junit.Test;


/**
 * Created by 32983 on 2017/6/7.
 */
public class HelloWorldCommandTest {
  @Test
  public void helloWorldCommand() {
    HelloWorldCommand helloWorldCommand = new HelloWorldCommand("klov");
    String result = helloWorldCommand.execute();
    System.out.println("【HelloWorldCommand】 result = "+result+" status:"+helloWorldCommand.isResponseFromFallback());
  }
}