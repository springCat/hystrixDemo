package org.springcat.demo.helloworld;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by 32983 on 2017/6/6.
 */
public class HelloWorldCommand extends HystrixCommand<String> {

  public HelloWorldCommand(String name) {
    super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name)));
  }

  @Override
  protected String run() throws Exception {
    System.out.println("run success " + Thread.currentThread().getName());
    return "run success " + Thread.currentThread().getName();
  }
}